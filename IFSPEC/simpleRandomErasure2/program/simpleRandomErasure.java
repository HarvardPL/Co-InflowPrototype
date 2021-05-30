
import java.util.Random;

public class simpleRandomErasure {
	private static int secret = 42;
	
	public static void main(String[] args) {
		int output = secret;
		
		Random random = new Random();
		output += random.nextInt(Integer.MAX_VALUE) - secret;
		
		System.out.println(Integer.toString(output));
	}
}
