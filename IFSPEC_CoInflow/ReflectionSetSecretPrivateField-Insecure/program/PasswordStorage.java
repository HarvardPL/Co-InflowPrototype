public class PasswordStorage {

    private String password="secret";
    private String version="4.2";

    public String getPassword(String auth) {
        if (auth.equals("secretHash")) {
            return password;
        }
        else {
            return "";
        }
    }

}
