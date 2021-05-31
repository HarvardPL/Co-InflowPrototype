package lbs.harvard.coinflow.compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayAccess;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.path.CtPath;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * Dataflow analysis
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class StaticDataFlowAnalyzerProcessor extends AbstractProcessor<CtElement>{
	Map<CtVariableReference, Set<CtVariableReference>> currentMethodFlows = new HashMap<>();
	Map<CtExecutableReference, Map<CtVariableReference, Set<CtVariableReference>>> methodAffectingLinks 
		= new HashMap<>();
	
	public Map<CtExecutableReference, 
			Map<CtVariableReference, Set<CtVariableReference>>> getAffectingLinks() {
		return methodAffectingLinks;
	}

	Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars = new HashMap<>();

	public Map<CtExecutableReference, Set<CtVariableReference>> getOpaqueLabeledVars() {
		return opaqueLabeledVars;
	}

	@Override
	public void process(CtElement element) {
		
		if(element instanceof CtExecutable) {
			// local variables already accessed
			if(currentMethodFlows.size() > 0) {
				CtExecutableReference exeRef = ((CtExecutable) element).getReference();
				Map<CtVariableReference, Set<CtVariableReference>> reducedMap = new HashMap<>();
				for(CtVariableReference rhs : currentMethodFlows.keySet()) {
					if(rhs.getSimpleName().startsWith(RewriteHelper.var_prefix) || rhs instanceof CtParameterReference) {
						if(!opaqueLabeledVars.containsKey(exeRef)) {
							opaqueLabeledVars.put(exeRef, new HashSet<>());
						}
						opaqueLabeledVars.get(exeRef).addAll(currentMethodFlows.get(rhs));
					}else {
						reducedMap.put(rhs, currentMethodFlows.get(rhs));
					}
				}
				methodAffectingLinks.put(exeRef, 
						reducedMap);
				currentMethodFlows.clear();
			}
		}
			
		
		if(element instanceof CtLocalVariable || element instanceof CtAssignment) {
			Set<CtVariableReference> defN = localVariablesModified(element);
			CtVariableReference usedN = variablesUsed(element);
			if(usedN == null) {
				return;
			}
			if(defN.contains(usedN)) {
				return;
			}
			
			if(!currentMethodFlows.containsKey(usedN)) {
				currentMethodFlows.put(usedN, new HashSet<>());
			}
			Set<CtVariableReference> s = currentMethodFlows.get(usedN);
			for(CtVariableReference affected : defN) {
				s.add(affected);
			}
		}
	}

	public static void propagateMethod(CtExecutableReference methodRef,
			Map<CtVariableReference, Set<CtVariableReference>> thisMethodAffectingLinks, 
			 Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars
			) {
		if(!opaqueLabeledVars.containsKey(methodRef)) {
			opaqueLabeledVars.put(methodRef, new HashSet<CtVariableReference>());
		}
		Set<CtVariableReference> visited = new HashSet<>();
		Queue<CtVariableReference> q = new LinkedList<>();
		for(CtVariableReference f : opaqueLabeledVars.get(methodRef)) {
			q.offer(f);
			visited.add(f);
		}
		// BFS
		while(q.size() > 0) {
			CtVariableReference top = q.poll();
			visited.add(top);
			if(thisMethodAffectingLinks.get(top) != null) {
				for(CtVariableReference affected : thisMethodAffectingLinks.get(top)) {
					if(!visited.contains(affected)) {
						opaqueLabeledVars.get(methodRef).add(affected);
						q.offer(affected);
					}
				}
			}
		}
	}
	
	public static void fullPropagation(
			Map<CtExecutableReference, Map<CtVariableReference, Set<CtVariableReference>>> affectingLinks,
			Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars) {
		for(CtExecutableReference methodRef : affectingLinks.keySet()) {
			Map<CtVariableReference, Set<CtVariableReference>> thisMethodAffectingLinks = affectingLinks.get(methodRef);
			propagateMethod(methodRef, thisMethodAffectingLinks, opaqueLabeledVars);
		}
	}
	
	private Set<CtVariableReference> localVariablesModified(CtElement element) {
		//Obtain the variables defined in this node
		HashSet<CtVariableReference> def = new HashSet<>();
		if (element != null) {
			if (element instanceof CtLocalVariable) {
				CtLocalVariable lv = ((CtLocalVariable) element);
				if (lv.getDefaultExpression() != null) {
					if(addModifiedRefCheck(lv.getReference())) {
						def.add(lv.getReference());
					}
				}
			} else if (element instanceof CtAssignment) {
				CtExpression e = ((CtAssignment) element).getAssigned();
				if (e instanceof CtVariableAccess) {
					CtVariableReference va = ((CtVariableAccess) e).getVariable() ;
					if(va instanceof CtLocalVariableReference) {
						if(addModifiedRefCheck(va)) {
							def.add(va);
						}
						
					}
				}
			}
		}
		return def;
	}
	
	private boolean addModifiedRefCheck(CtVariableReference va){
		String varName = va.getSimpleName();
		if(varName.startsWith(ForLoopProcessor.forLoopLocal_prefix) 
				|| varName.startsWith(CtForEachProcessor.forEachLocal_prefix)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * find all variables used in this control flow graph node, including variables defined
	 * @param n
	 * @return
	 */
	private CtVariableReference variablesUsed(CtElement element ) {
		if (element == null) {
			return null;
		}
//		//Obtain variables used in this node
//		HashSet<CtVariableReference> read = new HashSet<>();
//		for (CtVariableAccess a: element.getElements(new TypeFilter<CtVariableAccess>(CtVariableAccess.class))) {
//			if(a.getVariable() instanceof CtLocalVariableReference || a.getVariable() instanceof CtParameterReference) {
//				read.add(a.getVariable());
//			}
//		}
		
		// only counts x = y;
		if (element instanceof CtLocalVariable) {
			CtLocalVariable lv = ((CtLocalVariable) element);
			// don't count special local variables
			if(!addModifiedRefCheck(lv.getReference())){
				return null;
			}
			
			if (lv.getDefaultExpression() != null) {
				if(lv.getDefaultExpression() instanceof CtVariableRead) {					
					CtVariableRead rhs = (CtVariableRead)lv.getDefaultExpression();
					CtVariableReference va = ((CtVariableAccess) rhs).getVariable() ;
					if(va instanceof CtLocalVariableReference || va instanceof CtParameterReference) {
						return va;
					}
				}
			}
		} else if (element instanceof CtAssignment) {
			CtExpression rhs = ((CtAssignment) element).getAssignment();
			if (rhs instanceof CtVariableAccess) {
				CtVariableReference va = ((CtVariableAccess) rhs).getVariable() ;
				if(va instanceof CtLocalVariableReference || va instanceof CtParameterReference) {
					return va;
				}
			}
		}
		return null;
	}
	
	public static void deleteCtForEachLocals(
			Map<CtExecutableReference, Set<CtVariableReference>> opaqueLabeledVars) {

		for(CtExecutableReference methKey : opaqueLabeledVars.keySet()) {
			Set<CtVariableReference> vars = opaqueLabeledVars.get(methKey);
			Set<CtVariableReference> newVars = new HashSet<>();
			for(CtVariableReference vr : vars) {
				if(vr instanceof CtLocalVariableReference ) {
					CtLocalVariableReference lvr = (CtLocalVariableReference)vr;
					if(lvr.getDeclaration().getParent() != null && lvr.getDeclaration().getParent() instanceof CtForEach) {
						// if the right hand is t from CtForEach, i.e., for(T t: list), then ignore
						continue; 
					}
//					String localName = vr.getSimpleName();
//					if(localName.startsWith(CtForEachProcessor.forEachLocal_prefix)
//							|| localName.startsWith(ForLoopProcessor.forLoopLocal_prefix)) {
//						continue;
//					}
				}
				newVars.add(vr);
			}
			opaqueLabeledVars.put(methKey, newVars);
		}
	}
	
}
