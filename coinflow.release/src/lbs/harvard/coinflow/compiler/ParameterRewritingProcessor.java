package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.internal.IFCPolicyInternal.Port;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.util.RewriteHelper;
import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.Labeled;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtThrow;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Dealing with the label stack at the callee side: 
 * (1) reading the label stack and 
 * (2) creating new local variables (opaqueLabeled typed) for the parameters
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class ParameterRewritingProcessor extends AbstractProcessor<CtElement>{

	
	public static String para_rewrite_prefix = "$$_para_";
	public static String calleeLabelsArray_prefix = "$$_caller_labels";
	public static Map<CtVariableReference, CtVariableReference> rewrittenMap = new HashMap<>();
	
	@Override
	public void process(CtElement element) {
		
		if(element instanceof CtExecutable) {
			CtExecutable exec = (CtExecutable)element;

			CodeFactory codeFactory = getFactory().Code();
			CoreFactory coreFactory = getFactory().Core();
			
			// Check if this is the main
			if(RewriteHelper.checkIfMain(exec.getReference().getSignature())){
				return;
			}
			
			// executable without a body: lambda expression
			if(exec.getBody() == null) {
				return;
			}
			
			if(exec instanceof CtLambda) {
				return;
			}
			
			List<CtParameter> parameters = exec.getParameters();
			if(parameters.size() > 0) {
				// retrieve the caller's opaque labels
				CtLocalVariable labelVar = createIFCLabelLocalVar(exec);	
				// rewrite the body of methods, create local variables which are labeled parameters
				for(int i = 0; i < parameters.size(); i++) {
					CtParameter para = parameters.get(i);
					if(!para.getSimpleName().equals(calleeLabelsArray_prefix)) {
						createNewLocalVar(para, exec, i, labelVar);	
					}
				}
				if(exec.getParameters().size() > 0) {
					exec.getBody().insertBegin(labelVar);
					CoInflowCompiler.methodProcessed.add(exec.getSignature());
				}
			}
		}
	}
	
	private static CtLocalVariable createIFCLabelLocalVar(CtExecutable method) {
		CodeFactory codeFactory = method.getFactory().Code();
		CoreFactory coreFactory = method.getFactory().Core();
		CtArrayTypeReference arrayT = coreFactory.createArrayTypeReference();
		CtTypeReference labelType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class);
		arrayT.setComponentType(labelType);
		
		CtExecutableReference getCurrentCallerLabelRef = coreFactory.createExecutableReference();
		getCurrentCallerLabelRef.setStatic(true);
		getCurrentCallerLabelRef.setSimpleName("getCurrentCallerLabels");
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		getCurrentCallerLabelRef.setDeclaringType(target);
		
		List argus = new ArrayList();
		argus.add(codeFactory.createLiteral(method.getSignature()));
		argus.add(codeFactory.createLiteral(method.getParameters().size()));
		
		CtInvocation getLabelsExpr = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
				getCurrentCallerLabelRef, argus); 
		
		CtLocalVariable newLocalLabels = codeFactory.createLocalVariable(arrayT, calleeLabelsArray_prefix, getLabelsExpr);
		return newLocalLabels;
//		method.getBody().insertBegin(newLocalLabels);
//		CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newLocalLabels);
//		return ref;
	}
	
	
	
	private static void createNewLocalVar(CtParameter para, CtExecutable method, int idx, CtLocalVariable labels) {
		CodeFactory codeFactory = para.getFactory().Code();
		CoreFactory coreFactory = para.getFactory().Core();
		if(labels.getType() instanceof CtArrayTypeReference) {
			CtArrayTypeReference arrayT = (CtArrayTypeReference)labels.getType();
			CtTypeReference type = para.getType();
			String newLocalName =  para_rewrite_prefix + idx;
			RewriteHelper.removeTypeArgument(type);
			CtExpression newAssign = codeFactory.createVariableRead(para.getReference(), false);
			CtTypeReference opaqueType = LocalVariableProcessor.generatedOpaqueLabeledType(type);
			
			CtExecutableReference execRef = coreFactory.createExecutableReference();
			execRef.setSimpleName("OpaqueLabeled");
		    execRef.setDeclaringType(codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.OpaqueLabeled.class));
		    
			CtConstructorCall newOpaqueRef = coreFactory.createConstructorCall();
			newOpaqueRef.setExecutable(execRef);
			newOpaqueRef.setType(codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.OpaqueLabeled.class));
			List argus = new ArrayList();
			argus.add(newAssign);
			CtArrayRead labelRead = coreFactory.createArrayRead();
			labelRead.setType(codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class));
			CtVariableRead vr = coreFactory.createVariableRead();
			vr.setType(arrayT);
			vr.setVariable(labels.getReference());
			labelRead.setTarget(vr);
			labelRead.setIndexExpression(codeFactory.createLiteral(idx));
			// join the source label if the parameter
			argus.add(joinParameterSourceIfAny(para, method, idx, labelRead));
			newOpaqueRef.setArguments(argus);
			
			CtLocalVariable newVarDecl = codeFactory.createLocalVariable(opaqueType, newLocalName, newOpaqueRef);
			CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
			// store the original type information for later usage
			RewriteHelper.addOriginalTypeMetadata(newVarDecl, type);
			
			List<CtVariableAccess> paraRefs = method.getElements(new TypeFilter<CtVariableAccess>(CtVariableAccess.class));
			for(CtVariableAccess paraRef : paraRefs) {
				if(paraRef.getVariable() instanceof CtParameterReference) {
					if(((CtParameterReference) paraRef.getVariable()).getSimpleName().equals(para.getSimpleName())) {
						// if the parameter reference is part of super(); ignore
						if(!isPartOfSuperCall(paraRef)) {
							paraRef.setType(ref.getType());
							paraRef.setVariable(ref);	
						}
					}
				}

			}
			method.getBody().insertBegin(newVarDecl);
		}
	}
	
	public static CtExpression joinParameterSourceIfAny(CtParameter para, 
			CtExecutable method, int idx, CtExpression arguLabelExpr) {
		CodeFactory codeFactory = para.getFactory().Code();
		CoreFactory coreFactory = para.getFactory().Core();
		
		if(method.getReference() != null && method.getReference().getDeclaringType() != null) {
			String className = method.getReference().getDeclaringType().getQualifiedName();
			String methodName = method.getSignature();
			int paraPos = idx + 1;
			Port p = new Port(IFCPolicyInternal.parameter, className, methodName, paraPos);
			if(RecordChannelProcessor.recordedSources.contains(p.toString())) {
				CtExpression sourceLabelExpr = OutputChannelProcessor.createLabelExprFromPort(codeFactory, coreFactory, p, false);
				
				CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
				CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
				joinLabelRef.setStatic(true);
				joinLabelRef.setSimpleName("joinLabel");
				joinLabelRef.setDeclaringType(target);
				
				List labelArgus = new ArrayList<>();
				labelArgus.add(sourceLabelExpr);
				labelArgus.add(arguLabelExpr);
				CtExpression joinLabelExpr = codeFactory.createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
						labelArgus);
				
			    return joinLabelExpr;
			}else if(RecordChannelProcessor.annotatedStaticSourceLabels.containsKey(p.toString())) {
				CtExpression sourceLabelExpr = OutputChannelProcessor.createGetLabelFromIDExpr(codeFactory, coreFactory, 
						RecordChannelProcessor.annotatedStaticSourceLabels.get(p.toString()));
				CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
				CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
				joinLabelRef.setStatic(true);
				joinLabelRef.setSimpleName("joinLabel");
				joinLabelRef.setDeclaringType(target);
				
				List labelArgus = new ArrayList<>();
				labelArgus.add(sourceLabelExpr);
				labelArgus.add(arguLabelExpr);
				CtExpression joinLabelExpr = codeFactory.createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
						labelArgus);
				
			    return joinLabelExpr;
			}
			
		}
		
		return arguLabelExpr;
	}
	
	public static boolean isPartOfSuperCall(CtExpression expr) {
		CtElement tmp = RewriteHelper.getTopLevelStmt(expr);
		if(tmp == null || tmp.getParent() == null){
			return false;
		}
		if(tmp instanceof CtInvocation) {
			CtInvocation ctv = (CtInvocation)tmp;
			if(ctv.getExecutable().getSimpleName().startsWith("<init>")) {
				return true;
			}
			return false;
		}
		return false;
	}
}
