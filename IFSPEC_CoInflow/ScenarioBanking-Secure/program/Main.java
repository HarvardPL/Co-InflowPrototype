import lbs.harvard.coinflow.CoInFlowUserAPI;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        CoInFlowUserAPI.raiseObjLabel(account, CoInFlowUserAPI.getLattice().getLabelByName("high"));
        account.deposit(100);
        AccountOwner owner = new AccountOwner(account);
        Beneficiary beneficiary = new Beneficiary();
        owner.payBeneficiary(beneficiary, 150);
    }
}
