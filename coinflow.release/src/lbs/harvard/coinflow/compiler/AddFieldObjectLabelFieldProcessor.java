package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeInformation;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * This process adds two fields to every class definition it can rewrite: 
 * (1) a field for the field label, and (2) a field for the object label
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class AddFieldObjectLabelFieldProcessor extends AbstractProcessor<CtType>{
	
	public static String obj_field_label = "obj_field_label";
	public static String obj_object_label = "obj_object_label";

	@Override
	public void process(CtType element) {
		// TODO: dealing with inner classes
        //		if(!clazz.isAnonymous()) {
		
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		CtType clazz = (CtType)element;
//		if(element instanceof CtInterface) {
//			clazz = (CtInterface)element;
//		}else if (element instanceof CtClass) {
//			clazz = (CtClass)element;
//			if(hasSuperClassProcessed(clazz)) {
//				return;
//			}
//		}
			
		CtTypeReference labelRef = getFactory().Code().createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class);
		if(clazz.getDeclaredFields() != null) {
			for(CtFieldReference f :  clazz.getDeclaredFields()) {
				if(f.getSimpleName().equals(obj_field_label)) {
					return;
				}
			}
		}
		
		CtInvocation ctinv = ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory);
		CtField fieldLabelfield = codeFactory.createCtField(obj_field_label, labelRef, "", ModifierKind.PUBLIC);
		fieldLabelfield.setAssignment(ctinv);
		clazz.addFieldAtTop(fieldLabelfield);
		
		CtField objectLabelfield = codeFactory.createCtField(obj_object_label, labelRef, "", ModifierKind.PUBLIC);
		objectLabelfield.setAssignment(ctinv.clone());
		clazz.addFieldAtTop(objectLabelfield);
		
			
			// create a static initializer
			/*
			CtAssignment fieldLabelAssign = coreFactory.createAssignment();
			CtVariableWrite fieldLabelAssigned = coreFactory.createVariableWrite();
			fieldLabelAssigned.setVariable(fieldLabelfield.getReference());
			fieldLabelAssign.setAssigned(fieldLabelAssigned);
			fieldLabelAssign.setAssignment(bot);
			
			CtAssignment objectLabelAssign = coreFactory.createAssignment();
			CtVariableWrite objLabelAssigned = coreFactory.createVariableWrite();
			objLabelAssigned.setVariable(objectLabelfield.getReference());
			objectLabelAssign.setAssigned(objLabelAssigned);
			objectLabelAssign.setAssignment(bot);
			
			CtBlock block = coreFactory.createBlock();
			block.addStatement(fieldLabelAssign);
			block.addStatement(objectLabelAssign);
			
			CtAnonymousExecutable staticInit = coreFactory.createAnonymousExecutable();
			staticInit.setBody(block);
			clazz.addAnonymousExecutable(staticInit);
			*/
//		}
	}
	
	// need to deal with inheritance: for classes that have superclass, do not add object label and field label
	public boolean hasSuperClassProcessed(CtType clazz) {
		CtTypeReference t = clazz.getReference();
		if(t.getSuperclass() == null) {
			return false;
		}
		if(CoInflowCompiler.classesToProcess.contains(t.getSuperclass().getQualifiedName())){
			return true;
		}
		
		return false;
	}

}
