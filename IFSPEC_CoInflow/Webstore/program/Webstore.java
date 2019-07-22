import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.Labeled;

public class Webstore {
    public int low;
    private int high;

    private static int h;
    private static int l;

    private int product = 0;
    private Labeled<Integer> cc = null;
  
    
   public static void main(String[] args){

      Webstore w = new Webstore();
      w.buyProduct(l,h);
   }

       
   /*Customer buys the product and wants to pay with the given credit card number. 
     Afterwards, the store returns the bought product*/ 
   public int buyProduct(int prod, int cc) {
	  CoInFlowUserAPI.raiseObjLabel(this, CoInFlowUserAPI.getLattice().getLabelByName("low"));
      product = prod;
      this.cc = CoInFlowUserAPI.toLabeled(cc, CoInFlowUserAPI.getLattice().getLabelByName("high"));
      return product;
   }    
}
