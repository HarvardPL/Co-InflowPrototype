package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class FieldWriteProcessor extends AbstractProcessor<CtAssignment> {

	@SuppressWarnings("unchecked")
	@Override
	public void process(CtAssignment fieldWrite) {
		
		CodeFactory codeFactory = getFactory().Code();
		
		if(fieldWrite.getAssigned() instanceof CtFieldWrite && fieldWrite.getAssignment() instanceof CtInvocation) {
			
			CtFieldWrite lhs = (CtFieldWrite) fieldWrite.getAssigned();
			CtInvocation rhs = (CtInvocation) fieldWrite.getAssignment();
			
			
			if(rhs.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.ifc.IFCUtil.unlabelOpaque")) {
				//only work for field write from unlabelOpaque
				CtExecutableReference executableReference = getFactory().Core().createExecutableReference();
			    executableReference.setStatic(true);
			    executableReference.setSimpleName("opaqueFieldWrite");
			    CtTypeReference target = getFactory().Code().createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			    executableReference.setDeclaringType(target);
			    
			    CtTypeReference opaqueType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.OpaqueLabeled.class);
			    executableReference.setType(opaqueType);	
			    
			    CtExpression newCall = getFactory().Code().createInvocation(
						getFactory().Code().createTypeAccessWithoutCloningReference(target), executableReference, fieldWrite.getAssignment());
			    
			    fieldWrite.setAssignment(newCall);
			}
		}else if (fieldWrite.getAssigned() instanceof CtFieldWrite ) {
			
		}
		
	}
	
	
	
}
