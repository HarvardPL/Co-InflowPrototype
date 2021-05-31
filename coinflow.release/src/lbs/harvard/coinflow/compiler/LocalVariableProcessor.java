package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
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
import spoon.reflect.declaration.CtExecutable;
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
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtPath;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtCatchVariableReference;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtIntersectionTypeReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtModuleReference;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeMemberWildcardImportReference;
import spoon.reflect.reference.CtTypeParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtUnboundVariableReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.reference.CtWildcardReference;
import spoon.reflect.visitor.CtVisitor;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Transform the local variables created by Co-Inflow by adding UnlabelOpaque()
 * @author Jian Xiang(jxiang@seas.harvard.edu)
 *
 */
public class LocalVariableProcessor extends AbstractProcessor {

	public static Map<CtVariableReference, CtExpression> transformedLocalVarRef = new HashMap<>();
	Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars = new HashMap<>();

	public LocalVariableProcessor(Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars) {
		this.opaqueLabeledVars = opaqueLabeledVars;
	}
	
	@Override
	public void process(CtElement element) {
		
		if(element instanceof CtClass) {
			CtClass clazz = (CtClass)element;
			if(!clazz.isAnonymous()) {
				transformedLocalVarRef.clear();
			}
		}
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		
		if (element instanceof CtLocalVariable) {
			CtTypeReference originT = ((CtLocalVariable) element).getType();
			element.putMetadata(RewriteHelper.OriginalType, originT);
			
			// this is only local variable declaration
			CtLocalVariable varDecl = (CtLocalVariable)element;
			CtElement executableElement = varDecl.getParent();
			while(! (executableElement instanceof CtExecutable)) {
				if(executableElement == null) {
					return;
				}
				executableElement = executableElement.getParent();
			}
			
			if(varDecl.getSimpleName().startsWith(RewriteHelper.api_prefix)) {
				return;
			}
			
			if(varDecl.getSimpleName().startsWith(RewriteHelper.var_prefix)) {
				CtExpression rhs = varDecl.getAssignment();
				RewriteHelper.rewriteToOpaqueLabeled(varDecl.getAssignment(), coreFactory, codeFactory, varDecl.getType());	
				if(rhs.getTypeCasts().size() > 0) {
					CtTypeReference opaqueT = generatedOpaqueLabeledType((CtTypeReference)rhs.getTypeCasts().get(0));
					varDecl.setType(opaqueT);
				}else {
					CtTypeReference opaqueT = generatedOpaqueLabeledType(rhs.getType());
					varDecl.setType(opaqueT);
					if(opaqueT.getActualTypeArguments() != null) {
						for(CtTypeReference ref : opaqueT.getActualTypeArguments()) {
							varDecl.getType().removeActualTypeArgument(ref);
							varDecl.getType().addActualTypeArgument(ref);
						}
					}
				}	
			}
			else {
				if(executableElement != null && executableElement instanceof CtExecutable) {
					CtExecutableReference methodRef = 
							((CtExecutable)executableElement).getReference();
					if (opaqueLabeledVars.containsKey(methodRef) && 
							opaqueLabeledVars.get(methodRef).contains(varDecl.getReference()) 
							)
					{
						if(RewriteHelper.localVarNotToBeRewritten(varDecl.getReference())) {
							return;
						}
						
						// Change the local variable's type into opaquelabeled<T>
						
						varDecl.setType(generatedOpaqueLabeledType(originT));
						if(varDecl.getAssignment() != null) {
							RewriteHelper.rewriteToOpaqueLabeled(varDecl.getAssignment(), coreFactory, codeFactory, originT);	
						}	
					}
				}
			}
		}
	
		else if (element instanceof CtAssignment) {
			CtAssignment assignment = (CtAssignment)element;
			CtElement executableElement = assignment.getParent();
			while(! (executableElement instanceof CtExecutable)) {
				executableElement = executableElement.getParent();
			}
			CtExpression lhs = assignment.getAssigned();
			if(lhs instanceof CtVariableWrite) {
				CtVariableWrite vw = (CtVariableWrite)lhs;
				if(vw.getVariable() instanceof CtLocalVariableReference) {
					if(executableElement != null && executableElement instanceof CtExecutable) {
						CtExecutableReference methodRef = 
								((CtExecutable)executableElement).getReference();
						if (opaqueLabeledVars.containsKey(methodRef) && 
								opaqueLabeledVars.get(methodRef).contains(vw.getVariable()) 
								){
							RewriteHelper.rewriteToOpaqueLabeled(assignment.getAssignment(), coreFactory, codeFactory, vw.getType());	
						}
					}
				}
			}
		}
		
		if(element instanceof CtVariableRead) {
			CtVariableRead variableR = (CtVariableRead)element;
			if(variableR.getVariable().
					getSimpleName().startsWith(AddFieldObjectLabelFieldProcessor.obj_field_label) 
					|| variableR.getVariable().
					getSimpleName().startsWith(AddFieldObjectLabelFieldProcessor.obj_object_label) 
				) {
				return;
			}
			
			if (variableR.getVariable().getSimpleName().startsWith(RewriteHelper.var_prefix) 
					){
				RewriteHelper.rewriteToUnlabelOpaque(variableR, getFactory().Core(), getFactory().Code());
			}else {
				CtElement executableElement = element.getParent();
				while(! (executableElement instanceof CtExecutable)) {
					if(executableElement == null) {
						return;
					}
					executableElement = executableElement.getParent();
				}
				
				if(executableElement != null && executableElement instanceof CtExecutable) {
					CtExecutableReference methodRef = 
							((CtExecutable)executableElement).getReference();
					if (opaqueLabeledVars.containsKey(methodRef) && 
							opaqueLabeledVars.get(methodRef).contains(variableR.getVariable()) 
							){
						RewriteHelper.rewriteToUnlabelOpaque(variableR, getFactory().Core(), getFactory().Code());
					}
				}
			}
		}
		
	}
	
	public static CtTypeReference generatedOpaqueLabeledType(CtTypeReference exprType) {
		CodeFactory codeFactory = exprType.getFactory().Code();
		CoreFactory coreFactory = exprType.getFactory().Core();
	    CtTypeReference opaqueType = codeFactory.createCtTypeReference(lbs.harvard.coinflow.internal.OpaqueLabeled.class);
	    
	    CtTypeReference rhsT = exprType;
	    RewriteHelper.removeTypeArgument(rhsT);
	    if(rhsT.isPrimitive()) {
	    		opaqueType.addActualTypeArgument(rhsT.box());
	    }else {
	    		opaqueType.addActualTypeArgument(rhsT);
	    }
	    return opaqueType;
	}
	
	public static void putNewEntryInRefMap(CtVariableReference ref, CtExpression expr) {
		if(!checkIfLoopVar(ref)) {
			transformedLocalVarRef.put(ref, expr);
			rewriteRHS(expr);
		}
		if(checkIfLoopVar(ref) && (expr instanceof CtVariableRead)) {
			// This is constructed by our loopEffectProcessor.java file, for loop variables
			CtVariableRead rhs = (CtVariableRead)expr;
			transformedLocalVarRef.remove(rhs.getVariable());
		}
	}
	
	
	
	private static boolean checkIfLoopVar(CtVariableReference vref) {
		if(vref.getSimpleName().startsWith(LoopEffectProcessor.loop_var_prefix)) {
			return true;
		}
		return false;
	}
	
	/**
	public static CtTypeReference rewriteAssignmentToOpaqueTyped(CtExpression expr){
		CodeFactory codeFactory = expr.getFactory().Code();
		CoreFactory coreFactory = expr.getFactory().Core();
		if (expr instanceof CtInvocation) {
			// if this is unlabelOpaque, then strip the unlabelOpaque
			CtInvocation ctInv = (CtInvocation)expr;
			CtExecutable exec = (CtExecutable)ctInv.getExecutable().getDeclaration();
			if(ctInv.getExecutable().getSimpleName().startsWith("unlabelOpaque")){
				CtExpression argu = (CtExpression)ctInv.getArguments().get(0);
				expr.replace(argu);
				return argu.getType();
			}else {
				return ArgumentProcessor.rewriteToOpaqueLabeled(expr, coreFactory, codeFactory, expr.getType());	
			}
		}else {
			return ArgumentProcessor.rewriteToOpaqueLabeled(expr, coreFactory, codeFactory, expr.getType());	
		}
	}*/
	
	public static void rewriteRHS(CtExpression rhs) {
		// Input is the rhs of an assignment, we should 
		CodeFactory codeFactory = rhs.getFactory().Code();
		CoreFactory coreFactory = rhs.getFactory().Core();
		CtTypeReference cttype = rhs.getType();
		if(cttype != null) {
			CtExpression replacement = null;
			if(cttype.isPrimitive()) {
				String typeName = cttype.toString();
				switch (typeName){
					case "boolean":
						replacement = codeFactory.createLiteral(true);
						break;
					case "byte":
						replacement = codeFactory.createLiteral(0);
						break;
					case "char":
						replacement = codeFactory.createLiteral('a');
						break;
					case "short":
						replacement = codeFactory.createLiteral(0);
						break;
					case "int":
						replacement = codeFactory.createLiteral(0);
						break;
					case "long":
						replacement = codeFactory.createLiteral(1L);
						break;
					case "float":
						replacement = codeFactory.createLiteral(0F);
						break;
					case "double":
						replacement = codeFactory.createLiteral(0.0);
						break;
				}
			}else {
				replacement = codeFactory.createCodeSnippetExpression("null");
			}
			rhs.replace(replacement);
		}
	}
	
	
}
