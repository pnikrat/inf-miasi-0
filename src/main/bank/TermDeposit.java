package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bank.Operation.*;

/**
 * Created by student on 05.11.2016.
 */
public class TermDeposit implements IProduct {
    private Account associatedAccount;
    private String termDepositNumber;
    private BigDecimal originalAmount;
    private LocalDate creationDate;
    private LocalDate endDate;
    private BigDecimal interestRate;
    private boolean isTermDepositActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public TermDeposit(Account associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                       String termDepositNumber) {
        this.isTermDepositActive = true;
        this.associatedAccount = associatedAccount;
        this.termDepositNumber = termDepositNumber;
        this.originalAmount = originalAmount;
        this.creationDate = LocalDate.now();
        this.endDate = endDate;
            // interestRate
        CreateTermDeposit createOperation = new CreateTermDeposit(associatedAccount, this, originalAmount);

    }

    public Account getAssociatedAccount() { return associatedAccount; }

    @Override
    public String getProductNumber() {
        return termDepositNumber;
    }

    @Override
    public BigDecimal getBalance() {
        return originalAmount;
    }

    @Override
    public void setBalance(BigDecimal amount) {

    }

    public void setIsTermDepositActive(boolean value) {
        isTermDepositActive = value;
    }

    public LocalDate getEndDate() { return endDate; }

    @Override
    public List<IOperation> getOperationHistory() {
        return operationHistory;
    }

    @Override
    public boolean canDepositMoney() {
        return false;
    }

    @Override
    public boolean canWithdrawMoney() {
        return false;
    }

    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public void productWithdrawal(BigDecimal amount) {

    }

    @Override
    public void addOperationToHistory(IOperation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    @Override
    public void productDeposit(BigDecimal amount) {
        this.originalAmount = amount;
    }

    @Override
    public String toString() {
        return "Lokata ID: " + termDepositNumber + ". Zalozona dnia: " + creationDate.toString()
                + " na kwote: " + originalAmount + ". Koniec lokaty dnia: " + endDate.toString() + ".\n"
                + "Powiazane konto: \n" + associatedAccount.toString();
    }
}
