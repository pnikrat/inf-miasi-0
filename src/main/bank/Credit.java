package bank;

import helpers.OperationComparator;
import interfaces.IInterestRate;
import interfaces.IOperation;
import interfaces.IProduct;
import operations.CreateCredit;
import operations.RepayCredit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Przemek on 2016-11-13.
 */
public class Credit implements IProduct {
    private IProduct associatedAccount;
    private String creditNumber;
    private BigDecimal borrowedAmount;
    private BigDecimal amountToPayback;
    private LocalDate creationDate;
    private LocalDate repaymentDate;
    private IInterestRate interestRateMechanism;
    private boolean isCreditActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public Credit(IProduct associatedAccount, BigDecimal borrowedAmount, LocalDate repaymentDate,
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

    public Credit(IProduct associatedAccount, BigDecimal borrowedAmount, LocalDate repaymentDate,
                  String creditNumber) {
        this.isCreditActive = true;
        this.associatedAccount = associatedAccount;
        this.creditNumber = creditNumber;
        this.borrowedAmount = borrowedAmount;
        this.creationDate = LocalDate.now();
        this.repaymentDate = repaymentDate;
        this.interestRateMechanism = new MonthlyInterestRate(new BigDecimal("0.03").setScale(2, BigDecimal.ROUND_HALF_UP));
        CreateCredit createOperation = new CreateCredit(associatedAccount, this, borrowedAmount);
    }

    public IProduct getAssociatedAccount() { return associatedAccount; }

    @Override
    public Integer getOwnerId() {
        return associatedAccount.getOwnerId();
    }

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
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
    public BigDecimal getBalanceWithDebit() {
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
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public String toString() {
        return "Kredyt ID: " + creditNumber + ". Pożyczona kwota: " + borrowedAmount + "\n"
                + "Powiązane konto: \n" + associatedAccount.toString();
    }

    @Override
    public BigDecimal getDebit() {
        return null;
    }

    @Override
    public void setDebit(BigDecimal amount) {

    }
}
