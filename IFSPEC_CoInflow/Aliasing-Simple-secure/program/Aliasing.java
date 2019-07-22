import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Aliasing {

    static class A { 
        int i; 
    }

    static void set(A v1, A v2, int h) {
    		CoInFlowUserAPI.raiseObjLabel(v1, CoInFlowUserAPI.getLattice().getLabelByName("high"));
        v1.i = h;
    }

    static int getNumber() {return 42;}

    static int test(int i){
    		A v1 = new A();
        A v2 = new A();        
        set (v1, v2, i);
        return v2.i;
    }

    public static void main (String args[]) throws Exception {
        test(getNumber());
    }
}
