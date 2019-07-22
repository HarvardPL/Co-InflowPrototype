import lbs.harvard.coinflow.CoInFlowUserAPI;

public class IFMethodContract {
    public static int low;
    private static int high;
  
    
   public static void main(String[] args){
      IFMethodContract ifm = new IFMethodContract();
      ifm.secure_if_high_n5_n1();
   }
       
    
    void secure_if_high_n5_n1() {
    		CoInFlowUserAPI.raiseObjLabel(IFMethodContract.class, CoInFlowUserAPI.getLattice().getLabelByName("high"));
        if (high > 0) {
            low = n5(high);
        } else {
            high = -high;
            low = n5(high + low);
        }
        
    }
    
    
    int n5(int x) {
        high = 2 * x;
        return 15;
    }
    
    
}
