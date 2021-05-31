package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * link references of old parameters to newly created local variables. 
 * These newly created local variables are opaqueLabeled typed
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class ParameterRefProcessor extends AbstractProcessor<CtElement>{


	@Override
	public void process(CtElement element) {
		CoreFactory coreFactory = getFactory().Core();
		CodeFactory codeFactory = getFactory().Code();
		
		if(element instanceof CtVariableRead) {
			CtVariableRead variableR = (CtVariableRead)element;
			if(variableR.getVariable().getSimpleName().startsWith(ParameterRewritingProcessor.para_rewrite_prefix)) {
				if( element.getParent() instanceof CtInvocation) {
					CtInvocation parentInv = (CtInvocation)element.getParent();
					if(parentInv.getExecutable() != null && parentInv.getExecutable().getSimpleName().equals("unlabelOpaque")) {
						return;
					}
				}
				RewriteHelper.rewriteToUnlabelOpaque(variableR, coreFactory, codeFactory);
			}
		}else if (element instanceof CtAssignment) {
			CtAssignment assignment = (CtAssignment)element;
			CtExpression left = assignment.getAssigned();
			CtExpression right = assignment.getAssignment();
			
			if(left instanceof CtVariableWrite) {
				CtVariableWrite vw = (CtVariableWrite)left;
				if(vw.getVariable().getSimpleName().startsWith(ParameterRewritingProcessor.para_rewrite_prefix)) {
					if(!vw.getType().getSimpleName().startsWith("OpaqueLabeled")) {
						return;
					}
//					
//					CtTypeReference exceptionRef = codeFactory.createCtTypeReference(Exception.class);
//					if(varDel.getType().isSubtypeOf(exceptionRef)){
//						return ;
//					}
					RewriteHelper.rewriteToOpaqueLabeled(right, right.getFactory().Core(), right.getFactory().Code(), left.getType());
				}
			}
		}
	}
}
