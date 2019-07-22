public class PasswordManager {
	private String password = "supersecret";
	private int invalidTries = 0;
	private int maximumTries = 10;
	private boolean loggedIn = false;
	
	public void tryLogin(String tryedPassword) {
		if(this.invalidTries < this.maximumTries) {
			if(this.password.equals(tryedPassword)) {
				this.loggedIn = true;
				this.invalidTries=0;
			} else {
				this.loggedIn = false;
				this.invalidTries++;
			}
		} else {
			System.out.println("No more password tries allowed");
		}
	}
}
