public class Main {

    static private int secret = 42;

    public static void main(String[] args) {

        int[] arr = new int[5];
        arr[0] = secret;

        if (arr[0] == 42) {
            System.out.println("Found");
        }

    }

}
