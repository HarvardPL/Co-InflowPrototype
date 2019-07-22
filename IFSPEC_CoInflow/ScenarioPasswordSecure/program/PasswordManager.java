import lbs.harvard.coinflow.CoInFlowUserAPI;

public class PasswordManager {
	private String password = "supersecret";
	private int invalidTries = 0;
	private int maximumTries = 10;
	private boolean loggedIn = false;
	
	public void tryLogin(String tryedPassword) {
		checkPassWord(tryedPassword);
		System.out.println("Login Attempt Completed");
	}
	
	private void checkPassWord(String tryedPassword) {
		CoInFlowUserAPI.raiseObjLabel(this, CoInFlowUserAPI.getLattice().getLabelByName("high"));
		if(this.invalidTries < this.maximumTries) {
			if(this.password.equals(tryedPassword)) {
				this.loggedIn = true;
			} else {
				this.loggedIn = false;
				this.invalidTries++;
			}
		}
	}
}

