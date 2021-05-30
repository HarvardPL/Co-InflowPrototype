package lbs.harvard.coinflow.compiler;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

public class ObjectLabelProcessor extends AbstractProcessor<CtInvocation>{

	@Override
	public void process(CtInvocation ctInv) {
		String methodSign = ctInv.getExecutable().getSignature();
		if(methodSign.equals("fieldLabelOf(java.lang.Object)")) {
			//join object label
			CodeFactory codeFactory = getFactory().Code();
			CoreFactory coreFactory = getFactory().Core();
			CtStatement stmt = RewriteHelper.getTopLevelStmt(ctInv);
			CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
			joinLabelRef.setStatic(true);
			joinLabelRef.setSimpleName("joinTopLabel");
			joinLabelRef.setDeclaringType(target);
			
			CtExpression readObjLabel = RewriteHelper.createObjectLabelFromTarget(
					(CtExpression) ctInv.getArguments().get(0), codeFactory);
			if(!readObjLabel.toString().equals(LabelCheckProcessor.bottomName) 
					&& !readObjLabel.toString().equals(LabelCheckProcessor.bottomString)) {
				CtStatement raiseContextLabelExpr = getFactory().Code().createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
						readObjLabel);
			    RewriteHelper.insertStatements(stmt, raiseContextLabelExpr, true);
			}
			// rewrite it to xxx.fieldLabel
			ctInv.replace(RewriteHelper.buildFieldLabelExpr((CtExpression) ctInv.getArguments().get(0)));
			
		}
	}

}
