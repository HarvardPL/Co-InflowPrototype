public class program {

    static int foo(int h) {
        int y = id(h);
        int x = 0;
        return id(x);
    }

    static int id(int x) {
      return x;
    }

    public static void main (String [] args) {
        foo(randInt());
    }

    /** Helper method to obtain a random boolean */
    static boolean randBool() {
        return true;
    }
    /** Helper methot to obtain a random integer */
    static int randInt() {
        return 42;
    }

}
