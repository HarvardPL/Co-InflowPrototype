
public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        account.deposit(100);
        AccountOwner owner = new AccountOwner(account);
        Beneficiary beneficiary = new Beneficiary();
        owner.payBeneficiary(beneficiary, 150);
    }
}
