package lbs.harvard.coinflow.compiler;

import java.util.HashMap;
import java.util.Map;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

public class RemoveDeadLocalProcessor extends AbstractProcessor<CtElement>{

	@Override
	public void process(CtElement element) {
		CodeFactory codeFactory = getFactory().Code();
		// not only delete variable declaration, but also all assignments to this variables
		if(element instanceof CtLocalVariable) {
			CtLocalVariable local = (CtLocalVariable)element;
			if(!LocalVariableRefCountProcessor.referredLocals.contains(local.getReference())) {
				if(!local.getSimpleName().startsWith(RewriteHelper.var_prefix)) {
					local.delete();	
				}
			}
		}else if (element instanceof CtAssignment) {
			CtAssignment assignment = (CtAssignment)element;
			CtExpression lhs = assignment.getAssigned();
			if(lhs instanceof CtVariableWrite) {
				CtVariableWrite cvw = (CtVariableWrite)lhs;
				if(!LocalVariableRefCountProcessor.referredLocals.contains(cvw.getVariable())) {
					if(!cvw.getVariable().getSimpleName().startsWith(RewriteHelper.var_prefix)) {
						assignment.delete();	
					}
				}
			}
			
		}
		
	}
	
	
}
