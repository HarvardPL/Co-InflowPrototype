package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class FieldReadProcessor extends AbstractProcessor<CtFieldRead>{
		@Override
		public void process(CtFieldRead element) {
			CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();
			CodeFactory codeFactory = getFactory().Code();
			if(codeFactory == null) {
				return;
			}
			
			if(element.getTarget() instanceof CtTypeAccess) {
				return;
			}
			
			if(element.getTarget().getType().toString().equals("void") ||
					(//(! (element.getTarget() instanceof CtThisAccess)) && 
						//	(! (element.getTarget() instanceof CtSuperAccess)) &&
							(element.getTarget().getType() != null) &&
							RewriteHelper.checkIfContinueProcess(element)
					)){
				
				CtTypeReference type = element.getType();
				String newLocalName = RewriteHelper.var_prefix + CoInflowCompiler.counter++;
				
				CtElement tmp = element;				
				CtFieldRead replacement = element.clone();
				while (! (tmp.getParent() instanceof CtBlock)) {
					tmp = tmp.getParent();
				}
				CtExpression newAssignment = replacement;
						
				CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, newAssignment);
				
				CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
				CtVariableAccess vr = codeFactory.createVariableRead(ref, false);
				element.replace(vr);
				
				RewriteHelper.insertStatements(((CtStatement)tmp), newVarDecl, true);
				
			}
			}
			
}
