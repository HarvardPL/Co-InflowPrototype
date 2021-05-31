package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * This class is not used currently. 
 * @author Jian Xiang(jxiang@seas.harvard.edu)
 *
 */
public class ContructorArgumentProcessor extends AbstractProcessor<CtConstructorCall> {
	CodeFactory codeFactory = null;
	CoreFactory coreFactory  = null;
	
	@Override
	public void process(CtConstructorCall ctInvocation) {
		codeFactory = getFactory().Code();
		coreFactory = getFactory().Core();
		if(!ctInvocation.getExecutable().getSimpleName().startsWith("unlabelOpaque") && ctInvocation.toString().contains("unlabelOpaque") 
				&& !ctInvocation.getExecutable().getSimpleName().startsWith("toOpaqueLabeled")) {
			// only proceed when there is an 'unlabelOpaque(x)' argument
			CtElement element = ctInvocation;
			while(! (element.getParent() instanceof CtBlock)) {
				// find the statement level element
				element = element.getParent();
			}
			
			CtStatement stmt = (CtStatement) element;
			// for sanity check
			setCalleeSigature(stmt, ctInvocation.getExecutable().getSignature());
			
			CtExpression targetObj = ctInvocation.getTarget();
			if(targetObj instanceof CtInvocation) {
				CtInvocation opaqueObjCalled = (CtInvocation)targetObj;
				if (opaqueObjCalled.getExecutable().getSimpleName().startsWith("unlabelOpaque")) {
					CtExpression objCalled = (CtExpression)opaqueObjCalled.getArguments().get(0);
					insertArgumentLabelStatement(stmt, objCalled);
				}else {
					insertArgumentLabelStatement(stmt, null);
				}
			}else {
				insertArgumentLabelStatement(stmt, null);
			}
			for(int i =0; i< ctInvocation.getArguments().size(); i++) {
				
				CtExpression argu = (CtExpression)ctInvocation.getArguments().get(i);
				if(argu instanceof CtInvocation) {
					CtInvocation unlabelOpaqueCall = (CtInvocation)argu;
					if (unlabelOpaqueCall.getExecutable().getSimpleName().startsWith("unlabelOpaque")) {
						CtExpression opaqueLabeledArgu = (CtExpression)unlabelOpaqueCall.getArguments().get(0);
						insertArgumentLabelStatement(stmt, opaqueLabeledArgu);
					}else {
						insertArgumentLabelStatement(stmt, null);
					}
				}else {
					insertArgumentLabelStatement(stmt, null);
				}
			}
			
		}
		
	}
	
	private void insertArgumentLabelStatement( CtStatement stmt, CtExpression content) {
		
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference addLabelRef = coreFactory.createExecutableReference();
		addLabelRef.setStatic(true);
		addLabelRef.setSimpleName("addPassingArgumentLabel");
		addLabelRef.setDeclaringType(target);
		CtTypeReference labelT = codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class);

		if(content == null) {
			CtExecutableReference executableReference = coreFactory.createExecutableReference();
		    executableReference.setStatic(true);
		    executableReference.setSimpleName("buttomLabel");
		    executableReference.setDeclaringType(target);
		    
			CtExpression getButtomLabel = codeFactory.createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, new ArrayList());
			
			CtStatement addLabelStmt = codeFactory.createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), addLabelRef, getButtomLabel);
			RewriteHelper.insertStatements(stmt, addLabelStmt, true);
		}else {
			CtExecutableReference executableReference = coreFactory.createExecutableReference();
		    executableReference.setStatic(true);
		    executableReference.setSimpleName("opaqueLabelOf");
		    executableReference.setDeclaringType(target);
		    
		    CtTypeReference arguT = content.getType();
		    executableReference.setType(labelT);
		    
			CtExpression unlabelOpaqueExpr = codeFactory.createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, content);
			
			CtStatement addLabelStmt = codeFactory.createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), addLabelRef, unlabelOpaqueExpr);
			RewriteHelper.insertStatements(stmt, addLabelStmt, true);
		}
	}
	
	private void setCalleeSigature(CtStatement stmt, String methodSign) {
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference setMethodSign = coreFactory.createExecutableReference();
		setMethodSign.setStatic(true);
		setMethodSign.setSimpleName("setCalleeSigature");
		setMethodSign.setDeclaringType(target);
		CtStatement setCalleeSignStmt = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), setMethodSign, 
				codeFactory.<String>createCodeSnippetExpression("\"" + methodSign + "\""));
		
		RewriteHelper.insertStatements(stmt, setCalleeSignStmt, true);
	}

}
