
public class Account {

    double balance;

    ErrorLog errorLog = new ErrorLog();

    TransactionLog transactionLog = new TransactionLog();

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            this.logTransaction(true);
        } else {
            this.logError("Cannot deposit a non-positive amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0) {
            double newAmount = this.balance - amount;
            if (newAmount > 0) {
                this.balance = newAmount;
                this.logTransaction(false);
                return true;
            } else {
                return false;
            }
        }
        this.logError("Cannot withdraw a non-positive amount.");
        return false;
    }

    private void logTransaction(boolean isDeposit) {
        String transaction = isDeposit ? "Deposit" : "Withdrawal";
        this.transactionLog.logTransaction(transaction + " completed, new balance: " + this.balance);
    }

    public void logError(String message) {
        this.errorLog.logError(message);
    }

}
