import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Main {

    static class A {
        int val;

        A(int val) {
            this.val = val;
        }

        void update(int val) {
            this.val = val;
        }
    }

    static int secret = 42;

    public static void main(String[] args) {
        A a = new A(1);
        A b = new A(1);
        A c = b;

        CoInFlowUserAPI.raiseObjLabel(a, 
        		CoInFlowUserAPI.getLattice().getLabelByName("high"));
        doUpdate(a, secret);
        System.out.println(c.val);
    }

    static void doUpdate(A a, int val) {
        a.update(val);
    }
}
