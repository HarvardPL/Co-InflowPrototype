package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnum;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * Locate the elements that need to be renamed and opaque labeled 
 * @author Jian Xiang(jxiang@seas.harvard.edu)
 *
 */
public class ElementToBeRewrittenProcessor extends AbstractProcessor{

		@Override
		public void process(CtElement element) {
			CodeFactory codeFactory = getFactory().Code();
			if(codeFactory == null) {
				return;
			}
			
			if(element.getParent() instanceof CtBlock) {
				return;
			}
			
			if(element instanceof CtInvocation) {
				CtInvocation ctInv = (CtInvocation)element;
				if(ctInv.getTarget()!= null && RewriteHelper.isCoInFlowLibrary(ctInv.getTarget().getType())) {
					return;
				}
				if(ctInv.getExecutable().getSimpleName().startsWith(ReorganizeInitilizationProcessor.method_prefix)) {
					return;
				}
				if(ctInv.getType()!= null && ! ctInv.getType().toString().equals("void") ) {
					if(RewriteHelper.checkIfContinueProcess(element)) {
						RewriteHelper.createNewLocalVarToBeRewritten(ctInv, codeFactory);
					}
				}
			}
			else if(element instanceof CtConstructorCall) {
				if(element.isImplicit() || 
						(element.getParent()!=null && element.getParent() instanceof CtEnumValue)) {
					// if this is implicit, such as Enum, we don't deal with them
					return;
				}
				CtConstructorCall ctConsCall = (CtConstructorCall)element;
				// inner class declaration
				if(ctConsCall.getExecutable().getDeclaringType().isAnonymous()) {
					return;
				}
				String clazz = ctConsCall.getExecutable().
						getDeclaringType().getQualifiedName();
				
				if(ctConsCall.getExecutable().
						getDeclaringType()!= null && RewriteHelper.isCoInFlowLibrary(ctConsCall.getExecutable().
								getDeclaringType())) {
					return;
				}
				
				if(!RewriteHelper.ifClassBeingRewritten(clazz)) {
					// if this is a legacy library class
					// create a new local variable
					RewriteHelper.createNewObjVar(ctConsCall);
				}else if(! ctConsCall.getType().toString().equals("void")){
//						&& RewriteHelper.checkIfContinueProcess(element)) {
					RewriteHelper.createNewLocalVarToBeRewritten(ctConsCall, codeFactory);
				}
			}
			else if (element instanceof CtNewArray) {
				// process array read
				CtNewArray ctAR = (CtNewArray)element;
//				RewriteHelper.createNewLocalVarToBeRewritten(ctAR, codeFactory);
				RewriteHelper.createNewObjVar(ctAR);
			}else if (element instanceof CtFieldRead) {
				CtFieldRead fr = (CtFieldRead)element;
				if(fr.getTarget() instanceof CtSuperAccess) {
					CtFieldReference f = fr.getVariable();
					CtTypeReference tf = ((CtSuperAccess)fr.getTarget()).getType();
					RewriteHelper.createNewLocalVarToBeRewritten(fr, codeFactory);
				}
				else if(fr.getTarget()!= null && RewriteHelper.isCoInFlowLibrary(fr.getTarget().getType())) {
					return;
				}
				else if(fr.getTarget() instanceof CtTypeAccess) {
					RewriteHelper.createNewLocalVarToBeRewritten(fr, codeFactory);
				} 
				else if(! fr.getType().toString().equals("void") ) {
					RewriteHelper.createNewLocalVarToBeRewritten(fr, codeFactory);
				}
			}else if (element instanceof CtArrayRead) {
				CtArrayRead arrayRead = (CtArrayRead)element;
				if(RewriteHelper.isCoInFlowLibrary(arrayRead.getType())) {
					return;
				}
				if(! arrayRead.getType().toString().equals("void") ) {
					RewriteHelper.createNewLocalVarToBeRewritten(arrayRead, codeFactory);
				}
			}
		}
}
