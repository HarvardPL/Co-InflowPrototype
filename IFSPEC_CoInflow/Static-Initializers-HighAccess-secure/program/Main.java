import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Main {

    private static String secret="secret";

    static class A {
        static String stored;

        static {
        		CoInFlowUserAPI.raiseObjLabel(Main.A.class, CoInFlowUserAPI.getLattice().getLabelByName("high"));
            stored = secret;
            System.out.println("initialized");
        }

        int add(int a, int b) {
            return a + b;
        }
    }

    public static void main(String[] args) {
        A a = new A();
        a.add(1, 2);
    }

}
