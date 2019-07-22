
import java.lang.reflect.Field;


public class SimpleReflection {
	private static boolean secret = true;
	
	public static void main(String args[]) throws Exception {
		SecureSafe superSecureSafe = new SecureSafe();
		
		Field passwordField = SecureSafe.class.getDeclaredField("password");
		passwordField.setAccessible(secret);
		try {
			passwordField.get(superSecureSafe).toString();
			System.out.println("Secret was false");
		} catch(Exception e) {
			System.out.println("Secret was true");
		}
	}
}
