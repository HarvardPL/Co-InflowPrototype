package lbs.harvard.coinflow.util;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import lbs.harvard.coinflow.compiler.AddFieldObjectLabelFieldProcessor;
import lbs.harvard.coinflow.compiler.LabelCheckProcessor;
import lbs.harvard.coinflow.compiler.ReturnProcessor;
import lbs.harvard.coinflow.compiler.SuperArguProcessor;
import lbs.harvard.coinflow.compiler.CoInflowCompiler;
import lbs.harvard.coinflow.compiler.ConstructorObjFieldLabelsProcessor;
import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.lattice.IFCLabel;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtSynchronized;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.reference.CtWildcardReference;

public class RewriteHelper {
	public static String var_prefix = "$_ifc_";
	public static String obj_prefix = "$_obj_";
	public static int obj_counter = 0;
	
	public static void rewriteObjLabelOf(CtInvocation ctInv, CodeFactory codeFactory) {
		CtExpression target = ctInv.getTarget();
		if(CoInflowCompiler.classesProcessed.contains(target.getType().getQualifiedName())) {
			CtExpression replacement = RewriteHelper.createFieldRead(target, AddFieldObjectLabelFieldProcessor.obj_object_label);
			ctInv.replace(replacement);
		}else {
//			return codeFactory.createCodeSnippetExpression("Lattice.bot");
		}
	}
	
	public static CtExpression createFieldLabelRead(CtFieldAccess fw, CodeFactory codeFactory) {
		if(fw.getTarget() instanceof CtTypeAccess) {
			// accessing a static field
			return createLegacyFieldLabel(fw, codeFactory, fw.getFactory().Core());
		}else {
			if(fw.getTarget() == null || fw.getTarget().getType() == null) {
			}
			
			if(CoInflowCompiler.classesProcessed.contains(fw.getTarget().getType().getQualifiedName())) {
				CtExpression fr = RewriteHelper.createFieldRead(fw.getTarget(), AddFieldObjectLabelFieldProcessor.obj_field_label);
				return fr;
			}else {
				// legacy class's field
				return createLegacyFieldLabel(fw, codeFactory, fw.getFactory().Core());
			}
		}
	}
	
	public static CtExpression createFieldLabelArrayRead(CtArrayAccess arrayAccess) {
		CodeFactory codeFactory = arrayAccess.getFactory().Code();
		CoreFactory coreFactory = arrayAccess.getFactory().Core();
		CtExecutableReference localClsRef = coreFactory.createExecutableReference();
		localClsRef.setStatic(true);
		localClsRef.setSimpleName("getFieldLabel");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    localClsRef.setDeclaringType(target);
	    List args = new ArrayList();
	    args.add(arrayAccess.getTarget());
		CtInvocation getFieldLabelInv = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), localClsRef, args);
	    return getFieldLabelInv;
	}
	
	private static String normalizeStaticField(CtFieldAccess fw) {
		// for static fields, we use special strings as keys in the objectLabelMap
		if(fw.getTarget() instanceof CtTypeAccess) {
			String clazz = fw.getVariable().getDeclaringType().getQualifiedName();
			return "static_field_" + fw.getVariable().toString();
		}
		return fw.toString();
	}
	
	private static CtExpression createLegacyFieldLabel(CtFieldAccess fw, CodeFactory codeFactory, CoreFactory coreFactory) {
		CtExecutableReference localClsRef = coreFactory.createExecutableReference();
		localClsRef.setStatic(true);
		localClsRef.setSimpleName("getFieldLabel");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    localClsRef.setDeclaringType(target);
	    List args = new ArrayList();
		
	    CtExpression targetObj = rebuildFieldAccessObj(fw);
	    if(targetObj == null) {
	   		return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, coreFactory);
	    }
	    args.add(targetObj);
		CtInvocation getFieldLabelInv = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), localClsRef, args);
	    return getFieldLabelInv;
	}
	
	
	/**
	 * This is a helper method for getting field label or object label of an object. 
	 * It gets the target of the field access. Sometimes the target is empty: for example, this.field1, field, and super.field
	 * 
	 * @param fw
	 * @return
	 */
	public static CtExpression rebuildFieldAccessObj(CtFieldAccess fw) {
		CodeFactory codeFactory = fw.getFactory().Code();
		CoreFactory coreFactory = fw.getFactory().Core();
		if(fw.getTarget() instanceof CtTypeAccess) {
			// if this is a static field access, we use ClassName.class as the object
			// worked for spoon 7.3 version, doesn't work for 9.1 version

			if(fw.getTarget() instanceof CtTypeAccess) {
				// if this a static field access, get ClassName.class
				if(fw.getTarget().toString().equals("")) {
					// empty string, create it
					CtTypeAccess newTarget = coreFactory.createTypeAccess();
					newTarget = codeFactory.createTypeAccess(fw.getVariable().getDeclaringType());
					CtExpression static_class_fr = RewriteHelper.createFieldRead(newTarget, "class");
					return static_class_fr;
				}
			}
			CtExpression static_class_fr = RewriteHelper.createFieldRead(fw.getTarget(), "class");
			return static_class_fr;
			
			/**
			//static field write
			if(fw.getType().isPrimitive()) {
				// if this is a primitive field, it's protected by a special string in the objectLabelMap
				return codeFactory.createLiteral(normalizeStaticField(fw));
			}else {
				// if this is a object field, it should have it's separate entry in the objectLabelMap
				// use static_class_name.class
				CtExpression static_class_fr = RewriteHelper.createFieldRead(fw.getTarget(), "class");
				return static_class_fr;
			}
			*/
		}else {
			// instance field write
			CtExpression target = fw.getTarget();
			if(target.toString().length() > 0) {
				return fw.getTarget();
			}else {
				return codeFactory.createThisAccess(fw.getTarget().getType());
			}
		}
	}
	/**
	 * deal with objectLabelOf primitive call
	 * @param ctInv The calls to objectLabelOf
	 * @param codeFactory
	 * @return
	 */
	public static CtExpression createObjectLabelRead(CtInvocation ctInv, CodeFactory codeFactory) {
		CtExpression target = (CtExpression)ctInv.getArguments().get(0);
		if(CoInflowCompiler.classesProcessed.contains(target.getType().getQualifiedName())) {
			CtExpression fr = RewriteHelper.createFieldRead(target, AddFieldObjectLabelFieldProcessor.obj_object_label);
			return fr;
		}else {
			// legacy class's field
			return createLegacyObjectLabel(target, codeFactory, target.getFactory().Core());
		}
	}
	
	private static CtExpression createLegacyObjectLabel(CtExpression obj, CodeFactory codeFactory, CoreFactory coreFactory) {
		CtExecutableReference localClsRef = coreFactory.createExecutableReference();
		localClsRef.setStatic(true);
		localClsRef.setSimpleName("getObjLabel");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    localClsRef.setDeclaringType(target);
	    List args = new ArrayList();
		args.add(obj);
		CtInvocation getFieldLabelInv = codeFactory.createInvocation(
	    		codeFactory.createTypeAccessWithoutCloningReference(target), localClsRef, args);
	    return getFieldLabelInv;
	}
	
	
	
	/**
	 * Dealing with method calls, where the containers should raise to the join of current context level and the object label of the object being called
	 * @param ctInv
	 * @param codeFactory
	 * @return
	 */
	public static CtExpression createObjectLabelFromMethodCalled(CtInvocation ctInv, CodeFactory codeFactory) {
		CtExpression target = ctInv.getTarget();
		if(target instanceof CtTypeAccess || target == null || target.getType() == null) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, ctInv.getFactory().Core());
		}
		target = rebuildMethodCalledObj(ctInv);
		if(target.getType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, ctInv.getFactory().Core());
		}
		if(CoInflowCompiler.classesProcessed.contains(target.getType().getQualifiedName())) {
			CtExpression fr = RewriteHelper.createFieldRead(target, AddFieldObjectLabelFieldProcessor.obj_object_label);
			return fr;
		}else {
			// legacy class's field
			return createLegacyObjectLabel(target, codeFactory, target.getFactory().Core());
		}
	}
	
	public static CtExpression createObjectLabelFromTarget(CtExpression target, CodeFactory codeFactory) {
		if(target instanceof CtTypeAccess || target == null || target.getType() == null) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, target.getFactory().Core());
		}
		if(target.getType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel(codeFactory, target.getFactory().Core());
		}
		if(CoInflowCompiler.classesProcessed.contains(target.getType().getQualifiedName())) {
			CtExpression fr = RewriteHelper.createFieldRead(target, AddFieldObjectLabelFieldProcessor.obj_object_label);
			return fr;
		}else {
			// legacy class's field
			return createLegacyObjectLabel(target, codeFactory, target.getFactory().Core());
		}
	}

	public static CtExpression rebuildMethodCalledObj(CtInvocation ctInv) {
		CodeFactory codeFactory = ctInv.getFactory().Code();
		CoreFactory coreFactory = ctInv.getFactory().Core();
		CtExpression target = ctInv.getTarget();
		if(target instanceof CtTypeAccess || target == null || target.getType() == null) {
			return null;
		}		
		
		if(target.toString().length() == 0) {
			// .foo(); could be this.foo() or super.foo(); we need to rebuild 'this'
			CtExpression thisExpr = codeFactory.createThisAccess(target.getType());
			// find the correct class that associates with 'this'
			CtClass calledClass = findClassTypeOfThis(target, ctInv.getExecutable().getDeclaringType());
			
			CtThisAccess rebuildThisExpr = codeFactory.createThisAccess(calledClass.getReference());
			rebuildThisExpr.setTarget(codeFactory.createTypeAccess(calledClass.getReference()));
			return rebuildThisExpr;
		}else if(target instanceof CtSuperAccess) {
			CtThisAccess newTarget = codeFactory.createThisAccess(target.getType());
			CtClass calledClass = findClassTypeOfThis(target, ctInv.getExecutable().getDeclaringType());
			CtThisAccess rebuildThisExpr = codeFactory.createThisAccess(calledClass.getReference());
			rebuildThisExpr.setTarget(codeFactory.createTypeAccess(calledClass.getReference()));
			return rebuildThisExpr;
//			System.out.println(target.getType());
//			CtSuperAccess superAccess = (CtSuperAccess)target;
//			System.out.println(superAccess.getTarget());
//			if(superAccess.getTarget() == null ){
//				CtElement clazzEle = ctInv;
//				while(! (clazzEle instanceof CtClass)) {
//					clazzEle = clazzEle.getParent();
//				}
//				newTarget.setType(((CtClass)clazzEle).getReference());
//				target = newTarget;
//			}else {
//				newTarget.setTarget(((CtSuperAccess) target).getTarget());
//				target = newTarget;	
//			}
		}
		return ctInv.getTarget().clone();
	}
		
		
	/**
	 * Input parameter is an object
	 * @param expr
	 * @return
	 */
	public static CtExpression buildFieldLabelExpr(CtExpression expr) {
		if(expr.toString().equals("null")) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel( expr.getFactory().Code(), expr.getFactory().Core());
		}
		if(expr.getType().isPrimitive()) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel( expr.getFactory().Code(), expr.getFactory().Core());
		}
		if(expr.getType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
			return ConstructorObjFieldLabelsProcessor.buildGetCurrentLevel( expr.getFactory().Code(), expr.getFactory().Core());
		}
		if(CoInflowCompiler.classesProcessed.contains(expr.getType().getQualifiedName())) {
			CtExpression fr = RewriteHelper.createFieldRead(expr, AddFieldObjectLabelFieldProcessor.obj_field_label);
			return fr;
		}else {
			CoreFactory coreFactory = expr.getFactory().Core();
			CodeFactory codeFactory = expr.getFactory().Code();
			// legacy class's field, use fieldLabelOf
			CtExecutableReference localClsRef = coreFactory.createExecutableReference();
			localClsRef.setStatic(true);
			localClsRef.setSimpleName("getFieldLabel");
		    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		    localClsRef.setDeclaringType(target);
		    List args = new ArrayList();
		    args.add(expr);
			CtInvocation getFieldLabelInv = codeFactory.createInvocation(
		    		codeFactory.createTypeAccessWithoutCloningReference(target), localClsRef, args);
		    return getFieldLabelInv;
		}
	}
	
	
	public static void rewriteToLabeled(CtInvocation ctInv, CodeFactory codeFactory) {
		// if this target is one of the classes being processed
		CtExpression target = ctInv.getTarget();
		if(CoInflowCompiler.classesProcessed.contains(target.getType().getQualifiedName())) {
			CtExpression fr = RewriteHelper.createFieldRead(target, AddFieldObjectLabelFieldProcessor.obj_field_label);
			ctInv.replace(fr);
		}else {
			// if not one of the processing classes
		}
	}
	
	public static CtExpression createFieldRead(CtExpression target, String name) {
		CodeFactory codeFactory = target.getFactory().Code();
		CoreFactory coreFactory = target.getFactory().Core();
		CtFieldRead fr = coreFactory.createFieldRead();
		fr.setTarget(target.clone());
		CtFieldReference fref = coreFactory.createFieldReference();
		fref.setSimpleName(name);
		fref.setDeclaringType(target.getType());
		fr.setVariable(fref);
		return fr;
	}
	
	public static CtExpression createTypeAccessFieldRead(CtExpression target, CtFieldAccess fa, String name) {
		CodeFactory codeFactory = target.getFactory().Code();
		CoreFactory coreFactory = target.getFactory().Core();
		CtFieldRead fr = coreFactory.createFieldRead();
		
		if(target instanceof CtTypeAccess) {
			// if this a static field access, get ClassName.class
			if(target.toString().equals("")) {
				// empty string, create it
				CtTypeAccess newTarget = coreFactory.createTypeAccess();
				newTarget.setType(fa.getVariable().getDeclaringType());
				fr.setTarget(newTarget);
			}
		}else {
			fr.setTarget(target.clone());
		}
		
		
		
		CtFieldReference fref = coreFactory.createFieldReference();
		fref.setSimpleName(name);
		fref.setDeclaringType(target.getType());
		fr.setVariable(fref);
		return fr;
	}
	
	/**
	 * create a new local variable for the input expression. 
	 * This is used for controlling new objects created for class, or array
	 * @param assignment
	 * @return
	 */
	public static CtExpression createNewObjVar(CtExpression element) {
		if((element instanceof CtConstructorCall) || (element instanceof CtNewArray)) {
			CodeFactory codeFactory = element.getFactory().Code();
			CtTypeReference type = element.getType();
			String newLocalName =  obj_prefix + obj_counter++;
			CtElement tmp = element;
			CtExpression newAssignment = element.clone();
			tmp = RewriteHelper.getTopLevelStmt(element);
			
			// This line of code sets type arguments to empty; HashMap<> => HashMap
			removeTypeArgument(type);
			CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, newAssignment);
			CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
			RewriteHelper.insertStatements(((CtStatement)tmp), newVarDecl, true);
			CtExpression vr = codeFactory.createVariableRead(ref, false);
			vr.setType(type);
			addTypeCase(element, vr);
			if(element.getRoleInParent() != CtRole.STATEMENT) {
				element.replace(vr);
			}else {
				element.delete();
			}
			return element;
		}
		return element;
	}
	
	public static void removeTypeArgument(CtTypeReference type) {
		boolean hasValidTypeArgu = false;
		for(Object o : type.getActualTypeArguments()) {
			// wild card inside anonymous class needs to treated differently    <? extends xxx.1> spoon library's design
			// our coinflow library cannot fully support this feature
			if(o.toString().length() != 0) {
				if(o instanceof CtWildcardReference) {
					CtWildcardReference wr = (CtWildcardReference)o;
					if(!wr.getBoundingType().isAnonymous()) {
						hasValidTypeArgu = true;
					}
				}else {
					hasValidTypeArgu = true;
				}
			}
		}
		if(!hasValidTypeArgu) {
			type.setActualTypeArguments(null);
		}
	}
	
	
	public static String api_prefix = "$_userapi_";
	public static int api_counter = 0;
	
	
	public static CtTypeReference getExpressionType(CtExpression exp) {
		// the default getType of Spoon doesn't always work
		if(exp.getType() != null) {
			return exp.getType();
		}
		// several kinds of elements that are rewritten
		if(exp instanceof CtFieldRead) {
			CtFieldRead fr = (CtFieldRead)exp;
			CtTypeReference t = fr.getVariable().getType();
			if (t != null) {
				return t;
			}
		}
		return null;
	}
	
	
	
	public static String OriginalType = "var.original.type";
	
	public static void createNewLocalVarToBeRewritten(CtExpression element, CodeFactory codeFactory) {
		CtTypeReference type = element.getType();
		String newLocalName =  var_prefix + CoInflowCompiler.counter++;		
		if(element instanceof CtInvocation) {
			CtInvocation inv = (CtInvocation)element;
			if(inv.getExecutable() != null) {
				CtExecutableReference exec = inv.getExecutable();
				if(exec.getDeclaringType() != null &&
						exec.getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow.CoInFlowUserAPI")){
					if(exec.getSignature().startsWith("labelOf") 
							|| exec.getSignature().startsWith("labelData") 
							|| exec.getSignature().startsWith("unlabel")
							|| exec.getSignature().startsWith("getCurrentLevel")) {
						newLocalName = api_prefix + api_counter++;
					}else if(! exec.getSignature().startsWith("toLabeled") 
							|| ! exec.getSignature().startsWith("raiseObjLabel") ){
						return;
					}
				}
				
				if(exec.getSimpleName().startsWith(SuperArguProcessor.argu_prefix)) {
					return;
				}
			}
		}
		
		CtElement tmp = element;
		CtExpression newAssignment = element.clone();
		if(tmp == null || tmp.getParent() == null) {
			return;
		}
		tmp = RewriteHelper.getTopLevelStmt(element);
		// if this method invocation belongs to super() or this(), we don't insert
		if(tmp == null) {
			return ;
		}
		
		// This line of code sets type arguments to empty; HashMap<> => HashMap
		RewriteHelper.removeTypeArgument(type);
		if(element.getTypeCasts().size() > 0) {
			type = (CtTypeReference) element.getTypeCasts().get(0);
		}
		CtLocalVariable newVarDecl = codeFactory.createLocalVariable(type, newLocalName, newAssignment);
		
		
		CtLocalVariableReference ref = codeFactory.createLocalVariableReference(newVarDecl);
		RewriteHelper.insertStatements(((CtStatement)tmp), newVarDecl, true);
		CtExpression vr = codeFactory.createVariableRead(ref, false);
		
		// store the original type information for later usage
		RewriteHelper.addOriginalTypeMetadata(newVarDecl, type);
		RewriteHelper.addOriginalTypeMetadata(vr, type);
		
		if(element.getRoleInParent() != CtRole.STATEMENT) {
			element.replace(vr);
		}else {
			element.delete();
		}
		
	}
	
	public static void addTypeCase(CtExpression origin, CtExpression target) {
		for(int i=0; i< origin.getTypeCasts().size(); i++) {
			target.addTypeCast((CtTypeReference) origin.getTypeCasts().get(i));
		}
	}
	
	public static boolean checkIfContinueProcess(CtElement expr) {
		// check if it is part of a toLabeled
		if (checkIfToLabeled1stArgu(expr)) {
			// if this is the first argument of toLabeled, don't continue
			return false;
		}
				
		if(expr instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)expr;
			CtExecutableReference exec = ctInv.getExecutable();		
			if(exec.getSignature().equals("main(java.lang.String[])")) {
				return false;
			}
			if(exec.getDeclaringType() != null &&
					exec.getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow.CoInFlowUserAPI")){
				return true;
			}
			if(exec.getDeclaringType() != null &&
					exec.getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow.internal")){
				return false;
			}
			
			if(RewriteHelper.getTopLevelStmt(expr) instanceof CtFor) {
				// updates, init, and loop condition, donot generate new containers. 
				return false;
			}
			return true;
		}else if(expr instanceof CtFieldRead) {
			CtFieldRead fr = (CtFieldRead)expr;
			if(CoInflowCompiler.classesProcessed.contains((fr.getTarget().getType().getQualifiedName()))){
				return true;
			}
		}else if(expr instanceof CtConstructorCall) {
			CtConstructorCall ctInv = (CtConstructorCall)expr;
			if(ctInv.getExecutable().getDeclaringType().isAnonymous()) {
				return false;
			}
			String clazz = ctInv.getType().getQualifiedName();
			if(!CoInflowCompiler.classesProcessed.contains(ctInv.getExecutable().getDeclaringType().getQualifiedName())) {
				return false;
			}
			// every class needs to be rewritten, since they may be used to store information.
			return true;
			
			/**
			// if the class is one of the classes being rewritten
			if(MainProcessor.classesProcessed.contains(clazz)) {
				return true;
			}
			
			// if the class is a legacy class, we check the arguments to see if they contain unlabelOpaque
			// if they do, then rewrite the constructor call to a new local variable
			for(Object o : ctInv.getArguments()) {
				CtExpression argu = (CtExpression)o;
				if(checkIfContinueProcess(argu)) {
					return true;
				}
			}
			**/
		}else if (expr instanceof CtNewArray) {
			//create a new array
			return true;
		}
		return false;
	}
	
	/**
	 * helper function for dealing with toLabeled(). 
	 * It checks if the expression one of the arguments of a toLabeled()
	 * @param ctInv
	 * @return if the function return false, then this is the first argument of toLabeled, and we stop
	 */
	private static boolean checkIfToLabeled1stArgu(CtElement expr) {
		if(expr.getParent() != null &&
				expr.getParent() instanceof CtInvocation) {
			// if the parent is not toLabeled, do not rewrite it to another local variable
			CtInvocation parentInv = (CtInvocation)(expr.getParent());
			if(parentInv.getExecutable().toString().startsWith("toLabeled")) {
				if(expr.toString().equals(parentInv.getArguments().get(1).toString())) {
					// if this is the second argument, continue
					return false;
				}else {
					// if this is the first argument, stop
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean checkIfMain(String qMethodName) {
		return qMethodName.equals("main(java.lang.String[])");
	}
	
	public static CtStatement getTopLevelStmt(CtElement element){
		CtElement tmp = element; 
		if(tmp == null) {
			return null;
		}
		while(! (tmp.getParent() instanceof CtStatementList)) {
			tmp = tmp.getParent();
			if(tmp == null) {
				return null;
			}
			if(tmp.getParent() == null) {
				return null;
			}
		}
		if(!(tmp instanceof CtStatement)) {
			if(tmp.getParent() !=null && tmp.getParent() instanceof CtCase) {
				// if this is switch/case, insert at the beginning of the branch 
				
				CtCase ctCase = (CtCase) tmp.getParent();
				return getTopLevelStmt(ctCase);
//				if(ctCase.getStatements().size() == 0) {
//					return null;
//				}
//				return ctCase.getStatements().get(0);
			}
		}
		return (CtStatement)tmp;
	}
	
	public static void insertStatements(CtStatement origin, CtStatement toInsert, boolean beforeOrAfter) {
		
		if(origin == null) {
			return ;
		}
		if(origin instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)origin;
			String n = ctInv.getExecutable().getSimpleName(); 
			if(n.equals("<init>")){
				return;
			}
		}
		if(beforeOrAfter) {
			origin.insertBefore(toInsert);	
		}else {
			origin.insertAfter(toInsert);
		}
		
	}

	public static void insertStatements(CtStatement origin, CtStatementList toInsert, boolean beforeOrAfter) {
		if(origin instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)origin;
			String n = ctInv.getExecutable().getSimpleName(); 
			if(n.equals("<init>")){
				return;
			}
		}
		if(origin == null) {
			return;
		}
		if(beforeOrAfter) {
			origin.insertBefore(toInsert);	
		}else {
			origin.insertAfter(toInsert);
		}
		
	}
	
	public static CtStatement createCheckFieldFlowExpr(CtExpression fieldLabelExpr) {
		if(fieldLabelExpr == null) {
			return null;
		}
		CodeFactory codeFactory = fieldLabelExpr.getFactory().Code();
		CoreFactory coreFactory = fieldLabelExpr.getFactory().Core();
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference checkLabelExprRef = coreFactory.createExecutableReference();
		checkLabelExprRef.setStatic(true);
		checkLabelExprRef.setSimpleName("checkFieldWriteFlow");
		checkLabelExprRef.setDeclaringType(target);
		List argus = new ArrayList<>();
		argus.add(fieldLabelExpr);
		CtStatement checkLabelExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), checkLabelExprRef, argus);
		
		return checkLabelExpr;
	}
	
	public static CtStatement createJoinTopExpress(CtExpression extraExpr) {
		CodeFactory codeFactory = extraExpr.getFactory().Code();
		CoreFactory coreFactory = extraExpr.getFactory().Core();
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		CtExecutableReference joinLabelRef = coreFactory.createExecutableReference();
		joinLabelRef.setStatic(true);
		joinLabelRef.setSimpleName("joinTopLabel");
		joinLabelRef.setDeclaringType(target);
		
		if(!extraExpr.toString().equals(LabelCheckProcessor.bottomName) && !extraExpr.toString().equals(LabelCheckProcessor.bottomString)) {
			CtStatement raiseContextLabelExpr = codeFactory.createInvocation(
					codeFactory.createTypeAccessWithoutCloningReference(target), joinLabelRef, 
					extraExpr);
			return raiseContextLabelExpr;
		}
		return null;
	}
	
	
	public static CtExpression createOpaqueLabelOfExp(CtExpression exp) {
		CodeFactory codeFactory = exp.getFactory().Code();
		CoreFactory coreFactory = exp.getFactory().Core();
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		
		CtExecutableReference opaqueLabelOfRef = coreFactory.createExecutableReference();
		opaqueLabelOfRef.setStatic(true);
		opaqueLabelOfRef.setSimpleName("opaqueLabelOf");
		opaqueLabelOfRef.setDeclaringType(target);
		opaqueLabelOfRef.setType(exp.getType());
		
		CtExpression opaqueLabelOfExp = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
				opaqueLabelOfRef, exp.clone());
		return opaqueLabelOfExp;
	}
	
	public static CtExpression createOpaqueValueOfExp(CtExpression exp) {
		CodeFactory codeFactory = exp.getFactory().Code();
		CoreFactory coreFactory = exp.getFactory().Core();
		CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
		
		CtExecutableReference opaqueLabelOfRef = coreFactory.createExecutableReference();
		opaqueLabelOfRef.setStatic(true);
		opaqueLabelOfRef.setSimpleName("opaqueValueOf");
		opaqueLabelOfRef.setDeclaringType(target);
		opaqueLabelOfRef.setType(exp.getType());
		
		CtExpression opaqueValueOfExp = codeFactory.createInvocation(codeFactory.createTypeAccessWithoutCloningReference(target), 
				opaqueLabelOfRef, exp.clone());
		return opaqueValueOfExp;
	}
	
	
	public static boolean ifClassBeingRewritten(String classQName) {
		return CoInflowCompiler.classesProcessed.contains(classQName);
	}
	
	public static boolean isCoInFlowLibrary(CtTypeReference ctType) {
		return ctType != null && ctType.getQualifiedName().startsWith("lbs.harvard.coinflow");
	}
	
	/**
	 * This method is called when expressions such as foo() happen in anno class 
	 * @param target
	 * @param methodDeclClassType
	 * @param thisOrSuper
	 * @return
	 */
	public static CtExpression createThisExprMethod(CtExpression target, CtTypeReference methodDeclClassType, boolean thisOrSuper) {
		// thisOrSuper = true => this; thisOrSuper = false => super
		CodeFactory codeFactory = target.getFactory().Code();
		CtExpression thisExpr = codeFactory.createThisAccess(target.getType());
		// find the correct class that associates with 'this'
		CtClass calledClass = findClassTypeOfThis(target, methodDeclClassType);
		
		if(target.getType().getSimpleName() != methodDeclClassType.getSimpleName()) {
			CtTypeAccess typeAccess = codeFactory.createTypeAccess(methodDeclClassType);
			String s = "this";
			if(!thisOrSuper) {
				s = "super";
			}
			CtExpression fr = RewriteHelper.createFieldRead(typeAccess, s);
			fr.setType(calledClass.getReference());
			return fr; 
		}
		return thisExpr;
	}
	
	public static CtClass findClassTypeOfThis(CtElement ctElement, CtTypeReference methodDeclClassType) {
		while (!(ctElement instanceof CtClass && ((CtClass)ctElement).isSubtypeOf(methodDeclClassType))) {
			ctElement = ctElement.getParent();
		}
		return (CtClass)ctElement;
	}
	
	
	public static CtExpression createThisExprFieldAccess(CtExpression target, CtTypeReference realType) {
		CodeFactory codeFactory = target.getFactory().Code();
		CtExpression thisExpr = codeFactory.createThisAccess(target.getType());
		if(target.getType() instanceof CtTypeAccess) {
			return codeFactory.createTypeAccess(target.getType());
		}
		
//		if(target.getType().getSimpleName() != realType.getSimpleName()) {
//			CtTypeAccess typeAccess = codeFactory.createTypeAccess(target.getType());
//			String s = "this";
//			CtExpression fr = RewriteHelper.createFieldRead(typeAccess, s);
//			fr.setType(realType);
//			return fr; 
//		}
		return thisExpr;
	}
	
	public static boolean ifCoInflowCode(CtElement element) {
		if(element instanceof CtFieldAccess) {
			CtFieldAccess ctRead = (CtFieldAccess)element;
			if(ctRead.getTarget() != null) {
				if(ctRead.getTarget() instanceof CtTypeAccess) {
					CtTypeAccess ta = (CtTypeAccess)ctRead.getTarget();
					if(ta.getAccessedType().getQualifiedName().startsWith("lbs.harvard.coinflow")){
						return true;	
					}
				}
			}
		}else if (element instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)element;
			if(ctInv.getTarget() != null) {
				if(ctInv.getTarget() instanceof CtTypeAccess) {
					CtTypeAccess ta = (CtTypeAccess)ctInv.getTarget();
					if(ta.getAccessedType().getQualifiedName().startsWith("lbs.harvard.coinflow")){
						// toLabeled should be treated differently
						return true;	
					}
				}
			}
		}
		
		return false;
	}
	
	
	public static CtElement getTopClassDef(CtElement element){
		while(!(element.getParent() instanceof CtPackage)) {
			element = element.getParent();
		}
		if(element instanceof CtTypeMember) {
			
		}
		return element;
	}
	
	public static void rewriteToOpaqueLabeled(CtExpression expr, CoreFactory coreFactory, CodeFactory codeFactory, CtTypeReference paraTypeRef) {
		CtExecutableReference executableReference = coreFactory.createExecutableReference();
	    executableReference.setStatic(true);
	    executableReference.setSimpleName("toOpaqueLabeled");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    executableReference.setDeclaringType(target);

	    CtTypeReference opaqueLabeledType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.OpaqueLabeled.class);
	    
	    if(paraTypeRef.isPrimitive()) {
	    		opaqueLabeledType.addActualTypeArgument(paraTypeRef.box());
	    		// what should we do here?
	    		if(expr.getTypeCasts().size() > 0) {
	    			if(!paraTypeRef.toString().equals(expr.getTypeCasts().get(0).toString())) {
	    				// reverse the order of type casts
	    				List oriTypeCasts = expr.getTypeCasts();
	    				expr.setTypeCasts(null);
	    				oriTypeCasts.add(0, paraTypeRef);
	    				expr.setTypeCasts(oriTypeCasts);
	    			}
	    		}else {
	    			if(expr.getType() != null) {
		    			if(!paraTypeRef.toString().equals(expr.getType().toString())) {
		    				expr.addTypeCast(paraTypeRef);
		    			}
	    			}
	    		}
	    }else {
	    		opaqueLabeledType.addActualTypeArgument(paraTypeRef);
	    }
	    executableReference.setType(opaqueLabeledType);
		CtExpression opaqueExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, expr.clone());
		opaqueExpr.setType(opaqueLabeledType);
		expr.replace(opaqueExpr);
	}
	
	public static boolean localVarNotToBeRewritten(CtVariableReference ref) {
		if(ref.getDeclaration() instanceof CtLocalVariable) {
			CtVariable vrDecl = ref.getDeclaration();
			if(vrDecl.getParent() != null && vrDecl.getParent() instanceof CtForEach) {
				return true;
			}
		}
		return false;
	}
	
	public static void rewriteToUnlabelOpaque(CtVariableRead variableR, CoreFactory coreFactory, CodeFactory codeFactory) {
		if(localVarNotToBeRewritten(variableR.getVariable())) {
			return;
		}
		CtExecutableReference executableReference = coreFactory.createExecutableReference();
	    executableReference.setStatic(true);
	    executableReference.setSimpleName("unlabelOpaque");
	    CtTypeReference target = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.IFCUtil.class);
	    executableReference.setDeclaringType(target);
	    
	    
	    
		List<CtExpression<?>> ext = new ArrayList<>();
		CtVariableRead clone = variableR.clone();
		clone.setTypeCasts(null);
		ext.add(clone);
		CtExpression unlabelOpaqueExpr = codeFactory.createInvocation(
				codeFactory.createTypeAccessWithoutCloningReference(target), executableReference, ext);
		
		CtTypeReference originalType = getOriginalTypeMetadata(variableR.getVariable().getDeclaration());
		unlabelOpaqueExpr.setType(originalType);
		variableR.replace(unlabelOpaqueExpr);
		unlabelOpaqueExpr.setParent(variableR.getParent());
		
		// if original reference has type cast
		if(variableR.getTypeCasts() != null && variableR.getTypeCasts().size() >0) {
			for(Object t : variableR.getTypeCasts()) {
				unlabelOpaqueExpr.addTypeCast((CtTypeReference)t);
			}
		}
		//if originally, the variable is of primitive type, should do a type cast
		if(originalType.isPrimitive()) {
			unlabelOpaqueExpr.addTypeCast(originalType);
		}
		
		if(unlabelOpaqueExpr.getParent() instanceof CtReturn) {
			CtReturn ret = (CtReturn) unlabelOpaqueExpr.getParent();
			CtTypeReference type = ret.getReturnedExpression().getType();
			CtExecutable methodDecl = findExecutable(ret);
			if(type == null) {
				// expression such as "" + "abc" has no type information in Spoon 
				type = methodDecl.getType();
			}
			if(methodDecl.getType() != null && variableR.getVariable().getType() != null) {
				if(!methodDecl.getType().toString().equals(variableR.getVariable().getType().toString())) {
					if(!(methodDecl instanceof CtLambda)) {
						unlabelOpaqueExpr.addTypeCast(methodDecl.getType());
					}
				}
			}
		}
	}
	
	public static CtExecutable findExecutable(CtElement ctExp) {
		CtElement tmp = ctExp.getParent();
		while(! (tmp instanceof CtExecutable)) {
			tmp = tmp.getParent();
		}
		return (CtExecutable)tmp;
	}
	
	public static void addOriginalTypeMetadata(CtElement exp, CtTypeReference originialType) {
		// store the original type information for later usage
		if(exp.getMetadata(RewriteHelper.OriginalType) == null) {
			exp.putMetadata(RewriteHelper.OriginalType, originialType);	
		}
	}
	
	public static CtTypeReference getOriginalTypeMetadata(CtElement exp) {
		// store the original type information for later usage
		return (CtTypeReference)exp.getMetadata(RewriteHelper.OriginalType);
	}
	
}
