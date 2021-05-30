package lbs.harvard.coinflow.lattice;


public final class GraphNode{
	   String l = "";
	   GraphNode(GraphNode gn){
		   l = gn.l;
	   }
	   GraphNode(String i){
		   l = i;
	   }
	   
	   public String toString() {
		   return l;
	   }
	   
	   @Override
	    public boolean equals(Object o) {
		   if (o instanceof  GraphNode) {
			   return this.l.equals(o.toString());
		   }
		   return false;
	   }
	   
	   @Override
	   public int hashCode() {
		   return l.hashCode();
	   }
}