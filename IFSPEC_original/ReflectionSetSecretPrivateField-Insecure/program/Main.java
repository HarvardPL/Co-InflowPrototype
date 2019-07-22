import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws Exception{
        DataStorage data = new DataStorage();
        PasswordStorage pwd = new PasswordStorage();

        Field pwdField = PasswordStorage.class.getDeclaredField("password");
        pwdField.setAccessible(true);

        Field dataField = DataStorage.class.getDeclaredField("data");
        dataField.setAccessible(true);

        dataField.set(data, pwdField.get(pwd).toString());
        data.printData();
    }

}
