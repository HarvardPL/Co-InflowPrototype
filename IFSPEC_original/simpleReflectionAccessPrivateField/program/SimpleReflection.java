import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;


public class SimpleReflection {
	public static void main(String[] args) throws IOException, InterruptedException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		SecureSafe superSecureSafe = new SecureSafe();
		System.out.println(SimpleReflection.hackSafe(superSecureSafe));
	}
	
	public static String hackSafe(SecureSafe easyToHackSafe) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field insecurePasswordField = SecureSafe.class.getDeclaredField("password");
		insecurePasswordField.setAccessible(true);
		return insecurePasswordField.get(easyToHackSafe).toString();
	}
}
