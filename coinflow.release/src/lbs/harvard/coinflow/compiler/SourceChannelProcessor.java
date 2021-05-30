package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import lbs.harvard.coinflow.internal.IFCPolicyInternal;
import lbs.harvard.coinflow.internal.IFCPolicyInternal.Port;
import lbs.harvard.coinflow.util.RewriteHelper;
import lbs.harvard.coinflow.internal.IFCUtil;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
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

/**
 * This process join sources'labels if exist. This processor should be processed before label Check. 
 * @author llama_jian
 *
 */
public class SourceChannelProcessor extends AbstractProcessor<CtElement>{
	@Override
	public void process(CtElement ctElement) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if (ctElement instanceof CtFieldAccess) {
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
			if(ctElement instanceof CtFieldRead) {
				if(RecordChannelProcessor.recordedSources.contains(p.toString())) {
					CtExpression sourceLabelExpr = OutputChannelProcessor.createLabelExprFromPort(codeFactory, coreFactory, p, true);
					createFieldReadSourceControl(codeFactory, coreFactory, fa, p, sourceLabelExpr);
				}else if(RecordChannelProcessor.annotatedStaticSourceLabels.containsKey(p.toString())) {
					createFieldReadSourceControl(codeFactory, coreFactory, fa, p, 
							OutputChannelProcessor.createGetLabelFromIDExpr(codeFactory, coreFactory, RecordChannelProcessor.annotatedStaticSourceLabels.get(p.toString())));
				}
			}
		}else if(ctElement instanceof CtInvocation) {
			// check sink: method call parameters
			CtInvocation ctInv = (CtInvocation)ctElement;
			if(ctInv.getExecutable().getDeclaringType() != null) {
				String className = ctInv.getExecutable().getDeclaringType().getQualifiedName();
				String methodSign = ctInv.getExecutable().getSignature();
				// check if parameters are sinks
				Port p = new Port(IFCPolicyInternal.returnValue, className, methodSign, 0);
//				for(String s : RecordChannelProcessor.annotatedStaticSourceLabels.keySet()) {
//					System.out.println(s);
//				} 
				if(RecordChannelProcessor.recordedSources.contains(p.toString())) {
					CtExpression labelExpr = OutputChannelProcessor.createLabelExprFromPort(codeFactory, coreFactory, p, true);
					createMethodReturnSourceControl(codeFactory, coreFactory, ctInv, labelExpr);
				}else if(RecordChannelProcessor.annotatedStaticSourceLabels.containsKey(p.toString())) {
					// if the programmers have annotated the sink label
					CtExpression sinkLabelExpr = OutputChannelProcessor.createGetLabelFromIDExpr(
							codeFactory, coreFactory, RecordChannelProcessor.annotatedStaticSourceLabels.get(p.toString())); 
					createMethodReturnSourceControl(codeFactory, coreFactory, ctInv, sinkLabelExpr);
				}
			}
		}
	}
	
	private static void createMethodReturnSourceControl(CodeFactory codeFactory, CoreFactory coreFactory, CtInvocation ctInv, CtExpression labelExpr) {
		if(ctInv.getParent() instanceof CtInvocation) {
			CtInvocation parentInv = (CtInvocation)ctInv.getParent();
			if(parentInv.getExecutable() != null && parentInv.getExecutable().getSimpleName().startsWith("toOpaqueLabeled")) {
				CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
				CtExecutableReference checkOutChannelRef = coreFactory.createExecutableReference();
				checkOutChannelRef.setStatic(true);
				checkOutChannelRef.setSimpleName("toOpaqueLabeledSource");
				checkOutChannelRef.setDeclaringType(target);
				
				List argus = new ArrayList<>();
				argus.add(ctInv.clone());
				argus.add(labelExpr);
				CtStatement newInv = codeFactory.createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), checkOutChannelRef, 
						argus);
				parentInv.replace(newInv);;
			}
			
		}
	}

	/**
	 * create the code to check output channel on classes
	 */
	private static void createFieldReadSourceControl(CodeFactory codeFactory, CoreFactory coreFactory, CtElement ctElement, Port p, CtExpression labelExpr) {
		
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference checkOutChannelRef = coreFactory.createExecutableReference();
		checkOutChannelRef.setStatic(true);
		checkOutChannelRef.setSimpleName("joinTopLabel");
		checkOutChannelRef.setDeclaringType(target);
		
		List argus = new ArrayList<>();
		argus.add(labelExpr);
		CtStatement checkOutChannelExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), checkOutChannelRef, 
				argus);
		
	    CtStatement stmt = RewriteHelper.getTopLevelStmt(ctElement);
	    RewriteHelper.insertStatements(stmt, checkOutChannelExpr, true);
	}
}
 