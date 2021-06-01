package lbs.harvard.coinflow.compiler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * Add flow checks before method calls of raiseObjLabel
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class RaiseObjLabelProcessor extends AbstractProcessor<CtInvocation> {

	@Override
	public void process(CtInvocation ctInv) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(ctInv.getExecutable().getSimpleName().startsWith("raiseObjLabel")){
			CtExpression objTarget = (CtExpression)ctInv.getArguments().get(0);
			CtExpression labelTarget = (CtExpression)ctInv.getArguments().get(1);
			
			CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			
			try {
				if(CoInflowCompiler.classesToProcess.contains(objTarget.getType().getQualifiedName())){
					CtExpression fieldLabelExpr = (codeFactory.createCodeSnippetExpression(objTarget.toString() + "." + AddFieldObjectLabelFieldProcessor.obj_field_label));
					CtExpression objLabelExpr = (codeFactory.createCodeSnippetExpression(objTarget.toString() + "." + AddFieldObjectLabelFieldProcessor.obj_object_label));
					
					// check if current context level flows to the object label
					// Check if current field label flows to the second argument 
					CtExecutableReference checkLabelExprRef = coreFactory.createExecutableReference();
					checkLabelExprRef.setStatic(true);
					checkLabelExprRef.setSimpleName("checkRaiseObjLabelFlow");
					checkLabelExprRef.setDeclaringType(target);
					
					List argus = new ArrayList<>();
					
					argus.add(objLabelExpr);
					argus.add(fieldLabelExpr);
					argus.add(labelTarget);
					
					CtStatement toAdded = codeFactory.createInvocation(
									codeFactory.createTypeAccessWithoutCloningReference(target), checkLabelExprRef, argus);
					RewriteHelper.insertStatements(ctInv, toAdded, true);
					
					CtStatement changeObjLabel = codeFactory.createCodeSnippetStatement(objTarget.toString() + "." + AddFieldObjectLabelFieldProcessor.obj_field_label 
							+"=" + labelTarget.toString() );
					RewriteHelper.insertStatements(ctInv, changeObjLabel, true);
					ctInv.delete();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
