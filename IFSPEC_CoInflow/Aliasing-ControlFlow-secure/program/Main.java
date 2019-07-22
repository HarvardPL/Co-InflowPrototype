import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Main {

    static class A {
        int val;

        A(int val) {
            this.val = val;
        }
    }

    static private int secret = 42;

    public static void main(String[] args) {
    		A a = new A(1);
        A b = a;

        helper(a, b);

        System.out.println(b.val);
    }
    
    public static void helper(A a, A b) {
    		CoInFlowUserAPI.raiseObjLabel(a, 
    				CoInFlowUserAPI.getLattice().getLabelByName("high"));
    		if (secret == 42) {
            a.val = 2;
        } else {
            a.val = 2;
        }
    }
}
