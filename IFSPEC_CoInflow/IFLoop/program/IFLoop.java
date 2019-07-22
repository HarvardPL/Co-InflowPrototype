import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.CoInflow_channel;
import lbs.harvard.coinflow.internal.Labeled;

import static lbs.harvard.coinflow.CoInFlowUserAPI.*;
public class IFLoop {
  
    
   public static void main(String[] args){
      IFLoop ifl = new IFLoop();
      ifl.secure_ifl(17);
   }
      

    public int secure_ifl(int high) {
	Labeled<Integer> x = new Labeled<>(0, CoInFlowUserAPI.getCurrentLevel());
	int y = 0;
	int low = 23;
        //@ loop_invariant 0 <= y && y <= 10;
        //@ determines low, y, (y < 10 ? x : 0) \by \itself;
        //@ assignable low;
        //@ decreases 10 - y;
	while (y < 10) {
	    low = unlabel(x);
	    if (y == 5) {
			x = toLabeled(high, getLattice().getLabelByName("high"));
			y = 9;
	    }
	    x = toLabeled(unlabel(x) + 1, labelOf(x));
	    y++;
	}
	return low;
    }
}
