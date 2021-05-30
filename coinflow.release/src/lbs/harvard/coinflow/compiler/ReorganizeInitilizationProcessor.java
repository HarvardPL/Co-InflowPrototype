package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssert;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class ReorganizeInitilizationProcessor extends AbstractProcessor<CtField>{
	
	@Override
	public void process(CtField ctField) {
		if(ctField instanceof CtEnumValue) {
			return;
		}
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(ctField.getParent() != null) {
			rewriteInitilization(ctField, codeFactory, coreFactory);
		}
		
	}
	
	public static String method_prefix = "_ifc_init_";
	public static int method_counter = 0;
	
	public static void rewriteInitilization(CtField ctField, CodeFactory codeFactory, CoreFactory coreFactory) {
		if(ctField.getDefaultExpression() == null  || ctField.getDefaultExpression() instanceof CtLiteral) {
			return;
		}
		if(ctField.isFinal()) {
			ctField.removeModifier(ModifierKind.FINAL);
		}
		CtExpression rhs = ctField.getDefaultExpression();
		String newMethodName =  method_prefix + method_counter++;
	    
	    CtClass parentClazz = (CtClass)ctField.getParent();
		CtMethod initilizeBlock = coreFactory.createMethod();
		initilizeBlock.setSimpleName(newMethodName);
		if(ctField.isStatic()) {
			initilizeBlock.addModifier(ModifierKind.STATIC);
		}
		initilizeBlock.setType(rhs.getType());
		
		CtAssignment newFieldWriteExpr = coreFactory.createAssignment();
		CtFieldWrite newFieldWrite = coreFactory.createFieldWrite();
		if(ctField.isStatic()) {
			newFieldWrite.setTarget(codeFactory.createTypeAccess(parentClazz.getReference()));
		}else {
			newFieldWrite.setTarget(codeFactory.createThisAccess(parentClazz.getReference()));
		}
		
		newFieldWrite.setVariable(ctField.getReference());
		newFieldWriteExpr.setAssigned(newFieldWrite);
		newFieldWriteExpr.setAssignment(ctField.getAssignment());
		
		initilizeBlock.setBody(newFieldWriteExpr);
		CtReturn<Object> retStmt = coreFactory.createReturn();
		
		CtFieldRead<Object> retExpr = coreFactory.createFieldRead();
		retExpr.setTarget(newFieldWrite.getTarget().clone());
		retExpr.setVariable(newFieldWrite.getVariable().clone());
		retStmt.setReturnedExpression(retExpr);
		newFieldWriteExpr.insertAfter(retStmt);
		parentClazz.addMethod(initilizeBlock);
		
	    CtTypeReference rhsT = rhs.getType();
	    RewriteHelper.removeTypeArgument(rhsT);
	    if(ctField.isStatic()) {
	    		CtExpression newInitExpr = //codeFactory.createCodeSnippetExpression(newMethodName+"()");
					codeFactory.createInvocation(codeFactory.createTypeAccess(parentClazz.getReference()),
							initilizeBlock.getReference(), new ArrayList());
	    		ctField.setAssignment(newInitExpr);
	    }else {
    			CtExpression newInitExpr = //codeFactory.createCodeSnippetExpression(newMethodName+"()");
				codeFactory.createInvocation(codeFactory.createThisAccess(parentClazz.getReference()),
						initilizeBlock.getReference(), new ArrayList());
    			ctField.setAssignment(newInitExpr);
	    }

	    
	}
}
