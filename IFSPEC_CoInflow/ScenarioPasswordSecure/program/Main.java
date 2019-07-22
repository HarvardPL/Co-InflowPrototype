
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	//just here to have an entry point for the program
	public static void main(String[] args) throws Exception {
		String exitKeyword = "exit";
		boolean exit = false;
		
		PasswordManager pm = new PasswordManager();
		
		System.out.println("To exit, type: " + exitKeyword);
		
		while(!exit) {
			System.out.println("Enter password:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = br.readLine();
			exit |= input.equals(exitKeyword);
			pm.tryLogin(input);
			
			System.out.println("Run completed, run again");
		}
	}
}
