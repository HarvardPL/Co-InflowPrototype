package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.internal.IFCPolicyInternal.Port;
import lbs.harvard.coinflow.util.RewriteHelper;
import lbs.harvard.coinflow.internal.IFCUtil;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class OutputChannelProcessor extends AbstractProcessor<CtElement>{
	@Override
	public void process(CtElement ctElement) {
		
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(ctElement instanceof CtAbstractInvocation) {
			// check sink: method call parameters
			CtAbstractInvocation ctInv = (CtAbstractInvocation)ctElement;
			if(ctInv.getExecutable().getDeclaringType() != null) {
				String className = ctInv.getExecutable().getDeclaringType().getQualifiedName();
				String methodSign = ctInv.getExecutable().getSignature();
				// check if parameters are sinks
				for(int i = 1; i <= ctInv.getArguments().size(); i++) {
					Port p = new Port(IFCPolicyInternal.parameter, className, methodSign, i);
					if(p.toString().contains("print")) {
						System.out.println();
					}
					if(RecordChannelProcessor.recordedSinks.contains(p.toString())) {
						CtExpression sinkLabelExpr = createLabelExprFromPort(codeFactory, coreFactory, p, true);
						createMethodParameterSinkCheck(codeFactory, coreFactory, ctInv, p, sinkLabelExpr);
					}else if(RecordChannelProcessor.annotatedStaticSinkLabels.containsKey(p.toString())) {
						// if the programmers have annotated the sink label
						CtExpression sinkLabelExpr = createGetLabelFromIDExpr(codeFactory, coreFactory, 
								RecordChannelProcessor.annotatedStaticSinkLabels.get(p.toString())); 
						createMethodParameterSinkCheck(codeFactory, coreFactory, ctInv, p, sinkLabelExpr);
					}
				}
				// if the parameter is 0, we also check 
				Port p = new Port(IFCPolicyInternal.parameter, className, methodSign, 0);
				if(RecordChannelProcessor.recordedSinks.contains(p.toString())) {
					CtExpression sinkLabelExpr = createLabelExprFromPort(codeFactory, coreFactory, p, true);
					createMethodParameterSinkCheck(codeFactory, coreFactory, ctInv, p, sinkLabelExpr);
				}else if(RecordChannelProcessor.annotatedStaticSinkLabels.containsKey(p.toString())) {
					// if the programmers have annotated the sink label
					CtExpression sinkLabelExpr = createGetLabelFromIDExpr(codeFactory, coreFactory, 
							RecordChannelProcessor.annotatedStaticSinkLabels.get(p.toString())); 
					createMethodParameterSinkCheck(codeFactory, coreFactory, ctInv, p, sinkLabelExpr);
				}
			}
		}else if (ctElement instanceof CtFieldAccess) {
			// check sink: field writes
			CtFieldAccess fa = (CtFieldAccess) ctElement;
			String className = "";
			String fieldName = fa.getVariable().getSimpleName();
			if(fa.getTarget() instanceof CtTypeAccess) {
				// class/static field:   instanceof CttypeAccess
				CtTypeAccess classCalled = (CtTypeAccess)fa.getTarget();
				className = classCalled.getAccessedType().getQualifiedName();
			}else {
				// object field: instanceof 
				className = fa.getTarget().getType().getQualifiedName();
			}
			Port p = new Port(IFCPolicyInternal.field, className, fieldName, 0);
			if(ctElement instanceof CtFieldWrite) {
				if(RecordChannelProcessor.recordedSinks.contains(p.toString())) {
					CtExpression sinkLabelExpr = createLabelExprFromPort(codeFactory, coreFactory, p, true);
					createFieldWriteSinkCheck(codeFactory, coreFactory, fa, p, sinkLabelExpr);
				}else if(RecordChannelProcessor.annotatedStaticSinkLabels.containsKey(p.toString())) {
					createFieldWriteSinkCheck(codeFactory, coreFactory, fa, p, 
							createGetLabelFromIDExpr(codeFactory, coreFactory, RecordChannelProcessor.annotatedStaticSinkLabels.get(p.toString())));
				}
			}
		}else if (ctElement instanceof CtReturn) {
			CtReturn ctReturn = (CtReturn)ctElement;
			CtElement executableParent = ctReturn.getParent();
			while(! (executableParent instanceof CtExecutable)) {
				executableParent = executableParent.getParent();
			}
			CtExecutable ctMethod = (CtExecutable)executableParent;
			if(ctMethod.getReference() == null || ctMethod.getReference().getDeclaration() == null) {
				return;
			}
			CtExecutable ctExec = ctMethod.getReference().getDeclaration();
			if(ctExec.getReference()!= null && ctExec.getReference().getDeclaringType() != null ) {
				String className = ctExec.getReference().getDeclaringType().getQualifiedName();
				String methodName = ctExec.getSignature();
				// check if return value are sinks
				Port p = new Port(IFCPolicyInternal.returnValue, className, methodName, 0);
				if(RecordChannelProcessor.recordedSinks.contains(p.toString())) {
					CtExpression sinkLabelExpr = createLabelExprFromPort(codeFactory, coreFactory, p, true);
					createMethodReturnSinkCheck(codeFactory, coreFactory, ctReturn, p, sinkLabelExpr);
				}
				if(RecordChannelProcessor.annotatedStaticSinkLabels.containsKey(p.toString())) {
						createMethodReturnSinkCheck(codeFactory, coreFactory, ctReturn, p, 
								createGetLabelFromIDExpr(codeFactory, coreFactory, RecordChannelProcessor.annotatedStaticSinkLabels.get(p.toString())));
				}
				if(RecordChannelProcessor.recordedSources.contains(p.toString())) {
					// what to do here?
//					createMethodReturnSinkCheck(codeFactory, coreFactory, ctReturn, className, methodName);
				}
			}
			
		}
	}


	private static void createFieldWriteSinkCheck(CodeFactory codeFactory, CoreFactory coreFactory, CtElement ctElement, Port p, CtExpression sinkLabelExpr) {
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference checkOutChannelRef = coreFactory.createExecutableReference();
		checkOutChannelRef.setStatic(true);
		checkOutChannelRef.setSimpleName("checkIfFlowToSink");
		checkOutChannelRef.setDeclaringType(target);
		
		List argus = new ArrayList<>();
		argus.add(sinkLabelExpr);
		CtStatement checkOutChannelExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), checkOutChannelRef, 
				argus);
	    CtStatement stmt = RewriteHelper.getTopLevelStmt(ctElement);
	    RewriteHelper.insertStatements(stmt, checkOutChannelExpr, true);
	}

	
	public static CtExpression createLabelExprFromPort(CodeFactory codeFactory, CoreFactory coreFactory, Port p, boolean sinkOrSource) {
			// generate the argument label to be checked against
			CtTypeReference IFCPolicyInternalType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCPolicyInternal.class);
			CtExecutableReference getPortLabel = coreFactory.createExecutableReference();
			getPortLabel.setStatic(true);
			String className = p.getClassName();
			String callingMethodName = "";
			List parameterLabelArgus = new ArrayList<>();
			CtLiteral classNameLiteral = coreFactory.createLiteral();
			classNameLiteral.setValue(className);
			parameterLabelArgus.add(classNameLiteral);
			CtLiteral fieldOrMethodNameLiteral = coreFactory.createLiteral();
			fieldOrMethodNameLiteral.setValue(p.getMethodOrFieldName());
			parameterLabelArgus.add(fieldOrMethodNameLiteral);
			if(p.getKind().equals(IFCPolicyInternal.field)){
				callingMethodName = sinkOrSource ? "getFieldSink" : "getFieldSource";
			}else if (p.getKind().equals(IFCPolicyInternal.parameter)) {
				callingMethodName = sinkOrSource ? "getParameterSink" : "getParameterSource";
				CtLiteral posLiteral = coreFactory.createLiteral();
				posLiteral.setValue(p.getParameterPositionOnly());
				parameterLabelArgus.add(posLiteral);
			}else if (p.getKind().equals(IFCPolicyInternal.returnValue)) {
				callingMethodName = sinkOrSource ? "getReturnValSink" : "getReturnValSource";
			}
			getPortLabel.setSimpleName(callingMethodName);
			getPortLabel.setDeclaringType(IFCPolicyInternalType);
			
			CtExpression labelExpr = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(IFCPolicyInternalType), 
					getPortLabel, parameterLabelArgus);
			return labelExpr;
	}
	
	public static String var_prefix = "$_outc_";
	public static int counter = 0;
	
	/**
	 * create the code to check output channel on methods
	 */
	public static void createMethodParameterSinkCheck(
			CodeFactory codeFactory, CoreFactory coreFactory, 
			CtAbstractInvocation ctInv,
			Port p, CtExpression sinkLabelExpr) {
		
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference checkOutChannelRef = coreFactory.createExecutableReference();
		checkOutChannelRef.setStatic(true);
		checkOutChannelRef.setSimpleName("checkIfFlowToSink");
		checkOutChannelRef.setDeclaringType(target);
		
		
		CtStatement stmt = RewriteHelper.getTopLevelStmt(ctInv);
		Object arg = null;
		// first argument: the label of the argument
		if(p.getParameterPositionOnly() == 0) {
			if(ctInv instanceof CtInvocation) {
				arg = ((CtInvocation)ctInv).getTarget();
			}else {
				return;
			}
		}else {
			// the rifl files index parameters from 1 instead of 0
			arg = ctInv.getArguments().get(p.getParameterPositionOnly() - 1);
		}
		
		
		if(arg instanceof CtExpression) {
			CtExpression argExpr = (CtExpression) arg;
			if(argExpr instanceof CtInvocation) {
				CtInvocation argInv = (CtInvocation)argExpr;
				if(argInv.getExecutable().getSignature().startsWith("unlabelOpaque(") || 
						argInv.getExecutable().getSignature().startsWith("opaqueValueOf") ) {
					if(argInv.getArguments().get(0) instanceof CtExpression) {
						// add check to ensure the opaque label flows to the output channel
						List argus = new ArrayList<>();
						CtExpression opaLabeledArgu = (CtExpression)argInv.getArguments().get(0);
						argus.add(RewriteHelper.createOpaqueLabelOfExp(opaLabeledArgu));
						argus.add(sinkLabelExpr);
						CtStatement checkOpaqueLabelFlows = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
								checkOutChannelRef, argus);
						addCheckFlowStmt(ctInv, checkOpaqueLabelFlows);
					}
				}
				
				if(argInv.getType()!=null && !argInv.getType().isPrimitive()) {
					// if an  object  is passed to the output channel, check if the field label flows
					CtExpression tmp = argExpr;
					CtExpression fieldLabelArgu = RewriteHelper.buildFieldLabelExpr(tmp);
					List argus = new ArrayList<>();
					argus.add(fieldLabelArgu);
					// generate the second argument: label to be checked against
					argus.add(sinkLabelExpr);
					CtStatement methodParameterCheckExpr = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
							checkOutChannelRef, argus);
					addCheckFlowStmt(ctInv, methodParameterCheckExpr);
				}
			}
		}
			
		
		
	}
	
	public static void addCheckFlowStmt(CtAbstractInvocation ctInv, CtStatement newStatement) {
		if(ctInv instanceof CtInvocation) {
			RewriteHelper.insertStatements((CtInvocation)ctInv, newStatement, true);
		}else if(ctInv instanceof CtConstructorCall) {
			RewriteHelper.insertStatements((CtConstructorCall)ctInv, newStatement, true);
		}
	}
	
	
	// generate label checks before return statement, if this return statement is a sink
	public static void createMethodReturnSinkCheck(
			CodeFactory codeFactory, CoreFactory coreFactory, 
			CtReturn ctReturn, Port p, CtExpression sinkLabelExpr) {
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference checkOutChannelRef = coreFactory.createExecutableReference();
		checkOutChannelRef.setStatic(true);
		checkOutChannelRef.setSimpleName("checkIfFlowToSink");
		checkOutChannelRef.setDeclaringType(target);
		
		List argus = new ArrayList<>();
		argus.add(sinkLabelExpr);
		CtStatement checkOutChannelExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), checkOutChannelRef, 
				argus);
		
	    CtStatement stmt = RewriteHelper.getTopLevelStmt(ctReturn);
	    RewriteHelper.insertStatements(stmt, checkOutChannelExpr, true);
	
	}
	
	public static CtExpression createGetLabelFromIDExpr(CodeFactory codeFactory, CoreFactory coreFactory, String labelId) {
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference getLabelByIdRef = coreFactory.createExecutableReference();
		getLabelByIdRef.setStatic(true);
		getLabelByIdRef.setSimpleName("getLabelById");
		getLabelByIdRef.setDeclaringType(target);
		CtTypeReference returnType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class);
		getLabelByIdRef.setType(returnType);
		
		List argus = new ArrayList<>();
		CtLiteral idExpr = codeFactory.createLiteral(""+ labelId);
		argus.add(idExpr);
		CtInvocation getLabelByIdExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), getLabelByIdRef, 
				argus);
		getLabelByIdExpr.setExecutable(getLabelByIdRef);
		getLabelByIdExpr.setType(returnType);
//		CtExpression expr = codeFactory.createCodeSnippetExpression("IFCUtil.getLabelById(" + labelId + ")");
//		expr.setType(returnType);
		return getLabelByIdExpr;
	}

}
 