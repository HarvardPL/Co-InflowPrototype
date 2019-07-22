public class AccountOwner {
    private Account account;

    public AccountOwner(Account account) {
        this.account = account;
    }

    public void payBeneficiary(Beneficiary b, double amount) {
        boolean transactionPossible = this.account.withdraw(amount);
        if (transactionPossible) {
            b.receive(amount);
        }
    }

}
