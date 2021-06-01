package lbs.harvard.coinflow.compiler;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtIntersectionTypeReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtInterfaceImpl;

/**
 * Preprocess tenary expressions
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class TenaryExprProcessor extends AbstractProcessor<CtConditional>{

	@Override
	public void process(CtConditional ctCond) {
		createNewLocal(ctCond, ctCond.getFactory().Code(), ctCond.getFactory().Core());
	}

	private static int idx = 0;
	private static String prefix = "$$_tenary_";
	
	private static void createNewLocal(CtConditional ctCond, CodeFactory codeFactory, CoreFactory coreFactory) {
		
		CtTypeReference type = coreFactory.createTypeReference();
		if(ctCond.getType() instanceof CtIntersectionTypeReference) {
			type = (CtTypeReference)((CtIntersectionTypeReference)ctCond.getType()).getBounds().get(0);
		}else {
			type = ctCond.getType();
		}
		String newLocalName =  prefix + idx++;
		
		CtElement tmp = ctCond;
		if(tmp == null || tmp.getParent() == null) {
			return;
		}
		tmp = RewriteHelper.getTopLevelStmt(ctCond);
		// if this method invocation belongs to super() or this(), we don't insert
		if(tmp == null) {
			return ;
		}
		
		// This line of code sets type arguments to empty; HashMap<> => HashMap
		RewriteHelper.removeTypeArgument(type);
		if(ctCond.getTypeCasts().size() > 0) {
			type = (CtTypeReference) ctCond.getTypeCasts().get(0);
		}
		CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, null);
		CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
		
		RewriteHelper.insertStatements(((CtStatement)tmp), newVarDecl, true);
		CtExpression vr = codeFactory.createVariableRead(ref, false);
//			addTypeCase(element, vr);
		if(ctCond.getRoleInParent() != CtRole.STATEMENT) {
			ctCond.replace(vr);
		}else {
			ctCond.delete();
		}
		CtIf ifTenary = coreFactory.createIf();
		ifTenary.setCondition(ctCond.getCondition());
		
		CtAssignment fst = coreFactory.createAssignment();
		CtVariableWrite left = coreFactory.createVariableWrite();
		left.setVariable(ref);
		fst.setAssigned(left);
		fst.setAssignment(ctCond.getThenExpression());
		

		CtAssignment snd = coreFactory.createAssignment();
		CtVariableWrite sec_left = coreFactory.createVariableWrite();
		sec_left.setVariable(ref);
		snd.setAssigned(sec_left);
		snd.setAssignment(ctCond.getElseExpression());
		
		CtBlock fstBlock = coreFactory.createBlock();
		fstBlock.addStatement(fst);
		
		CtBlock sndBlock = coreFactory.createBlock();
		sndBlock.addStatement(snd);
		
		ifTenary.setThenStatement(fstBlock);
		ifTenary.setElseStatement(sndBlock);
		RewriteHelper.insertStatements(((CtStatement)tmp), ifTenary, true);
	}
	
}
