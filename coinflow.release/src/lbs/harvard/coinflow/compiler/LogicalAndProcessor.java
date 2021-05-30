package lbs.harvard.coinflow.compiler;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;

public class LogicalAndProcessor extends AbstractProcessor<CtElement>{

	boolean processedAndOp = false;
	

	@Override
	public void process(CtElement element) {
		// TODO Auto-generated method stub
		CodeFactory codeFactory = getFactory().Code();	
		CoreFactory coreFactory = getFactory().Core();
		if(element instanceof CtBinaryOperator) {
			CtBinaryOperator bo = (CtBinaryOperator) element;
			if(bo.getKind().equals(BinaryOperatorKind.AND)){
				if(bo.getRoleInParent() == CtRole.CONDITION) {
					if(bo.getParent() instanceof CtIf) {
						CtIf parentIf = (CtIf)bo.getParent();
						CtIf rightHandIF = coreFactory.createIf();
						rightHandIF.setCondition(bo.getRightHandOperand());
						rightHandIF.setThenStatement(parentIf.getThenStatement().clone());
						if(parentIf.getElseStatement() != null) {
							rightHandIF.setElseStatement(parentIf.getElseStatement().clone());
						}
						CtBlock b = coreFactory.createBlock();
						b.addStatement(rightHandIF);
						parentIf.setThenStatement(b);
						parentIf.setCondition(bo.getLeftHandOperand());
						processedAndOp = true;
					}else {
						processedAndOp = false;
					}
					
				}
			}else if(bo.getKind().equals(BinaryOperatorKind.OR)){
				if(bo.getRoleInParent() == CtRole.CONDITION) {
					
					if(bo.getParent() instanceof CtConditional) {
						// ternary expressions
						CtConditional parentCondIf = (CtConditional)bo.getParent();
						CtExpression newLocal = RewriteHelper.createNewObjVar(parentCondIf.getCondition());
						
//						CtBlock thenBlock = coreFactory.createBlock();
//						thenBlock.addStatement(bo.getLeftHandOperand());
//						parentIf.setElseStatement(b);
//						
//						CtIf rightHandIF = coreFactory.createIf();
//						rightHandIF.setCondition(bo.getRightHandOperand());
//						rightHandIF.setThenStatement(parentIf.getThenStatement().clone());
//						if(parentIf.getElseStatement() != null) {
//							rightHandIF.setElseStatement(parentIf.getElseStatement().clone());
//						}
//						CtBlock b = coreFactory.createBlock();
//						b.addStatement(rightHandIF);
//						parentIf.setElseStatement(b);
//						parentIf.setCondition(bo.getLeftHandOperand());
						processedAndOp = false;
						
					}else if(bo.getParent() instanceof CtIf){
						// if statement
						CtIf parentIf = (CtIf)bo.getParent();
						CtIf rightHandIF = coreFactory.createIf();
						rightHandIF.setCondition(bo.getRightHandOperand());
						rightHandIF.setThenStatement(parentIf.getThenStatement().clone());
						if(parentIf.getElseStatement() != null) {
							rightHandIF.setElseStatement(parentIf.getElseStatement().clone());
						}
						CtBlock b = coreFactory.createBlock();
						b.addStatement(rightHandIF);
						parentIf.setElseStatement(b);
						parentIf.setCondition(bo.getLeftHandOperand());
						processedAndOp = true;
					}
				}
			}
		}else if (element instanceof CtIf) {
			CtIf ctif = (CtIf)element;
			if(ctif.getElseStatement()!= null && 
					ctif.getElseStatement() instanceof CtIf) {
				CtBlock newBlock = coreFactory.createBlock();
				newBlock.addStatement(ctif.getElseStatement().clone());
				ctif.getElseStatement().delete();
				ctif.setElseStatement(newBlock);
				processedAndOp = true;
			}
		}
		
	}
	
	public boolean isProcessedAndOp() {
		return processedAndOp;
	}


}
