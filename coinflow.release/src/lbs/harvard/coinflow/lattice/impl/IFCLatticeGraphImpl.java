package lbs.harvard.coinflow.lattice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.lattice.IFCLabelString;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.lattice.principal.Principal;

/**
 * Graph implementation of IFCLattice. 
 * Programmers should provide the flow relations (edges) of the lattice. 
 * Then, it uses topological sorting to get an order of the flow relations and build a lattice. 
 *  
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class IFCLatticeGraphImpl implements IFCLattice {

	// The graph should be a lattice, but we don't check it at the current version
	   
	boolean hasSetup = false;
	MutableGraph<IFCLabel> graph = GraphBuilder.directed().build();
	List<IFCLabel> orderedNodes = new ArrayList<>();
	/** This map includes all flowsTo relations. It considers the flowsTo relations caused by actsfor, 
	    for example, if we have the following lattice, and alice actsfor {ab}
	   	      ab
	   	  a         b
	   	      L

	   	  then alice can access data owned by a and b
    */ 
	Map<IFCLabel, Set<IFCLabel>> flowsToMap = new HashMap<>();
	MutableGraph<IFCLabel> graphWithActsfor = GraphBuilder.directed().build();
	
	public IFCLatticeGraphImpl(Map<IFCLabel, Set<IFCLabel>> flowsRelation){
		Iterator<IFCLabel> i = flowsRelation.keySet().iterator();
		while(i.hasNext()) {
			IFCLabel from = i.next();
			for(IFCLabel to : flowsRelation.get(from)) {
				addFlowRelation(from, to);
			}
		}
	}
	
	public IFCLatticeGraphImpl() {
	}
	
	public void addActsforRelation(IFCLabel actor, IFCLabel actedfor) {
		graphWithActsfor.putEdge(actedfor, actor);
	}
	   
   /**
    * This function works for from and to principles that are in the lattice; another version is flowsToWithActsfor()
    * @param from
    * @param to
    * @return
    */

	@Override
    public boolean flowsTo(IFCLabel from, IFCLabel to) {
	   return Graphs.reachableNodes(graph, from).contains(to);
    }
	   
	   /**
	    * This function works with principles that may be involved in actsfor relation
	    * @param from
	    * @param to
	    * @return
	    */
	   public boolean flowsToWithActsfor(IFCLabel from, IFCLabel to) {
		   return Graphs.reachableNodes(graphWithActsfor, from).contains(to);
	   }
	   
	   public void addFlowRelation(IFCLabel from, IFCLabel to) {
		   graph.putEdge(from, to);
		   graphWithActsfor.putEdge(from, to);
	   }
	   
	   public IFCLabel leastUpperBound(IFCLabel n1, IFCLabel n2) {
		   checkSetup();
		   for(int i =0 ; i < orderedNodes.size(); i++) {
			   IFCLabel gn = orderedNodes.get(i);
			   if(Graphs.reachableNodes(graph, n1).contains(gn)
					   && Graphs.reachableNodes(graph, n2).contains(gn)
					   ) {
				   return gn;
			   }
		   }
		   return null;
	   }
	   
	   public IFCLabel greatestLowerBound(IFCLabel n1, IFCLabel n2) {
		   checkSetup();
		   for(int i =orderedNodes.size() -1 ; i >=0 ; i--) {
			   IFCLabel gn = orderedNodes.get(i);
			   if(Graphs.reachableNodes(graph, gn).contains(n1)
					   && Graphs.reachableNodes(graph, gn).contains(n2)
					   ) {
				   return gn;
			   }
		   }
		   return null;
	   }
	
	   
	    private static void topologicalSortUtil(IFCLabel v, Set<IFCLabel> visited,
	                             Stack<IFCLabel> stack, MutableGraph<IFCLabel> inputGraph)
	    {
	        visited.add(v);
	        Iterator<IFCLabel> it = inputGraph.successors(v).iterator();
	        while (it.hasNext()) {
	        	IFCLabel i = it.next();
	            if (!visited.contains(i))
	                topologicalSortUtil(i, visited, stack, inputGraph);
	        }
	        stack.push(v);
	    }
	    
	    private void topologicalSort()
	    {
	        Stack<IFCLabel> stack = new Stack<IFCLabel>();
	        Set<IFCLabel> visited = new HashSet<IFCLabel>();
	        Iterator<IFCLabel> it = graph.nodes().iterator();
	        while (it.hasNext()) {
	        	IFCLabel gn = it.next();
	        	    if (!visited.contains(gn))
		                topologicalSortUtil(gn, visited, stack, graph);
	        }
	        while (stack.empty() == false)
	        	orderedNodes.add(stack.pop());
	    }
	    
	    public void setup() {
	    	topologicalSort();
	    	flowsToMap.clear();
	    	for(int i =0 ; i < orderedNodes.size(); i++) {
	    		IFCLabel gn = orderedNodes.get(i);
	    		Set<IFCLabel> flowsToNodes = new HashSet<>();
	    		for(IFCLabel n : Graphs.reachableNodes(graph, gn)) {
	    			flowsToNodes.add(n);
	    		}
	    		flowsToMap.put(gn, flowsToNodes);
	    	}
	    	hasSetup = true;
	    }
	
	
	private void checkSetup() {
		if(!hasSetup) {
			setup();
		}
	}
	    
	@Override
	public IFCLabel join(IFCLabel t1, IFCLabel t2) {
		checkSetup();
		return leastUpperBound(t1, t2);
	}

	@Override
	public IFCLabel meet(IFCLabel t1, IFCLabel t2) {
		checkSetup();
		return greatestLowerBound(t1, t2);
	}

	@Override
	public IFCLabel bot() {
		checkSetup();
		if (orderedNodes.size() == 0) {
			System.err.println("It must be a non-empty lattice");
		}
		return orderedNodes.get(0);
	}

	@Override
	public IFCLabel top() {
		checkSetup();
		return orderedNodes.get(orderedNodes.size()-1);
	}

	@Override
	public IFCLabel lookup(Principal p) {
		checkSetup();
		return new IFCLabelString(p.getIdentifier());
	}

	@Override
	public IFCLabel memoPrincipal(Principal p, IFCLabel label) {
		checkSetup();
		return null;
	}
	
	public boolean hasCycle(){
		return Graphs.hasCycle(graph);
	}
	
	/**
	 * Check if the graph is a lattice. This function call may get very expensive, depending on the side of the graph
	 * @return true if this graph a lattice, otherwise no
	 */
	public boolean isLattice() {
		checkSetup();
		if(Graphs.hasCycle(graph)) {
			return false;
		}
		
		for(int i = 0; i<  orderedNodes.size() ; i++) {
			IFCLabel li = orderedNodes.get(i);
			for(int j = i+1; j < orderedNodes.size(); j++) {
				IFCLabel lj = orderedNodes.get(j);
				if(greatestLowerBound(li, lj) == null || leastUpperBound(li, lj) == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static IFCLattice makeDefaultLattice() {
		IFCLatticeGraphImpl imp = new IFCLatticeGraphImpl();
		imp.addFlowRelation(new IFCLabelString("L"), new IFCLabelString("H"));
		return imp;
	}
	
	public static void main(String[] args) {
		IFCLatticeGraphImpl imp = new IFCLatticeGraphImpl();
		imp.addFlowRelation(new IFCLabelString("L"), new IFCLabelString("alice"));
		imp.addFlowRelation(new IFCLabelString("L"), new IFCLabelString("bob"));
		imp.addFlowRelation(new IFCLabelString("alice"), new IFCLabelString("H"));
		imp.addFlowRelation(new IFCLabelString("bob"), new IFCLabelString("H"));
		System.out.println(imp.bot());

//		imp.addFlowRelation(new IFCLabelString("0"), new IFCLabelString("b"));
//        imp.addFlowRelation(new IFCLabelString("0"), new IFCLabelString("b"));
//        imp.addFlowRelation(new IFCLabelString("0"), new IFCLabelString("c"));
//        imp.addFlowRelation(new IFCLabelString("b"), new IFCLabelString("ab"));
//        imp.addFlowRelation(new IFCLabelString("a"), new IFCLabelString("ab"));
//        imp.addFlowRelation(new IFCLabelString("c"), new IFCLabelString("d"));
//        imp.addFlowRelation(new IFCLabelString("ab"), new IFCLabelString("top"));
//        imp.addFlowRelation(new IFCLabelString("d"), new IFCLabelString("top"));
//        
//        imp.addActsforRelation(new IFCLabelString("alice"), new IFCLabelString("ab"));
//        
//        imp.setup();
//        System.out.println(imp.isLattice());
//        
//        System.out.println(imp.meet(new IFCLabelString("b"), new IFCLabelString("ab")));
//        System.out.println(imp.join(new IFCLabelString("b"), new IFCLabelString("ab")));
//        System.out.println(imp.bot());
//        System.out.println(imp.top());
//        
//        System.out.println(imp.flowsToWithActsfor(new IFCLabelString("b"), new IFCLabelString("alice")));
	}
}
