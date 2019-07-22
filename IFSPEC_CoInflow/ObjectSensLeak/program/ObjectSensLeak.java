import lbs.harvard.coinflow.CoInFlowUserAPI;

public class ObjectSensLeak {
	
	public static int high = 0;
	public static int low = 1;
	


	public static void main(String[] args) {
		test(high,low);
	}

      
	public static int test(int h, int l){
        	A a1 = new A(CoInFlowUserAPI.labelData(l, CoInFlowUserAPI.getLattice().getLabelByName("bottom")));
		A a2 = new A(CoInFlowUserAPI.labelData(l, CoInFlowUserAPI.getLattice().getLabelByName("top")));
		return a1.doPrint();
	}

}
