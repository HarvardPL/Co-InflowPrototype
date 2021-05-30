package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lbs.harvard.coinflow.CoInflowLibraryAnnotation;
import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.reference.CtTypeReference;

public class LibraryCallProcessor extends AbstractProcessor<CtAbstractInvocation>{

	@Override
	public void process(CtAbstractInvocation abstractInv) {
		Map<String, List<Integer>> libraryEffectReadMap = CoInflowLibraryAnnotation.libraryEffectReadMap;
		Map<String, List<Integer>> libraryEffectWriteMap = CoInflowLibraryAnnotation.libraryEffectWriteMap;
		if(abstractInv instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)abstractInv;
			if(ctInv.getExecutable() != null && ctInv.getExecutable().getDeclaringType() != null) {
				CtTypeReference classRef = ctInv.getExecutable().getDeclaringType();
				String className = classRef.getQualifiedName();
				String methodSign = ctInv.getExecutable().getSignature();
				
				String key = CoInflowLibraryAnnotation.buildMethodSign(classRef.getQualifiedName(), methodSign);
				String starKey = CoInflowLibraryAnnotation.buildMethodSign("*", methodSign);
				List<Integer> paraAnnotated = new ArrayList<>();
				if(libraryEffectReadMap.containsKey(key)) {
					paraAnnotated = libraryEffectReadMap.get(key);
				}else if(libraryEffectReadMap.containsKey(starKey)) {
					paraAnnotated = libraryEffectReadMap.get(starKey);
				}
				// for reads, call joinLabel
				for(int pos : paraAnnotated) {
					CtExpression fieldLabelExpr = null;
					if(pos == 0) {
						// the object being called upon
						fieldLabelExpr = RewriteHelper.buildFieldLabelExpr(ctInv.getTarget());
					}else {
						fieldLabelExpr = RewriteHelper.buildFieldLabelExpr((CtExpression)(ctInv.getArguments().get(pos - 1)));
					}
					CtStatement joinStatement = RewriteHelper.createJoinTopExpress(fieldLabelExpr);
					if(joinStatement != null) {
						CtStatement tmp = RewriteHelper.getTopLevelStmt(ctInv);
						RewriteHelper.insertStatements(tmp, joinStatement, true);
					}
				}
				
				List<Integer> paraAnnotatedWrite = new ArrayList<>();
				if(libraryEffectWriteMap.containsKey(key)) {
					paraAnnotatedWrite = libraryEffectWriteMap.get(key);
				}else if(libraryEffectWriteMap.containsKey(starKey)) {
					paraAnnotatedWrite = libraryEffectWriteMap.get(starKey);
				}
				// for writes, call checkFieldWriteFlow
				for(int pos : paraAnnotatedWrite) {
					CtExpression fieldLabelExpr = null;
					if(pos == 0) {
						// the object being called upon
						fieldLabelExpr = RewriteHelper.buildFieldLabelExpr(ctInv.getTarget());
					}else {
						fieldLabelExpr = RewriteHelper.buildFieldLabelExpr((CtExpression)(ctInv.getArguments().get(pos - 1)));
					}
					CtStatement checkFieldFlowStmt = RewriteHelper.createCheckFieldFlowExpr(fieldLabelExpr);
					if(checkFieldFlowStmt != null) {
						CtStatement tmp = RewriteHelper.getTopLevelStmt(ctInv);
						RewriteHelper.insertStatements(tmp, checkFieldFlowStmt, false);
					}
				}
				
			}
		}else if (abstractInv instanceof CtConstructorCall) {
			CtConstructorCall constructorCall = (CtConstructorCall)abstractInv;
			if(constructorCall.getType() != null && constructorCall.getExecutable()!= null ) {
				String className = constructorCall.getType().getQualifiedName();
				String methodSign = constructorCall.getExecutable().getSignature();
				String key = CoInflowLibraryAnnotation.buildMethodSign(className, methodSign);
				if(libraryEffectReadMap.containsKey(key)){
					// for reads, call joinLabel
					for(int pos : libraryEffectReadMap.get(key)) {
						CtExpression fieldLabelExpr = null;
						// read cannot has pos == 0
						if(pos == 0) {
							continue;
						}
						fieldLabelExpr = RewriteHelper.buildFieldLabelExpr((CtExpression)(constructorCall.getArguments().get(pos - 1)));
						CtStatement joinStatement = RewriteHelper.createJoinTopExpress(fieldLabelExpr);
						if(joinStatement != null) {
							CtStatement tmp = RewriteHelper.getTopLevelStmt(constructorCall);
							RewriteHelper.insertStatements(tmp, joinStatement, true);
						}
					}
				}
				if(libraryEffectWriteMap.containsKey(key)) {
					// for writes, call checkFieldWriteFlow
					for(int pos : libraryEffectWriteMap.get(key)) {
						CtExpression fieldLabelExpr = null;
						// pos == 0 means the newly created object
						if(pos == 0) {
							// locate the new object
							if(constructorCall.getParent() instanceof CtLocalVariable) {
								CtLocalVariable localV = (CtLocalVariable)constructorCall.getParent();
								CtVariableAccess vr = constructorCall.getFactory().Code().createVariableRead(localV.getReference(), false);
								fieldLabelExpr = RewriteHelper.buildFieldLabelExpr(vr);
							}
						}else {
							fieldLabelExpr = RewriteHelper.buildFieldLabelExpr((CtExpression)(constructorCall.getArguments().get(pos - 1)));
						}
						CtStatement checkFieldFlowStmt = RewriteHelper.createCheckFieldFlowExpr(fieldLabelExpr);
						if(checkFieldFlowStmt != null) {
							CtStatement tmp = RewriteHelper.getTopLevelStmt(constructorCall);
							RewriteHelper.insertStatements(tmp, checkFieldFlowStmt, false);
						}
					}
				}
			}
		}
		

	}

}
