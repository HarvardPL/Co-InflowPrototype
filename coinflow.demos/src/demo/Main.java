package demo;


public class Main {

	public static void main(String[] args) {
		Person alice = Data.alice;
		Person bob = Data.bob;
		
		String a = alice.getPhoneNum();
		String a1 = formatNum(a);
		alice.setPhoneNum(a1);
		
		
		String b = bob.getPhoneNum();
		String b1 = formatNum(b);
		bob.setPhoneNum(b1);
		
		/** demonstrate information flow leak here; 
		 uncomment the code, and do the compilation again */
		// alice.setPhoneNum(b1);
	}
	
	static String formatNum(String num) {
		return num;
	}
}
