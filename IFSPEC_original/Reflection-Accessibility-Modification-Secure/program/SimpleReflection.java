
import java.lang.reflect.Field;


public class SimpleReflection {
	private static boolean secret = true;
    private static boolean pub = true;
	
	public static void main(String args[]) throws Exception {
		SecureSafe superSecureSafe = new SecureSafe();
		
		Field passwordField = SecureSafe.class.getDeclaredField("password");
		passwordField.setAccessible(pub);
		try {
			passwordField.get(superSecureSafe).toString();
			System.out.println("The boolean was false");
		} catch(Exception e) {
			System.out.println("The boolean was true");
		}
	}
}
