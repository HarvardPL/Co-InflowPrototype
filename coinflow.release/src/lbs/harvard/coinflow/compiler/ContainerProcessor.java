package lbs.harvard.coinflow.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import spoon.reflect.visitor.filter.TypeFilter;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtSynchronized;
import spoon.reflect.code.CtThrow;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class ContainerProcessor extends AbstractProcessor{

	
	Set<CtStatement> topLevelStmt = new HashSet<>();
	List<CtStatement> containerCode = null;
	
//	private CtStatement getTopLevelStmt(CtElement element){
//		CtElement tmp = element; 
//		if(tmp == null) {
//			return null;
//		}
//		if( tmp instanceof CtClass || tmp instanceof CtInterface) {
//			return null;
//		}
//		while(! ( tmp.getParent() != null && ((tmp.getParent() instanceof CtBlock)) 
//				|| tmp.getParent() instanceof CtClass 
//				|| tmp.getRoleInParent() == CtRole.STATEMENT)) {
//			tmp = tmp.getParent();
//			if(tmp == null) {
//				return null;
//			}
//		}
//		if(tmp instanceof CtField) {
//			return null;
//		}
//		if(! (tmp instanceof CtStatement)) {
//			return null;
//		}
//		return (CtStatement)tmp;
//	}
	
	@Override
	public void process(CtElement element) {
		
		CoreFactory coreFactory = getFactory().Core();
		CodeFactory codeFactory = getFactory().Code();
		containerCode = buildContainerCode(coreFactory, codeFactory);
		CtStatement top = RewriteHelper.getTopLevelStmt(element);
		if(top == null) {
			return;
		}
		
		
		if(top instanceof CtFor || top instanceof CtForEach || top instanceof CtWhile) {
			return;
		}
		
		if(element instanceof CtInvocation) {
			CtInvocation inv = (CtInvocation)element;
			String methodName =  inv.getExecutable().getSimpleName();
			if(inv.getExecutable().getDeclaringType() != null) {
				String qName = inv.getExecutable().getDeclaringType().getQualifiedName();
				if(methodName.equals("<init>") || methodName.equals("buildLattice")) {
					return;
				}
				if(inv.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow.CoInFlowUserAPI")
					&& !methodName.startsWith("toLabeled")
					&& !methodName.startsWith("raiseObjLabel")) {
//					addContainerCode(top, coreFactory, codeFactory);	
					return;
				}
				
				if(inv.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow.internal")
						&& !methodName.startsWith("toOpaqueLabeled")
						) {
					return;
				}
				if(methodName.startsWith("unlabelOpaque")) {
					if(inv.getParent() instanceof CtReturn || inv.getParent() instanceof CtThrow) {
						return;
					}
				}
				if(methodName.startsWith("toOpaqueLabeled")) {
					// only method calls and field accesses need containers
					CtExpression argu = (CtExpression) inv.getArguments().get(0);
					if(!(argu instanceof CtInvocation) 
							&& !(argu instanceof CtFieldAccess)) {
						return;
					}
				}
			}
			/** This code works, but may not good for legacy library calls, such as sysout
			if(!methodName.startsWith("unlabelOpaque") ) {// && !methodName.startsWith("toOpaqueLabeled")) {
				if(RewriteHelper.checkIfContinueProcess(inv)) {
					addContainerCode(top,coreFactory, codeFactory);	
				}
			}else {
				// if unlabelopaque is part of another method invocation
				if(inv.getRoleInParent() == CtRole.ARGUMENT) {
					if(inv.getParent() instanceof CtInvocation) {
						CtInvocation p = (CtInvocation) inv.getParent();
						String n = p.getExecutable().getSimpleName(); 
						if(n.equals("<init>")){
							return;
						}
					}
					addContainerCode(top,coreFactory, codeFactory);	
				}else if (inv.getRoleInParent() == CtRole.ASSIGNMENT) {
					if(inv.getParent() instanceof CtFieldWrite) {
						addContainerCode(top,coreFactory, codeFactory);	
					}
				}
			}
			**/
			// wrap with container whenever there is a call to functions not from IFCUtil and CoInFlow
			addContainerCode(top, coreFactory, codeFactory);	
 		}else if(element instanceof CtConstructorCall) {
			if(element.isImplicit()) {
				// if this is implicit, such as Enum
				return;
			}
			CtConstructorCall inv = (CtConstructorCall)element;
			String methodName =  inv.getExecutable().getDeclaringType().getQualifiedName();
			if(methodName.equals("<init>")) {
				return;
			}
			if(inv.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
				return;
			}
			// wrap with container whenever there is a call to functions not from IFCUtil and CoInFlow
			addContainerCode(top,coreFactory, codeFactory);	
 		}else if (element instanceof CtFieldAccess) {
 			CtFieldAccess fr = (CtFieldAccess)element;
 			// if the fields are object labels and field labels, don't add containers
 			if(RewriteHelper.ifCoInflowCode(element)) {
 				return;
 			}
 			if(fr.getVariable().getSimpleName().
 					startsWith(AddFieldObjectLabelFieldProcessor.obj_object_label)) {
 				return;
 			}
 			if(fr.getVariable().getSimpleName().startsWith(AddFieldObjectLabelFieldProcessor.obj_field_label)) {
 				return;
 			}
 	 	 	addContainerCode(top,coreFactory, codeFactory);		
 		}
 		else if (element instanceof CtArrayAccess) {
 			CtArrayAccess aa = (CtArrayAccess)element;
 			if(aa.getTarget() instanceof CtVariableRead) {
 				CtVariableRead arrayTarget = (CtVariableRead)aa.getTarget();
 				if(arrayTarget.getVariable().getSimpleName().
 						startsWith(ParameterRewritingProcessor.calleeLabelsArray_prefix)) {
 					return;
 				}
 				if(arrayTarget.getVariable().getSimpleName().
 						startsWith(ArgumentProcessor.callerLabelArrayPrefix)) {
 					return;
 				}
 			}
 			addContainerCode(top,coreFactory, codeFactory);	
 		}
		
		if(element.getRoleInParent() == CtRole.STATEMENT) {
			topLevelStmt.clear();
		}
		if(element instanceof CtExecutable) {
			CtExecutable exec = (CtExecutable)element;
			if(exec.getSignature().equals("main(java.lang.String[])")) {
				List<CtStatement> code = ContainerProcessor.buildContainerCode(coreFactory, codeFactory);
				exec.getBody().insertBegin(code.get(0));
				exec.getBody().insertEnd(code.get(1));
			}
		}
	}
	
	

	
	// return if any branch of the if statement doesn't have return
//	private boolean processCtIf(CtIf ctIf, CoreFactory coreFactory, CodeFactory codeFactory) {
//		if(ctIf.getThenStatement() instanceof CtBlock) {
//			CtBlock block = (CtBlock)ctIf.getThenStatement();
//			if(block.)
//		}else if(ctIf.getThenStatement() instanceof CtReturn) {
//			CtBlock newBlock = coreFactory.createBlock();
//			ctIf.setThenStatement(newBlock);
//			CtStatement newRet = ctIf.getThenStatement().clone();
//			newBlock.addStatement(newRet);
//			RewriteHelper.insertStatements(newRet, containerCode.get(1), true);
//			
//			return false;
//		}
//		return true;
//	}
	
	private static int branchCounter(CtIf stmt) {
		int count = 1; 
		List<CtIf> ifs = stmt.getElements(new TypeFilter<>(CtIf.class));
		for(CtIf ctIf : ifs) {
			if(ctIf.getElseStatement() != null) {
				count++;
			}
		}
		return count;
	}
	
	private static void insertBeforeControlFlowJumps(CtStatement stmt, CtStatement toInsert) {
		List<CtReturn> rets = stmt.getElements(new TypeFilter<>(CtReturn.class));
		for(CtReturn ret : rets) {
			RewriteHelper.insertStatements(ret, toInsert.clone(), true);
		}
//		List<CtThrow> tws = stmt.getElements(new TypeFilter<>(CtThrow.class));
//		for(CtThrow tw : tws) {
//			RewriteHelper.insertStatements(tw, toInsert.clone(), true);
//		}
	}
	
	
	private static CtStatement getLastStmt(CtBlock block) {
		List<CtStatement> l = block.getStatements();
		for(int i = l.size() -1; i >=0; i--) {
			if(!(l.get(i) instanceof CtComment)) {
				return l.get(i);
			}
		}
		return null;
	}
	
	private static boolean earlyJump(CtStatement stmt) {
		if(stmt == null) {
			return false;
		}
		if(stmt instanceof CtIf) {
			CtIf ctif = (CtIf)stmt;
			if(ctif.getElseStatement() != null) {
				return earlyJump(ctif.getThenStatement()) 
						&& earlyJump(ctif.getElseStatement());
			}else {
				return earlyJump(ctif.getThenStatement());
			}
		}else if(stmt instanceof CtReturn) {
			return true;
		}else if (stmt instanceof CtBlock) {
			CtBlock block = (CtBlock)stmt;
			return earlyJump(getLastStmt(block));
		}else if(stmt instanceof CtThrow) {
			return true;
		}else if (stmt instanceof CtSynchronized) {
			CtSynchronized syc = (CtSynchronized)stmt;
			return earlyJump(syc.getBlock());
		}
		return false;
	}
	
	public void addContainerCode(CtStatement stmt, CoreFactory coreFactory, CodeFactory codeFactory) {
		if(topLevelStmt.contains(stmt)) {
			return;
		}
		
		CtStatement topLevel = RewriteHelper.getTopLevelStmt(stmt);
		if(topLevel instanceof CtReturn || topLevel instanceof CtIf) {
			return;
		}
		
		/* deal with control flow caused by return
		 * if(unlabelOpaque(...)){
		 *     return
		 *  }else {
		 *  	return
		 *  } */
		topLevelStmt.add(stmt);
		RewriteHelper.insertStatements(stmt, containerCode.get(0), true);
		RewriteHelper.insertStatements(stmt, containerCode.get(1), false);
		// The following piece of code tries to account for control flow jumps caused by return
		/* 
		if(stmt instanceof CtIf || stmt instanceof CtSynchronized) {
			RewriteHelper.insertStatements(stmt, containerCode.get(0), true);
			insertBeforeControlFlowJumps(stmt, containerCode.get(1));
			if(!earlyJump(stmt)){
				RewriteHelper.insertStatements(stmt, containerCode.get(1).clone(), false);
			}
		}else {
			RewriteHelper.insertStatements(stmt, containerCode.get(0), true);
			RewriteHelper.insertStatements(stmt, containerCode.get(1), false);
		}
		*/
	}
	
	public static List<CtStatement> buildContainerCode( CoreFactory coreFactory, CodeFactory codeFactory){
		CtExecutableReference newContainerRef = coreFactory.createExecutableReference();
		newContainerRef.setStatic(true);
		newContainerRef.setSimpleName("newContainer");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    newContainerRef.setDeclaringType(target);
	    
	    CtInvocation newContainerInc = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), newContainerRef, new ArrayList<>());
	    
		CtExecutableReference endContainerRef = coreFactory.createExecutableReference();
		endContainerRef.setStatic(true);
		endContainerRef.setSimpleName("endContainer");
	    target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    endContainerRef.setDeclaringType(target);
	    
	    CtInvocation endContainerInc = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), endContainerRef, new ArrayList<>());
	    List<CtStatement> code = new ArrayList<>();
	    code.add(newContainerInc);
	    code.add(endContainerInc);
	    return code;
	}

}
