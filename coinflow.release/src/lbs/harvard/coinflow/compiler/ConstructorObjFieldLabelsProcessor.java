package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.util.AccessedClasses;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;

public class ConstructorObjFieldLabelsProcessor extends AbstractProcessor<CtExecutable> {

	@Override
	public void process(CtExecutable element) {
		
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		
		if(element.getReference().isConstructor() 
				|| 
				(element.getSimpleName().startsWith(ReorganizeInitilizationProcessor.method_prefix) 
						&& !element.getReference().isStatic())) {

			
//		if(element.getReference().isConstructor()) {

				CtInvocation ctInv = buildGetCurrentLevel(codeFactory, coreFactory);
				
				
				CtAssignment<Object, Object> objLabelAssign = coreFactory.createAssignment();
				CtFieldWrite lhs = coreFactory.createFieldWrite();
				
//				CtTypeReference t = element.getReference().getType();
				CtTypeReference t = element.getReference().getDeclaringType();
				
				
				lhs.setTarget(codeFactory.createThisAccess(t));
				CtVariableReference objfieldRef = coreFactory.createFieldReference(); 
				objfieldRef.setType(codeFactory.createCtTypeReference(AccessedClasses.Label));
				objfieldRef.setSimpleName(AddFieldObjectLabelFieldProcessor.obj_object_label);
				lhs.setVariable(objfieldRef);
				objLabelAssign.setAssigned(lhs);
				objLabelAssign.setAssignment(ctInv);
				
				CtAssignment<Object, Object> fieldLabelAssign = coreFactory.createAssignment();
				CtFieldWrite lhs_field_label = coreFactory.createFieldWrite();
				lhs_field_label.setTarget(codeFactory.createThisAccess(t));
				CtVariableReference fieldfieldRef = coreFactory.createFieldReference(); 
				fieldfieldRef.setType(codeFactory.createCtTypeReference(AccessedClasses.Label));
				fieldfieldRef.setSimpleName(AddFieldObjectLabelFieldProcessor.obj_field_label);
				lhs_field_label.setVariable(fieldfieldRef);
				fieldLabelAssign.setAssigned(lhs_field_label);
				fieldLabelAssign.setAssignment(ctInv.clone());
				
			
			
//				CtStatement setObjectLabel = codeFactory.createCodeSnippetStatement("this."+ AddObjLabelFieldProcessor.obj_object_label + "= IFCUtil.getCurrentLabel()");
//				CtStatement setFieldLabel = codeFactory.createCodeSnippetStatement("this."+ AddFieldLabelFieldProcessor.obj_field_label + "= IFCUtil.getCurrentLabel()");
				
				element.getBody().insertBegin(objLabelAssign);
				element.getBody().insertBegin(fieldLabelAssign);
//			}
		}
	}
	
	
	// build IFCUtil.getCurrentLevel() expression
	public static CtInvocation buildGetCurrentLevel(CodeFactory codeFactory, CoreFactory coreFactory) {
		CtTypeReference target = codeFactory.createCtTypeReference(AccessedClasses.IFCUtil);
		CtExecutableReference getCurrentLabelRef = coreFactory.createExecutableReference();
		getCurrentLabelRef.setStatic(true);
		getCurrentLabelRef.setSimpleName("getCurrentLabel");
		getCurrentLabelRef.setDeclaringType(target);

		CtInvocation ctInv = codeFactory.createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), 
						getCurrentLabelRef, new ArrayList());
		
		return ctInv;
	}
	
}
