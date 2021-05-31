package lbs.harvard.coinflow.compiler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lbs.harvard.coinflow.CoInflow_channel;
import lbs.harvard.coinflow.CoInflow_channel.SinkTypes;
import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.internal.IFCPolicyInternal.Port;
import lbs.harvard.coinflow.lattice.IFCLabel;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;

/**
 * Record all sinks and sources provided by user annotations
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class RecordChannelProcessor extends AbstractProcessor<CtElement>{
	
	public static Set<String> outputClasses = new HashSet<>();
	public static Set<String> outputMethods = new HashSet<>();
	
	static Set<String> recordedSinks = new HashSet<>();
	static Set<String> recordedSources = new HashSet<>();
	
	static Map<String, String> annotatedStaticSinkLabels = new HashMap<>();
	static Map<String, String> annotatedStaticSourceLabels = new HashMap<>();
	
	@Override
	public void process(CtElement ctElement) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		 
		if(ctElement instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)ctElement;
			String methodSign = ctInv.getExecutable().getSignature();
			
			if(methodSign.startsWith("markClassOutputChannel(")) {
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					outputClasses.add(s.getValue().toString());
				}
			}else if(methodSign.startsWith("markMethodOutputChannel(")) {
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					outputMethods.add(s.getValue().toString());
				}
			}
			
			if(methodSign.startsWith("markFieldAsSink(")){
				// markFieldAsSink(String classQName, String fieldName, IFCLabel sinkLabel)
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral fieldNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String fieldName = fieldNameLiteral.getValue().toString();
						recordedSinks.add(new Port(IFCPolicyInternal.field, className, fieldName, 0).toString());
					}
				}
//				policyIFC.addFieldAsSink(className, fieldName, label);
			}else if (methodSign.startsWith("markMethodParameterAsSink(")) {
				// markMethodParameterAsSink(String classQName, String methodName, int parameterPosition, IFCLabel sinkLabel) {
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral methodNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String sinkMethodSign = methodNameLiteral.getValue().toString();
						if (ctInv.getArguments().get(2) instanceof CtLiteral) {
							CtLiteral positionLiteral = (CtLiteral) ctInv.getArguments().get(2);
							int pos = Integer.valueOf(positionLiteral.getValue().toString());
							recordedSinks.add(new Port(IFCPolicyInternal.parameter, className, sinkMethodSign, pos).toString());
						}
					}
				}
				
			}else if (methodSign.startsWith("markMethodReturnAsSink(")) {
				//markMethodReturnAsSink(String classQName, String methodName, IFCLabel sinkLabel)\
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral methodNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String sinkMethodSign = methodNameLiteral.getValue().toString();
						recordedSinks.add(new Port(IFCPolicyInternal.returnValue, className, sinkMethodSign, 0).toString());
					}
				}
			}else if(methodSign.startsWith("markFieldAsSource(")){
				// markFieldAsSink(String classQName, String fieldName, IFCLabel sinkLabel)
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral fieldNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String fieldName = fieldNameLiteral.getValue().toString();
						recordedSources.add(new Port(IFCPolicyInternal.field, className, fieldName, 0).toString());
					}
				}
//				policyIFC.addFieldAsSink(className, fieldName, label);
			}else if (methodSign.startsWith("markMethodParameterAsSource(")) {
				// markMethodParameterAsSink(String classQName, String methodName, int parameterPosition, IFCLabel sinkLabel) {
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral methodNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String sinkMethodSign = methodNameLiteral.getValue().toString();
						if (ctInv.getArguments().get(2) instanceof CtLiteral) {
							CtLiteral positionLiteral = (CtLiteral) ctInv.getArguments().get(2);
							int pos = Integer.valueOf(positionLiteral.getValue().toString());
							recordedSources.add(new Port(IFCPolicyInternal.parameter, className, sinkMethodSign, pos).toString());
						}
					}
				}
				
			}else if (methodSign.startsWith("markMethodReturnAsSource(")) {
				//markMethodReturnAsSink(String classQName, String methodName, IFCLabel sinkLabel)\
				if(ctInv.getArguments().get(0) instanceof CtLiteral) {
					CtLiteral s = (CtLiteral) ctInv.getArguments().get(0);
					String className = s.getValue().toString();
					if (ctInv.getArguments().get(1) instanceof CtLiteral) {
						CtLiteral methodNameLiteral = (CtLiteral) ctInv.getArguments().get(1);
						String sinkMethodSign = methodNameLiteral.getValue().toString();
						recordedSources.add(new Port(IFCPolicyInternal.returnValue, className, sinkMethodSign, 0).toString());
					}
				}
			}
		}else if(ctElement instanceof CtParameter) {
			// check if this is a sink or source
			CtParameter para = (CtParameter)ctElement;
			CtTypeReference annoTypeRef = codeFactory.createCtTypeReference(CoInflow_channel.class);
			CtAnnotation paraAnnotation = para.getAnnotation(annoTypeRef);
			if(paraAnnotation != null && para.getReference() != null) {
				CtParameterReference ref = para.getReference();
//				CtExecutableReference executableRef = ref.getDeclaringExecutable();
				// why is this null?
				if(para.getParent() instanceof CtMethod) {
					CtMethod method = (CtMethod)para.getParent();
					if(method.getDeclaringType() != null) {
						String className = method.getDeclaringType().getQualifiedName();
						String methodSign = method.getSignature();
						for(int i = 1; i <= method.getParameters().size(); i++) {
							CtParameter p = (CtParameter)method.getParameters().get(i-1);
							if(p.getSimpleName().equals(para.getSimpleName())) {
								Annotation annotation = paraAnnotation.getActualAnnotation();
								CoInflow_channel channel = (CoInflow_channel) annotation;
								if(channel.SinkType() == SinkTypes.SINK) {
									annotatedStaticSinkLabels.put(new Port(IFCPolicyInternal.parameter, className, methodSign, i).toString(), channel.IDOfSinkLabel());
								}else if(channel.SinkType() == SinkTypes.SOURCE) {
									annotatedStaticSourceLabels.put(new Port(IFCPolicyInternal.parameter, className, methodSign, i).toString(), channel.IDOfSinkLabel()); 
								}
								break;
							}
						}
					}
				}
			}
			
		}else if(ctElement instanceof CtMethod) {
			// check if the method return value is sink or source
			CtMethod method = (CtMethod)ctElement;
			CtTypeReference annoTypeRef = codeFactory.createCtTypeReference(CoInflow_channel.class);
			CtAnnotation methodAnnotation = method.getAnnotation(annoTypeRef);
			if(methodAnnotation != null) {
				if(method.getReference() != null) {
					CtExecutableReference ctExecRef = method.getReference();
					if(ctExecRef.getDeclaringType() != null) {
						String className = ctExecRef.getDeclaringType().getQualifiedName();
						String methodSign = ctExecRef.getSignature();
						Annotation annotation = methodAnnotation.getActualAnnotation();
						CoInflow_channel channel = (CoInflow_channel) annotation;
						if(channel.SinkType() == SinkTypes.SINK) {
							annotatedStaticSinkLabels.put(new Port(IFCPolicyInternal.returnValue, className, methodSign, 0).toString(), channel.IDOfSinkLabel());
						}else if(channel.SinkType() == SinkTypes.SOURCE) {
							annotatedStaticSourceLabels.put(new Port(IFCPolicyInternal.returnValue, className, methodSign, 0).toString(), channel.IDOfSinkLabel() );
						}
					}
				}
			}
		}else if(ctElement instanceof CtField) {
			// check if the field value is sink or source
			CtField field = (CtField)ctElement;
			CtTypeReference annoTypeRef = codeFactory.createCtTypeReference(CoInflow_channel.class);
			CtAnnotation fieldAnnotation = field.getAnnotation(annoTypeRef);
			if(fieldAnnotation != null) {
				if(field.getReference() != null && field.getReference().getDeclaringType() != null) {
					CtFieldReference ctFieldRef = field.getReference();
					if(ctFieldRef.getDeclaringType() != null) {
						String className = ctFieldRef.getDeclaringType().getQualifiedName();
						String fieldName = ctFieldRef.getSimpleName();
						Annotation annotation = fieldAnnotation.getActualAnnotation();
						CoInflow_channel channel = (CoInflow_channel) annotation;
						if(channel.SinkType() == SinkTypes.SINK) {
							annotatedStaticSinkLabels.put(new Port(IFCPolicyInternal.field, className, fieldName, 0).toString(), channel.IDOfSinkLabel());
						}else if(channel.SinkType() == SinkTypes.SOURCE) {
							annotatedStaticSourceLabels.put(new Port(IFCPolicyInternal.field, className, fieldName, 0).toString(), channel.IDOfSinkLabel());
						}
					}
				}
			}
		}
		
	}
}
