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
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class LocalVariableRefProcessor extends AbstractProcessor<CtVariableRead> {

	private Set<CtLocalVariable> transformedLocalVar = new HashSet<>();

	public LocalVariableRefProcessor(Set<CtLocalVariable> transformed) {
		transformedLocalVar = transformed;
	}
	
	@Override
	public void process(CtVariableRead ref) {
		CodeFactory codeFactory = getFactory().Code();
		CtVariable decl = ref.getVariable().getDeclaration();
		if(transformedLocalVar.contains(decl)) {
		    CtExecutableReference executableReference = getFactory().Core().createExecutableReference();
		    executableReference.setStatic(true);
		    executableReference.setSimpleName("unlabelOpaque");
		    CtTypeReference target = getFactory().Code().createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		    executableReference.setDeclaringType(target);
		    executableReference.setType(ref.getType());
		    
		    CtVariableAccess access =  getFactory().Code().createVariableRead(decl.getReference(), false);
			CtExpression newInv = getFactory().Code().createInvocation(
					getFactory().Code().createTypeAccessWithoutCloningReference(target), executableReference, access);
		    
		    
			ref.replace(newInv);
		}
	
	}

}
