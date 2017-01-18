package bank;

import helpers.OperationComparator;
import interfaces.*;
import operations.CreateTermDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by student on 05.11.2016.
 */
public class TermDeposit implements ICreditable {
    private IDebitable associatedAccount;
    private String termDepositNumber;
    private BigDecimal originalAmount;
    private BigDecimal finalAmount;
    private LocalDate creationDate;
    private LocalDate endDate;
    private IInterestRate interestRateMechanism;
    private boolean isTermDepositActive;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public TermDeposit(IDebitable associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                       String termDepositNumber, IInterestRate interestRateMechanism) {
        this.isTermDepositActive = true;
        this.associatedAccount = associatedAccount;
        this.termDepositNumber = termDepositNumber;
        this.originalAmount = originalAmount;
        this.finalAmount = BigDecimal.ZERO;
        this.creationDate = LocalDate.now();
        this.endDate = endDate;
        if (interestRateMechanism == null)
            this.interestRateMechanism = new MonthlyInterestRate(new BigDecimal("0.03").setScale(2, BigDecimal.ROUND_HALF_UP));
        else
            this.interestRateMechanism = interestRateMechanism;
    }

    public TermDeposit(IDebitable associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                       String termDepositNumber) {
        this(associatedAccount, originalAmount, endDate, termDepositNumber, null);
    }

    @Override
    public IDebitable getAssociatedAccount() { return associatedAccount; }

    @Override
    public Integer getOwnerId() {
        return associatedAccount.getOwnerId();
    }

    @Override
    public String getProductNumber() {
        return termDepositNumber;
    }

    @Override
    public void setBalance(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Override
    public BigDecimal getBalance() {
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
    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

    @Override
    public LocalDate getEndDate() { return endDate; }

    @Override
    public boolean getIsCreditableProductActive() { return isTermDepositActive; }

    @Override
    public void setIsCreditableProductActive(boolean value) {
        isTermDepositActive = value;
    }

    @Override
    public List<IOperation> getOperationHistory() {
        return operationHistory;
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

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
}
