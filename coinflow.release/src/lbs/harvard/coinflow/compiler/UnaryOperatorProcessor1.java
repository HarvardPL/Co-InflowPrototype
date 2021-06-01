package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.support.reflect.code.CtStatementListImpl;

/**
 * preprocess unary expressions
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class UnaryOperatorProcessor1 extends AbstractProcessor<CtElement>{

	@Override
	public void process(CtElement element) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(element instanceof CtUnaryOperator) {
//			if(element.getRoleInParent() == CtRole.FOR_UPDATE) {
//				return;
//			}
			
			if(element.getRoleInParent() == CtRole.STATEMENT || 
					element.getRoleInParent() == CtRole.FOR_UPDATE) {
				CtUnaryOperator unaryOp = (CtUnaryOperator) element;
				replaceWithAssignment(unaryOp,codeFactory, coreFactory);
				return;
			}
			
			CtUnaryOperator unaryOp = (CtUnaryOperator)element;// Preprocess these operators
			if(unaryOp.getKind() == UnaryOperatorKind.NEG || unaryOp.getKind() == UnaryOperatorKind.POS
					|| unaryOp.getKind() == UnaryOperatorKind.NOT) {
				// These operators have no issue
				return;
			}
			if(unaryOp.getOperand() instanceof CtVariableWrite) {
				CtVariableWrite v = (CtVariableWrite) unaryOp.getOperand();
				if(v.getVariable() instanceof CtLocalVariableReference || v.getVariable() instanceof CtParameterReference) {
					createNewLocalVar(unaryOp.getFactory().Code(), unaryOp);
				}
			}
		}else if (element instanceof CtOperatorAssignment) {
			CtOperatorAssignment opAssign = (CtOperatorAssignment)element;
			if(opAssign.getAssigned() instanceof CtVariableWrite) {
				CtVariableWrite v = (CtVariableWrite) opAssign.getAssigned();
				if(v.getVariable() instanceof CtLocalVariableReference || v.getVariable() instanceof CtParameterReference) {
					CtAssignment assign = coreFactory.createAssignment();
					assign.setAssigned(opAssign.getAssigned());
					CtVariableWrite vw = (CtVariableWrite)opAssign.getAssigned();
					CtVariableRead vr = coreFactory.createVariableRead();
					vr.setVariable(v.getVariable());
					CtBinaryOperator newBinaryAssignment = codeFactory.createBinaryOperator(
							vr, opAssign.getAssignment(), opAssign.getKind());
					assign.setAssignment(newBinaryAssignment);
					CtElement tmp = element;
					if(tmp == null || tmp.getParent() == null) {
						return;
					}
					tmp = RewriteHelper.getTopLevelStmt(element);
//					if(element.getRoleInParent() == CtRole.STATEMENT) {
//						RewriteHelper.insertStatements(((CtStatement)tmp), assign, true);
//						tmp.delete();
//					}
					if(element.getRoleInParent() == CtRole.STATEMENT || 
							element.getRoleInParent() == CtRole.FOR_UPDATE) {
						element.replace(assign);
					}
				}
			}
		}else if (element instanceof CtAssignment) {
			CtAssignment assignment = (CtAssignment)element;
			if(assignment.getRoleInParent() == CtRole.LEFT_OPERAND 
					|| assignment.getRoleInParent() == CtRole.RIGHT_OPERAND) {
				CtElement tmp = RewriteHelper.getTopLevelStmt(element);
				if(assignment.getAssigned() instanceof CtVariableWrite) {
					CtVariableWrite vw = (CtVariableWrite)assignment.getAssigned();
					CtVariableRead vr = coreFactory.createVariableRead();
					vr.setVariable(vw.getVariable());
					RewriteHelper.insertStatements((CtStatement)tmp, assignment.clone(), true);
					assignment.replace(vr);
				}
			}
		}
		
		
	}

	public static int counter = 0;
	public static String var_prefix = "$$_unary_";
	/**
	 * if the unray operator is called as part of other computation
	 * @param element
	 * @param codeFactory
	 * @param unaryOp
	 */
	public static void createNewLocalVar(CodeFactory codeFactory, CtUnaryOperator unaryOp) {
		CtTypeReference type = unaryOp.getOperand().getType();
		String newLocalName =  var_prefix + counter++;
		CtElement tmp = unaryOp;
		if(tmp == null || tmp.getParent() == null) {
			return;
		}
		tmp = RewriteHelper.getTopLevelStmt(unaryOp);
		// This line of code sets type arguments to empty; HashMap<> => HashMap
		RewriteHelper.removeTypeArgument(type);
		
		CtStatementList toInserted = new CtStatementListImpl();
		CoreFactory coreFactory = unaryOp.getFactory().Core();
		CtAssignment assign = coreFactory.createAssignment();
		assign.setAssigned(unaryOp.getOperand());
		CtVariableWrite v = (CtVariableWrite)unaryOp.getOperand();
		CtVariableRead vr = coreFactory.createVariableRead();
		vr.setVariable(v.getVariable());
		vr.setType(v.getType());
		CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, vr.clone());
		CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
		
		boolean post = unaryKindBeforeAfter(unaryOp.getKind());
		CtBinaryOperator newBinaryAssignment = codeFactory.createBinaryOperator(
				vr, codeFactory.createLiteral(1), unaryKindToBinaryKind(unaryOp.getKind()));
		assign.setAssignment(newBinaryAssignment);
		CtExpression newVr = codeFactory.createVariableRead(ref, false);
		RewriteHelper.addTypeCase(unaryOp, newVr);
		if(unaryOp.getRoleInParent() != CtRole.STATEMENT 
				) {
			if(post) {
				// if true, transformed assignment happens after the variable read, e.g., 
				// i++ => newLocal = i; i = i + 1;
				// create a new local variabel for the old value of i
				toInserted.addStatement(newVarDecl);
				toInserted.addStatement(assign);
				unaryOp.replace(newVr);
			}else {
				toInserted.addStatement(assign);
			}
			RewriteHelper.insertStatements(((CtStatement)tmp), toInserted, true);
		}else {
			toInserted.addStatement(assign);
			RewriteHelper.insertStatements(((CtStatement)tmp), toInserted, true);
			unaryOp.delete();
		}
		
	}
	
	private static void replaceWithAssignment(CtUnaryOperator unaryOp, CodeFactory codeFactory, CoreFactory coreFactory) {
		CtAssignment assign = coreFactory.createAssignment();
		assign.setAssigned(unaryOp.getOperand());
//		boolean post = unaryKindBeforeAfter(unaryOp.getKind());
		CtVariableWrite v = (CtVariableWrite)unaryOp.getOperand();
		CtVariableReference varDecl = v.getVariable();
		
		if(varDecl instanceof CtFieldReference){
			CtFieldWrite left = (CtFieldWrite)unaryOp.getOperand();
			CtFieldRead vr = coreFactory.createFieldRead();
			vr.setVariable(v.getVariable());
			vr.setType(v.getType());
			vr.setTarget(left.getTarget().clone());
			CtBinaryOperator newBinaryAssignment = codeFactory.createBinaryOperator(
					vr, codeFactory.createLiteral(1), unaryKindToBinaryKind(unaryOp.getKind()));
			assign.setAssignment(newBinaryAssignment);
			unaryOp.replace(assign);
		}else if(varDecl instanceof CtVariableReference) {
			CtVariableRead vr = coreFactory.createVariableRead();
			vr.setVariable(v.getVariable());
			vr.setType(v.getType());
			CtBinaryOperator newBinaryAssignment = codeFactory.createBinaryOperator(
					vr, codeFactory.createLiteral(1), unaryKindToBinaryKind(unaryOp.getKind()));
			assign.setAssignment(newBinaryAssignment);
			unaryOp.replace(assign);
		}
		
//		CtExpression newVr = codeFactory.createVariableRead(v.getVariable(), false);
//		RewriteHelper.addTypeCase(unaryOp, newVr);
	}
	
	public static boolean unaryKindBeforeAfter(UnaryOperatorKind k) {
		boolean post = true;
		switch (k) {
			case POSTINC:
				post = true;
				break;
			case POSTDEC:
				post = true;
				break;
			case PREDEC:
				post = false;
				break;
			case PREINC:
				post = false;
				break;
		}
		return post;
	}
	
	public static BinaryOperatorKind unaryKindToBinaryKind(UnaryOperatorKind k) {
		BinaryOperatorKind result = null;
		switch (k) {
			case POSTINC:
				result = BinaryOperatorKind.PLUS;
				break;
			case POSTDEC:
				result = BinaryOperatorKind.MINUS;
				break;
			case PREDEC:
				result = BinaryOperatorKind.MINUS;
				break;
			case PREINC :
				result = BinaryOperatorKind.PLUS;
				break;
		}
		return result;
	}
}
