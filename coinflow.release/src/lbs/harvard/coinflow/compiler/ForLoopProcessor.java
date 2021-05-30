package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;


/**
 * transfer for loop into a new shape: 
 * @author llama_jian
 *
 */
public class ForLoopProcessor extends AbstractProcessor<CtFor> {	
	
	static String forLoopLocal_prefix = "$$_for_var";
	static int idx = 0;
	
	@Override
	public void process(CtFor ctFor) {
		
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();

//		CtExpression guard = ctFor.getExpression();
//		CtExpression<Boolean> trueExpr = codeFactory.createLiteral(true);
//		ctFor.setExpression(trueExpr);
		
//		CtStatement body = ctFor.getBody();
//		List<CtStatement> initStmts = ctFor.getForInit();
//		for(CtStatement initStmt : initStmts) {
//			if(initStmt instanceof CtLocalVariable) {
//				CtLocalVariable var = (CtLocalVariable)initStmt;
//				String newLocalName = forLoopLocal_prefix + idx++;
//				CtForEachProcessor.addLocalForLoopVar(codeFactory, coreFactory, 
//						body, newLocalName, var);
//			}
//		}
		
		CtBlock newForBlock = coreFactory.createBlock();
		CtFor ctForNew = ctFor.clone();
		newForBlock.addStatement(ctForNew);
		for(Object o : ctForNew.getForInit()) {
			RewriteHelper.insertStatements(ctForNew, ((CtStatement)o).clone(), true);
		}
		ctForNew.setForInit(new ArrayList<>());
		
		ctFor.replace(newForBlock);
		
//		// create a block if it's not a block
//		if(body instanceof CtBlock) {
//			CtBlock block = (CtBlock) body;
//			for(CtStatement update: ctFor.getForUpdate()) {
//				update.delete();
//				block.insertEnd(update);
//			}
//			CtIf newIf = coreFactory.createIf();
//			
//			CtUnaryOperator negation = coreFactory.createUnaryOperator();
//			negation.setOperand(guard);
//			negation.setKind(UnaryOperatorKind.NOT);
//			
//			newIf.setCondition(negation);
//			newIf.setThenStatement(coreFactory.createBreak());
//			block.insertBegin(newIf);
//			
//			
//			
//			ctFor.setForUpdate(new ArrayList<CtStatement>());
//		}
		
	}

}
