
public class ObjectSensLeak {
	
	public static int high = 0;
	public static int low = 1;
	


	public static void main(String[] args) {
		test(high,low);
	}

        public static int test(int h, int l){
                A a1 = new A(l);
		A a2 = new A(h);

		return a1.doPrint();
        }

}
