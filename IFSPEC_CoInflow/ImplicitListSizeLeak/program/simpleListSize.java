
import java.util.ArrayList;

public class simpleListSize {
	
	public static void main(String[] args) {
		int value = 5;
		System.out.println("Running simpleListSize");
		System.out.println("Secret value:   " + value);
		System.out.println("Returned value: " + simpleListSize.listSizeLeak(value));
	}

	public static int listSizeLeak(int h) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		int r = 0;

		for(int i = 0; i < h; i++) {
			list.add(42);
		}

		if (list.size() < 10) {
			r = 1;
		}
		
		return r;
	}
}
