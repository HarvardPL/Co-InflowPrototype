package lbs.harvard.coinflow.compiler;

import java.lang.reflect.Field;
import java.util.*;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.internal.OpaqueLabeled;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class LabelCheckProcessor extends AbstractProcessor<CtElement>{
	
	public static String bottomString = 
			"lbs.harvard.coinflow.IFCLattice.bot";
	public static String bottomName = "IFCLattice.bot";
	// record all statements that have already added something
	Set<CtStatement> topLevelStmts = new HashSet<>();
	// record insertions for every statement
	Map<String, Set<String>> insertions = new HashMap<>();
	@Override
	public void process(CtElement element) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if (element instanceof CtFieldRead) {
			CtFieldRead ctRead = (CtFieldRead) element;
			// raise the context label
			CtExpression obj = ctRead.getTarget();
			
			// Check if this object has a field named "runtime_object_label"
			try {
				CtStatement stmt = RewriteHelper.getTopLevelStmt(element);
				CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
				CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
				joinLabelRef.setStatic(true);
				joinLabelRef.setSimpleName("joinTopLabel");
				joinLabelRef.setDeclaringType(target);
				
				if(RewriteHelper.ifCoInflowCode(ctRead)) {
					return;
				}
				
				CtExpression readFieldLabel = RewriteHelper.createFieldLabelRead(ctRead, codeFactory);
				if(!readFieldLabel.toString().equals(bottomName) && !readFieldLabel.toString().equals(bottomString)) {
					CtStatement raiseContextLabelExpr = getFactory().Code().createInvocation(
							codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
							readFieldLabel);
					
					String key = stmt.getShortRepresentation();
					insertions.putIfAbsent(key, new HashSet<String>());
					Set<String> inserted = insertions.get(key);
					if(!inserted.contains(raiseContextLabelExpr.toString())) {
						RewriteHelper.insertStatements(stmt, raiseContextLabelExpr, true);
						inserted.add(raiseContextLabelExpr.toString());
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}else if (element instanceof CtAssignment) {
			CtAssignment assignment = (CtAssignment)element;
			CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			if((assignment.getAssigned() instanceof CtFieldWrite)) {
				CtFieldWrite lhs = (CtFieldWrite) assignment.getAssigned();
				if(RewriteHelper.isCoInFlowLibrary(lhs.getType())) {
					return;
				}
				CtExecutableReference checkLabelExprRef = coreFactory.createExecutableReference();
				checkLabelExprRef.setStatic(true);
				checkLabelExprRef.setSimpleName("checkFieldWriteFlow");
				checkLabelExprRef.setDeclaringType(target);
				List argus = new ArrayList<>();
				// add field label as the first argument
				try {
					CtExpression readObjLabel = RewriteHelper.createFieldLabelRead(lhs, codeFactory);
					if(readObjLabel != null) {
						argus.add(readObjLabel);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				if(assignment.getAssignment() instanceof CtInvocation) {
					CtInvocation rhs = (CtInvocation) assignment.getAssignment();
					if(rhs.getExecutable().getSimpleName().startsWith("unlabelOpaque")) {
						CtExpression objCalled = (CtExpression)rhs.getArguments().get(0);
						// get the label of the unlabelOpaque argument
						CtExecutableReference executableReference = coreFactory.createExecutableReference();
					    executableReference.setStatic(true);
					    executableReference.setSimpleName("opaqueLabelOf");
					    executableReference.setDeclaringType(target);
					    CtTypeReference labelT = codeFactory.createCtTypeReference(IFCLabel.class);
						CtExpression opaqueLabelOfExpr = codeFactory.createInvocation(
								codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, objCalled);
						argus.add(opaqueLabelOfExpr);
					}
				}else {
					argus.add(ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory));
				}
				CtStatement checkLabelExpr = getFactory().Code().createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), checkLabelExprRef, argus);
				
			    CtStatement stmt = RewriteHelper.getTopLevelStmt(element);
			    RewriteHelper.insertStatements(stmt, checkLabelExpr, true);
			}else if((assignment.getAssigned() instanceof CtArrayWrite)) {
				CtArrayWrite lhs = (CtArrayWrite) assignment.getAssigned();
				if(lhs.getTarget() instanceof CtVariableRead) {
					CtVariableRead t = (CtVariableRead) lhs.getTarget();
					if(t.getVariable().getSimpleName().startsWith(ParameterRewritingProcessor.para_rewrite_prefix)) {
						return;
					}
				}
				if(RewriteHelper.isCoInFlowLibrary(lhs.getType())) {
					return;
				}
				CtExecutableReference checkLabelExprRef = coreFactory.createExecutableReference();
				checkLabelExprRef.setStatic(true);
				checkLabelExprRef.setSimpleName("checkFieldWriteFlow");
				checkLabelExprRef.setDeclaringType(target);
				List argus = new ArrayList<>();
				// add field label as the first argument
				try {
					CtExpression readObjLabel = RewriteHelper.createFieldLabelArrayRead(lhs);
					if(readObjLabel != null) {
						argus.add(readObjLabel);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				if(assignment.getAssignment() instanceof CtInvocation) {
					CtInvocation rhs = (CtInvocation) assignment.getAssignment();
					if(rhs.getExecutable().getSimpleName().startsWith("unlabelOpaque")) {
						CtExpression objCalled = (CtExpression)rhs.getArguments().get(0);
						// get the label of the unlabelOpaque argument
						CtExecutableReference executableReference = coreFactory.createExecutableReference();
					    executableReference.setStatic(true);
					    executableReference.setSimpleName("opaqueLabelOf");
					    executableReference.setDeclaringType(target);
					    CtTypeReference labelT = codeFactory.createCtTypeReference(IFCLabel.class);
					    
						CtExpression opaqueLabelOfExpr = codeFactory.createInvocation(
								codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, objCalled);
						
						argus.add(opaqueLabelOfExpr);
					}
				}else {
					argus.add(ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory));
				}
				CtStatement checkLabelExpr = getFactory().Code().createInvocation(
						codeFactory.createTypeAccessWithoutCloningReference(target), checkLabelExprRef, argus);
				
			    CtStatement stmt = RewriteHelper.getTopLevelStmt(element);
			    RewriteHelper.insertStatements(stmt, checkLabelExpr, true);
			}
		}else if (element instanceof CtInvocation) {
			// raise the context level with the object label of the object being called: this is not needed
			CtInvocation ctInv = (CtInvocation)element;
			String methodName =  ctInv.getExecutable().getSimpleName();
			if(ctInv.getExecutable().getDeclaringType() != null) {
				String qName = ctInv.getExecutable().getDeclaringType().getQualifiedName();
				if(ctInv.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard")) {
					return;
				}
			}
			CtStatement stmt = RewriteHelper.getTopLevelStmt(element);
			CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
			CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
			joinLabelRef.setStatic(true);
			joinLabelRef.setSimpleName("joinTopLabel");
			joinLabelRef.setDeclaringType(target);
			
			CtExpression readObjLabel = RewriteHelper.createObjectLabelFromMethodCalled(ctInv, codeFactory);
			CtStatement raiseContextLabelExpr = getFactory().Code().createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
					readObjLabel);
			// raise context label by the object label of the object being called on
			// Current implementation may not be very correct for Strings; 
			RewriteHelper.insertStatements(stmt, raiseContextLabelExpr, true);
		}else if (element instanceof CtLocalVariable) {
			CtLocalVariable ctLocalVar = (CtLocalVariable)element;
			if(ctLocalVar.getSimpleName().startsWith(RewriteHelper.obj_prefix)){
				setDefaultObjLabelsAtCreation(ctLocalVar, codeFactory, coreFactory);
			}
		}else if (element instanceof CtArrayRead) {
			CtArrayRead aRead = (CtArrayRead) element;
			if(aRead.getTarget() instanceof CtVariableRead) {
				CtVariableRead r = (CtVariableRead)aRead.getTarget();
				if(r.getVariable().getSimpleName().startsWith(ParameterRewritingProcessor.calleeLabelsArray_prefix)) {
					return;
				}
			}
			// raise the context level 
			try {
				CtStatement stmt = RewriteHelper.getTopLevelStmt(element);
				CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
				CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
				joinLabelRef.setStatic(true);
				joinLabelRef.setSimpleName("joinTopLabel");
				joinLabelRef.setDeclaringType(target);
				
				CtExpression readFieldLabel = RewriteHelper.createFieldLabelArrayRead(aRead);
				if(!readFieldLabel.toString().equals(bottomName) && !readFieldLabel.toString().equals(bottomString)) {
					CtStatement raiseContextLabelExpr = getFactory().Code().createInvocation(
							codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
							readFieldLabel);
				    RewriteHelper.insertStatements(stmt, raiseContextLabelExpr, true);	
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void setDefaultObjLabelsAtCreation(CtLocalVariable ctWrite, CodeFactory codeFactory, CoreFactory coreFactory) {
		CtStatement stmt = RewriteHelper.getTopLevelStmt(ctWrite);
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference defaultLabelsRef = coreFactory.createExecutableReference();
		defaultLabelsRef.setStatic(true);
		defaultLabelsRef.setSimpleName("setDefaultObjLabels");
		defaultLabelsRef.setDeclaringType(target);

		CtLocalVariableReference varRef = codeFactory.createLocalVariableReference(ctWrite.clone());
		
		CtVariableAccess varRead = codeFactory.createVariableRead(varRef, false);
		List argus = new ArrayList<>();
		argus.add(varRead);
		
		CtInvocation setDefaultLabelsStmt = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), defaultLabelsRef, argus);
		
		RewriteHelper.insertStatements(stmt, setDefaultLabelsStmt, false);
	}
	
//	public static CtExpression buildLatticeBottom(CoreFactory coreFactory, CodeFactory codeFactory) {
//		CtFieldRead fr = coreFactory.createFieldRead();
//		CtTypeReference latticeT = codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLattice.class);
//		fr.setTarget(codeFactory.createTypeAccessWithoutCloningReference(latticeT));
//		CtFieldReference fref = coreFactory.createFieldReference();
//		fref.setSimpleName("bot");
//		fref.setType(codeFactory.createCtTypeReference(lbs.harvard.coinflow.lattice.IFCLabel.class));
//		fref.setDeclaringType(latticeT);
//		fr.setVariable(fref);
//		return fr;
//	}

}
