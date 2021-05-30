package lbs.harvard.coinflow.compiler;

import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class CtForEachProcessor extends AbstractProcessor<CtForEach> {

	
	static String forEachLocal_prefix = "$$_for_var";
	static int idx = 0;
			
	@Override
	public void process(CtForEach forEach) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		
		CtStatement body = forEach.getBody();
		CtLocalVariable v = forEach.getVariable();
		String newLocalName = forEachLocal_prefix + idx++;
		
		addLocalForLoopVar(codeFactory, coreFactory, 
				body, newLocalName, v);
	}
	
	public static void addLocalForLoopVar(CodeFactory codeFactory, CoreFactory coreFactory, 
			CtStatement body, String newLocalName, CtLocalVariable v) {
		CtLocalVariableReference ref = codeFactory.createLocalVariableReference(v);
		CtLocalVariable newVarDecl = codeFactory.createLocalVariable(v.getType(), newLocalName, codeFactory.createVariableRead(ref, false));
		List<CtVariableAccess> variableAccesses = body.getElements(new TypeFilter<>(CtVariableAccess.class));
		for(CtVariableAccess va : variableAccesses) {
			if(va.getVariable() instanceof CtLocalVariableReference) {
				if(va.getVariable() == ref) {
					va.getVariable().setType(newVarDecl.getType());
					va.setVariable(newVarDecl.getReference());	
				}
			}
		}
		body.insertBefore(newVarDecl);
	}

}
