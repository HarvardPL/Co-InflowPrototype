package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtBreak;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;

public class SwitchCaseProcessor extends AbstractProcessor<CtSwitch>{

	public void process(CtSwitch ctSwitch) {
		// change switch/case to if statements
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(ctSwitch.getCases().size() == 0) {
			return;
		}
		CtIf ctIf = coreFactory.createIf();
		CtIf tmp = null;
		CtBlock ctDefaultBlock = null; 
		
		CtExpression[] conds = new CtExpression[ctSwitch.getCases().size()];
		for(int i = 0; i < ctSwitch.getCases().size(); i++) {
			CtCase caseExp = (CtCase)ctSwitch.getCases().get(i);
			CtExpression cond = (CtExpression)codeFactory.createBinaryOperator(ctSwitch.getSelector().clone(), 
					caseExp.getCaseExpression(), BinaryOperatorKind.EQ);
			conds[i] = cond;
		}
		
		for(int i = 0; i < ctSwitch.getCases().size(); i++) {
			CtBlock thisBlock = coreFactory.createBlock();
			CtCase caseExp = (CtCase)ctSwitch.getCases().get(i);
			for(int k = 0; k < caseExp.getStatements().size(); k++) {
				CtStatement stmt = (CtStatement)caseExp.getStatement(k);
				if(stmt instanceof CtBreak) {
					break;
				}
				thisBlock.addStatement(stmt.clone());
			}
			if(thisBlock.getStatements().size() == 0) {
				continue;
			}
			if(caseExp.getCaseExpression() == null) {
				// default case
				ctDefaultBlock = thisBlock;
				continue;
			}
			CtExpression cur_cond = conds[i];
			// case without break will execute the body of other cases, i.e., case 34: case 43: do(); break; means 34 and 43 both get to do();
			// to handle such situation, we lookup all conditions
			for(int j = i - 1; j >= 0; j--) {
				CtCase caseThisExp = (CtCase)ctSwitch.getCases().get(j);
				if(!(caseThisExp.getStatements().size() == 0) 
						&& caseThisExp.getStatements().get(caseThisExp.getStatements().size() -1) instanceof CtBreak) {
					// found case break; then we stop looking
					break;
				}
				if(caseThisExp.getCaseExpression() == null) {
					// found default case, we stop looking
					break;
				}
				// otherwise, we || the current condition
				cur_cond = (CtExpression)codeFactory.createBinaryOperator(cur_cond, 
						conds[j].clone(), BinaryOperatorKind.OR);
			}
			
			CtIf ctThisIf = coreFactory.createIf();
			ctThisIf.setCondition(cur_cond);
			ctThisIf.setThenStatement(thisBlock);
			if(tmp == null) {
				ctIf = ctThisIf;
			}else {
				tmp.setElseStatement(ctThisIf);
			}
			tmp = ctThisIf;	
		}
		if(ctDefaultBlock != null) {
			if(tmp !=null) {
				tmp.setElseStatement(ctDefaultBlock.clone());
			}
		}
		if(ctIf.getCondition() == null) {
			ctSwitch.replace(ctDefaultBlock.clone());
		}else {
			ctSwitch.replace(ctIf);
		}
	}
}
