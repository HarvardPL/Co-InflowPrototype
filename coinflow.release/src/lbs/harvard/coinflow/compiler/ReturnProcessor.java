package lbs.harvard.coinflow.compiler;

import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class ReturnProcessor extends AbstractProcessor<CtInvocation>{


	// dealing with return statement of the form: return unlabelOpaque(OpaqueLabeled<> input)
	
	@Override
	public void process(CtInvocation ctInv) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		
		// raise the context level if the return statement has the form: return unlabelOpaque(OpaqueLabel opl)
		CtStatement stmt = RewriteHelper.getTopLevelStmt(ctInv);
		if(ctInv.getExecutable().getSimpleName().startsWith("unlabelOpaque") && stmt instanceof CtReturn) {
			CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
			joinLabelRef.setStatic(true);
			joinLabelRef.setSimpleName("joinTopLabel");
			joinLabelRef.setDeclaringType(target);
			
			CtExpression objCalled = (CtExpression)ctInv.getArguments().get(0);
			CtExpression opaqueLabelOfExpr = RewriteHelper.createOpaqueLabelOfExp(objCalled);
			CtStatement raiseContextLabelExpr = getFactory().Code().createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
					opaqueLabelOfExpr);
			RewriteHelper.insertStatements(stmt, raiseContextLabelExpr, true);
		}
	}
}
