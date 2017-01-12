package bank;

import helpers.OperationComparator;
import interfaces.IInterestRate;
import interfaces.IOperation;
import interfaces.IProduct;
import interfaces.IProductVisitor;
import operations.CreateTermDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by student on 05.11.2016.
 */
public class TermDeposit implements IProduct {
    private IProduct associatedAccount;
    private String termDepositNumber;
    private BigDecimal originalAmount;
    private BigDecimal finalAmount;
    private LocalDate creationDate;
    private LocalDate endDate;
    private IInterestRate interestRateMechanism;
    private boolean isTermDepositActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public TermDeposit(IProduct associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                       String termDepositNumber, IInterestRate interestRateMechanism) {
        this.isTermDepositActive = true;
        this.associatedAccount = associatedAccount;
        this.termDepositNumber = termDepositNumber;
        this.originalAmount = originalAmount;
        this.finalAmount = BigDecimal.ZERO;
        this.creationDate = LocalDate.now();
        this.endDate = endDate;
        this.interestRateMechanism = interestRateMechanism;
        CreateTermDeposit createOperation = new CreateTermDeposit(associatedAccount, this, originalAmount);
    }

    public TermDeposit(IProduct associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                       String termDepositNumber) {
        this.isTermDepositActive = true;
        this.associatedAccount = associatedAccount;
        this.termDepositNumber = termDepositNumber;
        this.originalAmount = originalAmount;
        this.finalAmount = BigDecimal.ZERO;
        this.creationDate = LocalDate.now();
        this.endDate = endDate;
        this.interestRateMechanism = new MonthlyInterestRate(new BigDecimal("0.03").setScale(2, BigDecimal.ROUND_HALF_UP));
        CreateTermDeposit createOperation = new CreateTermDeposit(associatedAccount, this, originalAmount);
    }

    public IProduct getAssociatedAccount() { return associatedAccount; }

    @Override
    public Integer getOwnerId() {
        return associatedAccount.getOwnerId();
    }

    @Override
    public String getProductNumber() {
        return termDepositNumber;
    }

    @Override
    public BigDecimal getBalance() {
        return originalAmount;
    }

    @Override
    public BigDecimal getBalanceWithDebit() {
        return originalAmount;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
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
    public void setBalance(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public boolean getIsTermDepositActive() { return isTermDepositActive; }

    public void setIsTermDepositActive(boolean value) {
        isTermDepositActive = value;
    }

    public LocalDate getEndDate() { return endDate; }

    //FOR TESTING PURPOSES, should never be used in production!!
    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    @Override
    public List<IOperation> getOperationHistory() {
        return operationHistory;
    }


    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }


    @Override
    public void addOperationToHistory(IOperation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    @Override
    public String toString() {
        return "Lokata ID: " + termDepositNumber + ". Założona dnia: " + creationDate.toString()
                + " na kwotę: " + originalAmount + ". Koniec lokaty dnia: " + endDate.toString() + ".\n"
                + "Powiązane konto: \n" + associatedAccount.toString();
    }

    @Override
    public void accept(IProductVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public BigDecimal getDebit() {
        return null;
    }

    @Override
    public void setDebit(BigDecimal amount) {

    }
}
