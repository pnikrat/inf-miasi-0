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
    private BigDecimal amountToPayback;
    private LocalDate creationDate;
    private LocalDate repaymentDate;
    private IInterestRate interestRateMechanism;
    private boolean isCreditActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public Credit(Account associatedAccount, BigDecimal borrowedAmount, LocalDate repaymentDate,
                  String creditNumber, IInterestRate interestRateMechanism) {
        this.isCreditActive = true;
        this.associatedAccount = associatedAccount;
        this.creditNumber = creditNumber;
        this.borrowedAmount = borrowedAmount;
        this.creationDate = LocalDate.now();
        this.repaymentDate = repaymentDate;
        this.interestRateMechanism = interestRateMechanism;
        CreateCredit createOperation = new CreateCredit(associatedAccount, this, borrowedAmount);
    }

    public Account getAssociatedAccount() { return associatedAccount; }

    @Override
    public String getProductNumber() {
        return creditNumber;
    }

    @Override
    public void setInterestRateMechanism(IInterestRate interestRateMechanism) {
        this.interestRateMechanism = interestRateMechanism;
    }

    @Override
    public IInterestRate getInterestRateMechanism() {
        return interestRateMechanism;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getRepaymentDate() {
        return repaymentDate;
    }

    @Override
    public BigDecimal getBalance() {
        return borrowedAmount;
    }

    @Override
    public void setBalance(BigDecimal amountToPayback) {
        this.amountToPayback = amountToPayback;
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
