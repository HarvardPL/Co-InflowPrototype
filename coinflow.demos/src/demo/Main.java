package demo;

import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.Labeled;
import lbs.harvard.coinflow.lattice.IFCLabelString;

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
		Labeled<String> r = CoInFlowUserAPI.toLabeled(bob.getPhoneNum(), new IFCLabelString("bob"));
		System.out.println(CoInFlowUserAPI.unlabel(r));
	}
	
	static String formatNum(String num) {
		return num;
	}
}
