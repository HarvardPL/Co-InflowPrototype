package lbs.harvard.coinflow.compiler;

import java.util.HashSet;
import java.util.Set;

import lbs.harvard.coinflow.internal.IFCUtil;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;

public class LocalVariableRefCountProcessor extends AbstractProcessor<CtVariableAccess> {

	public static Set<CtVariableReference> referredLocals = new HashSet<>();

	@Override
	public void process(CtVariableAccess access) {
		if(access instanceof CtVariableRead) {
			CodeFactory codeFactory = getFactory().Code();
			CtVariable decl = access.getVariable().getDeclaration();
			if(access.getVariable() instanceof CtLocalVariableReference){
				referredLocals.add(access.getVariable());
			}	
		}
	}

}
