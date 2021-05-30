package lbs.harvard.coinflow.compiler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.internal.core.util.HandleFactory;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAnnotationFieldAccess;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtAssert;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtBreak;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtCatchVariable;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtContinue;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtJavaDoc;
import spoon.reflect.code.CtJavaDocTag;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtSynchronized;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtThrow;
import spoon.reflect.code.CtTry;
import spoon.reflect.code.CtTryWithResource;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtAnnotationMethod;
import spoon.reflect.declaration.CtAnnotationType;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnum;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtModule;
import spoon.reflect.declaration.CtModuleRequirement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtPackageDeclaration;
import spoon.reflect.declaration.CtPackageExport;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtProvidedService;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.declaration.CtUsedService;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;


public class LoopEffectProcessor extends AbstractProcessor<CtElement>{

	public static String loop_var_prefix = "$_loop_ifc_";
	public static int counter = 0;
	
	public static void processLoopForLocalVars(CtLoop loop) {
		// 1. find all free variables that are modified by the loop
		// 2. create assignments for these variables with their values in the map, 
		//	insert these assignments before the loop body
		// 3. delete all entries corresponding to the these modified variables in the map
		if(loop.getBody() instanceof CtBlock) {
			CtBlock loopBlock = (CtBlock)loop.getBody();
			for(CtStatement stmt : loopBlock.getStatements()) {
				if(stmt instanceof CtLoop) {
					// what happen for inner loop?
					// for local variables affected by inner loop; 
					processLoopForLocalVars((CtLoop)stmt);
				}else if(stmt instanceof CtAssignment) {
					CtAssignment assignment = (CtAssignment)stmt;
					if(assignment.getAssigned() instanceof CtVariableWrite) {
						CtVariableWrite lhs = (CtVariableWrite)assignment.getAssigned();
//						if(transformedLocalVarRef.containsKey(lhs.getVariable())) {
//							// delete the entry
//							transformedLocalVarRef.remove(lhs.getVariable());
//						}
					}
				}
				else if (stmt instanceof CtLocalVariable) {
					
				}
			}
		}
	}


	@Override
	public void process(CtElement element) {
		if(element instanceof CtLoop) {
			findAllFreeVariables((CtLoop)element);
		}
		CodeFactory codeFactory = element.getFactory().Code();
		CoreFactory coreFactory = element.getFactory().Core();
//		if(element instanceof CtVariableAccess) {
//			CtVariableAccess va = (CtVariableAccess)element;
//			if(newMapping.containsKey(va.getVariable())) {
//				CtExpression newVr = codeFactory.createVariableRead(newMapping.get(va.getVariable()), false);
//				va.replace(newVr);
//			}
//		}
		
	}
	
	public static Set<CtVariableReference> effectedFreeVariabled(CtLoop loop){
		Iterable<CtElement> elements = loop.asIterable();
		// this function asIterable iterates all elements
		
		Set<CtVariableReference> variablesRead = new HashSet<>();
		Set<CtVariableReference> variablesWrite = new HashSet<>();
		Set<CtVariableReference> variablesDeclared = new HashSet<>();
		Iterator<CtElement> it = elements.iterator();
		while(it.hasNext()) {
			CtElement element = it.next();
			if(element instanceof CtVariableRead) {
				CtVariableRead vr = (CtVariableRead)element;
				variablesRead.add(vr.getVariable());
			}else if(element instanceof CtVariableWrite) {
				CtVariableWrite vw = (CtVariableWrite)element;
				variablesWrite.add(vw.getVariable());
			}else if(element instanceof CtLocalVariable) {
				CtLocalVariable v = (CtLocalVariable) element;
				variablesDeclared.add(v.getReference());
			}
		}
		// delete all bounded variables
		for (CtVariableReference ref : variablesDeclared) {
			variablesWrite.remove(ref);
		}
		// what's left in variablesread are all free variables
		// now we get all variables that are affected by the loop
		
		return variablesWrite;
	}
	
	public static void findAllFreeVariables(CtLoop loop){
		Set<CtVariableReference> effectedFreeVars = effectedFreeVariabled(loop);
		// we insert new local variables for these free variables, and then
		// redirect references to the free variables to these newly created variables
		CodeFactory codeFactory = loop.getFactory().Code();
		CoreFactory coreFactory = loop.getFactory().Core();
		Map<CtVariableReference, CtVariableReference> newMapping = new HashMap<>();
		for(CtVariableReference ref : effectedFreeVars) {
			// create a new local variable
			CtTypeReference type = ref.getType();
			String newLocalName =  loop_var_prefix + counter++;
			RewriteHelper.removeTypeArgument(type);
			CtVariableReference refOldVar = ref.clone();
			CtExpression vr = codeFactory.createVariableRead(ref, false);
			CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, vr);
			
//			addTypeCase(, vr);
			CtVariableReference refNewVar = codeFactory.createLocalVariableReference(newVarDecl);
			newMapping.put(ref, refNewVar);
			// before loop body: insert newLocal = xxx;
			RewriteHelper.insertStatements(loop, newVarDecl, true);
		}
		Iterable<CtElement> elements = loop.asIterable();
		Iterator<CtElement> it = elements.iterator();
		it = elements.iterator();
		while(it.hasNext()) {
			CtElement element = it.next();
			if(element instanceof CtVariableAccess) {
				CtVariableAccess va = (CtVariableAccess)element;
				if(newMapping.containsKey(va.getVariable())) {
					if(element instanceof CtVariableRead) {
						CtExpression newVr = codeFactory.createVariableRead(newMapping.get(va.getVariable()), false);
						va.replace(newVr);	
					}else if(element instanceof CtVariableWrite) {
						CtVariableWrite newW = coreFactory.createVariableWrite();
						newW.setVariable(newMapping.get(va.getVariable()));
						va.replace(newW);
					}
					
				}
			}
		}
		for(CtVariableReference ref : newMapping.keySet()) {
			// after loop body: insert xxx = newlocal;
			CtVariableAccess r = codeFactory.createVariableRead(newMapping.get(ref), false);
			CtAssignment newAssign = codeFactory.createVariableAssignment(ref.clone(), false, r);
			CtVariableWrite w = coreFactory.createVariableWrite();
			w.setVariable(ref.clone());
			newAssign.setAssigned(w);
			RewriteHelper.insertStatements(loop, newAssign, false);
		}
	}
}
