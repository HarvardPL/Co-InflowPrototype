package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtArrayWrite;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtStatementListImpl;

public class ArgumentProcessor extends AbstractProcessor<CtInvocation> {

	CodeFactory codeFactory = null;
	CoreFactory coreFactory  = null;
	
	public static String callerLabelArrayPrefix = "_$$_ifc_array_";
	private static int counter = 0;
	
	private static String arg_prefix = "_$$_arg_";
	
	@Override
	public void process(CtInvocation ctInvocation) {
		codeFactory = getFactory().Code();
		coreFactory = getFactory().Core();
		
		if(ctInvocation.getExecutable().getDeclaringType() != null && 
				! ctInvocation.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
			// Would the following check fail? I have turned it off for now 
			// String classCalled = ctInvocation.getExecutable().getDeclaringType().getQualifiedName();
			// List paras = ctInvocation.getExecutable().getParameters();
			// if(CoInflowCompiler.methodProcessed.contains(ctInvocation.getExecutable().getSignature())) {
				processArgu(ctInvocation.getExecutable(), ctInvocation);
			// }
		}
	}
	
	// We use a stack of labels to pass the opaque labels from the caller to callee. 
	// This method creates the array of labels and passes them to the stack. 
	public static void processArgu(CtExecutableReference methodCalled, CtAbstractInvocation inv){
		if(inv.getArguments().size() == 0) {
			return;
		}
		CodeFactory codeFactory = methodCalled.getFactory().Code();
		CoreFactory coreFactory = methodCalled.getFactory().Core();
		if(methodCalled.isConstructor()) {
			if(! CoInflowCompiler.classesProcessed.contains(methodCalled.getType().getQualifiedName())){
				return;
			}
		}
		CtTypeReference labelT = codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class);
		CtStatementList toAdded = new CtStatementListImpl();
		CtArrayTypeReference arrayListType = coreFactory.createArrayTypeReference();
		arrayListType.setComponentType(labelT);
		CtNewArray ifc_array = coreFactory.createNewArray();
		String var_name = callerLabelArrayPrefix + counter++;
		ifc_array.setType(arrayListType);
		List dimensionList = new ArrayList<>();
		ifc_array.addDimensionExpression(codeFactory.createLiteral(inv.getArguments().size()));
		CtLocalVariable newArray = codeFactory.createLocalVariable(arrayListType, var_name, ifc_array); 
		
		toAdded.addStatement(newArray);
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		
		for(int i = 0; i < inv.getArguments().size(); i++) {
			Object arg = inv.getArguments().get(i);
			if(arg instanceof CtExpression) {
				CtExpression argExpr = (CtExpression) arg;
				if(argExpr instanceof CtInvocation) {
					CtInvocation argInv = (CtInvocation)argExpr;
					if(argInv.getExecutable().getSignature().startsWith("unlabelOpaque(")) {
						if(argInv.getArguments().get(0) instanceof CtExpression) {
							CtExpression opaLabeledArgu = (CtExpression)argInv.getArguments().get(0);
							
							if(opaLabeledArgu instanceof CtInvocation && 
									(((CtInvocation)opaLabeledArgu).getExecutable().getSignature().startsWith("unlabelOpaque("))) {
								opaLabeledArgu = (CtExpression) ((CtInvocation)opaLabeledArgu).getArguments().get(0);
							}
							
							
							
//							CtExecutableReference opaqueLabelOfRef = coreFactory.createExecutableReference();
//							opaqueLabelOfRef.setStatic(true);
//							opaqueLabelOfRef.setSimpleName("opaqueLabelOf");
//							opaqueLabelOfRef.setDeclaringType(target);
//							opaqueLabelOfRef.setParameters(argInv.getActualTypeArguments());
//							List opaqueLabelOfTargets = new ArrayList<>();
//							opaqueLabelOfRef.setType(opaLabeledArgu.getType());
//							opaqueLabelOfTargets.add(opaLabeledArgu.clone());
//							
//							CtExpression tmp = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
//									opaqueLabelOfRef, opaqueLabelOfTargets);
							
							CtExpression tmp = RewriteHelper.createOpaqueLabelOfExp(opaLabeledArgu);
							
							CtArrayWrite aw = coreFactory.createArrayWrite();
							aw.setType(labelT);
							aw.setTarget(codeFactory.createVariableRead(newArray.getReference(), false));
							aw.setIndexExpression(codeFactory.createLiteral(i));
							
							CtAssignment assign = coreFactory.createAssignment();
							assign.setAssigned(aw);
							assign.setAssignment(tmp);
							toAdded.addStatement(assign);
							
							// rewrite the argument to opaqueValueOf
//							CtExecutableReference opaqueValueOfRef = coreFactory.createExecutableReference();
//							opaqueValueOfRef.setStatic(true);
//							opaqueValueOfRef.setSimpleName("opaqueValueOf");
//							opaqueValueOfRef.setDeclaringType(target);
//							opaqueValueOfRef.setActualTypeArguments(argInv.getActualTypeArguments());
//							opaqueValueOfRef.setType(opaLabeledArgu.getType());
//							argInv.setExecutable(opaqueValueOfRef);
//							List opaqueLabelOfTargets_prime = new ArrayList<>();
//							opaqueLabelOfTargets_prime.add(opaLabeledArgu.clone());
//							argInv.setArguments(opaqueLabelOfTargets_prime);
							
							argInv.replace(RewriteHelper.createOpaqueValueOfExp(opaLabeledArgu));
						}
					}else {
						// if not an opaquelabeled value
						CtInvocation bLabel = 
								ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory);
						CtArrayWrite aw = coreFactory.createArrayWrite();
						aw.setType(labelT);
						aw.setTarget(codeFactory.createVariableRead(newArray.getReference(), false));
						aw.setIndexExpression(codeFactory.createLiteral(i));
						
						CtAssignment assign = coreFactory.createAssignment();
						assign.setAssigned(aw);
						assign.setAssignment(bLabel);
						toAdded.addStatement(assign);
					}
				}else {
					CtExpression bLabel = 
							ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory);
					CtArrayWrite aw = coreFactory.createArrayWrite();
					aw.setType(labelT);
					aw.setTarget(codeFactory.createVariableRead(newArray.getReference(), false));
					aw.setIndexExpression(codeFactory.createLiteral(i));
					
					CtAssignment assign = coreFactory.createAssignment();
					assign.setAssigned(aw);
					assign.setAssignment(bLabel);
					toAdded.addStatement(assign);
				}
			}
		}
		CtElement stmt = RewriteHelper.getTopLevelStmt(inv);
		CtExpression extraArgu = codeFactory.createVariableRead(newArray.getReference(), false);
		CtExecutableReference setCurrentCallerRef = coreFactory.createExecutableReference();
		setCurrentCallerRef.setStatic(true);
		setCurrentCallerRef.setSimpleName("setCurrentCallStack");
		setCurrentCallerRef.setDeclaringType(target);
		List callerLabels = new ArrayList<>();
		callerLabels.add(extraArgu);
		
		// add caller method's signature for check
		CtExpression callerSignature = codeFactory.createLiteral(inv.getExecutable().getSignature());
		callerLabels.add(callerSignature);
		
		CtStatement tmp = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
				setCurrentCallerRef, callerLabels);
		toAdded.addStatement(tmp);
		
		RewriteHelper.insertStatements((CtStatement)stmt, toAdded, true);
	}
	
	public static void processArgu(CtExecutableReference methodCalled, CtExpression expr, CoreFactory coreFactory, CodeFactory codeFactory, CtTypeReference paraTypeRef) {
		if(methodCalled.isConstructor()) {
			if(! CoInflowCompiler.classesProcessed.contains(methodCalled.getType().getQualifiedName())){
				return;
			}
		}
		
//		CtExecutable contructorDecl = methodCalled.getDeclaration();
		// if we have modified this method
		if(methodCalled.getExecutableDeclaration() instanceof CtMethod) {
			CtMethod ctMethod = (CtMethod)methodCalled.getExecutableDeclaration();
			for(Object topDef : ctMethod.getTopDefinitions()) {
				CtMethod topDefMethod = (CtMethod)topDef;
				if(!CoInflowCompiler.classesProcessed.contains(topDefMethod.getDeclaringType().getQualifiedName())) {
					return;
					// if we cannot modify this class, we return early
				}
			}
		}
		
		// other cases means we modified the class definition, and all functions are using OpaqueLabeled<> as parameters
		if (expr instanceof CtInvocation) {
			// if this is unlabelOpaque, then strip the unlabelOpaque
			CtInvocation ctInv = (CtInvocation)expr;
			CtExecutable exec = (CtExecutable)ctInv.getExecutable().getDeclaration();
			if(ctInv.getExecutable().getSimpleName().startsWith("unlabelOpaque")){
				expr.replace((CtExpression)ctInv.getArguments().get(0));
			}else {
				RewriteHelper.rewriteToOpaqueLabeled(expr, coreFactory, codeFactory, paraTypeRef);	
			}
		}else {
			RewriteHelper.rewriteToOpaqueLabeled(expr, coreFactory, codeFactory, paraTypeRef);	
		}
		
	}
	
	
	

}
