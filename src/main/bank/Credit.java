package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Przemek on 2016-11-13.
 */
public class Credit implements IProduct {
    private Account associatedAccount;
    private String creditNumber;
    private BigDecimal borrowedAmount;
    private LocalDate creationDate;
    //TODO remove amount to payback once interest rate is done
    private BigDecimal amountToPayback;

    private BigDecimal interestRate;
    private boolean isCreditActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public Credit(Account associatedAccount, BigDecimal borrowedAmount, String creditNumber, BigDecimal interestRate) {
        this.isCreditActive = true;
        this.associatedAccount = associatedAccount;
        this.creditNumber = creditNumber;
        this.borrowedAmount = borrowedAmount;
        this.creationDate = LocalDate.now();
        //TODO interest rate change
        this.interestRate = interestRate;

        BigDecimal tempNumber = borrowedAmount.add(interestRate);
        this.amountToPayback = tempNumber;

        CreateCredit createOperation = new CreateCredit(associatedAccount, this, borrowedAmount);
    }

    public Account getAssociatedAccount() { return associatedAccount; }

    @Override
    public String getProductNumber() {
        return creditNumber;
    }

    @Override
    public BigDecimal getBalance() {
        return amountToPayback;
    }

    @Override
    public void setBalance(BigDecimal amount) {
        //TODO: setBalance & getBalance in Credit and TermDeposit
    }

    @Override
    public List<IOperation> getOperationHistory() {
        return operationHistory;
    }

    public boolean getIsCreditActive() {return isCreditActive; }

    public void setIsCreditActive(boolean value) {
        isCreditActive = value;
    }

    public BigDecimal getAmountToPayback() {return amountToPayback; }

    @Override
    public void productDeposit(BigDecimal amount) {

    }

    @Override
    public void productWithdrawal(BigDecimal amount) {

    }

    @Override
    public void addOperationToHistory(IOperation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    public boolean repayCredit() {
        if(associatedAccount.isBalancePositive(amountToPayback)) {
            RepayCredit repayOperation = new RepayCredit(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean canWithdrawMoney() {
        return false;
    }

    @Override
    public boolean canDepositMoney() {
        return false;
    }

    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public String toString() {
        return "Kredyt ID: " + creditNumber + ". Pożyczona kwota: " + borrowedAmount + "\n"
                + "Powiązane konto: \n" + associatedAccount.toString();
    }
}
