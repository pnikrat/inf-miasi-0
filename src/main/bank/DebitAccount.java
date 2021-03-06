package bank;

import helpers.OperationComparator;
import interfaces.*;
import operations.CreateDebit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Created by student on 10.12.2016.
 */
public class DebitAccount implements IDebitable {
    private IDebitable decoratedAccount;
    private BigDecimal maximumDebit;
    private BigDecimal currentDebit;

    public DebitAccount(IDebitable decoratedAccount, BigDecimal maximumDebit) {
        this.decoratedAccount = decoratedAccount;
        this.maximumDebit = maximumDebit;
        this.currentDebit = BigDecimal.ZERO;
    }

    @Override
    public Integer getOwnerId() {
        return decoratedAccount.getOwnerId();
    }

    @Override
    public String getProductNumber() {
        return decoratedAccount.getProductNumber();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        decoratedAccount.setBalance(balance);
    }

    @Override
    public void setCreationDate(LocalDate creationDate) {
        decoratedAccount.setCreationDate(creationDate);
    }

    @Override
    public LocalDate getCreationDate() {
        return decoratedAccount.getCreationDate();
    }

    @Override
    public BigDecimal getBalance() {
        return decoratedAccount.getBalance();
    }

    @Override
    public BigDecimal getBalanceWithDebit() {
        //1500 + 300 - |-50|
        return decoratedAccount.getBalanceWithDebit().add(maximumDebit.subtract(currentDebit.abs()));
    }

    @Override
    public BigDecimal getDebit() {
        return currentDebit;
    }

    @Override
    public void setDebit(BigDecimal amount) {
        currentDebit = amount;
    }

    @Override
    public void setInterestRateMechanism(IInterestRate interestRateMechanism) {
        decoratedAccount.setInterestRateMechanism(interestRateMechanism);
    }

    @Override
    public IInterestRate getInterestRateMechanism() {
        return decoratedAccount.getInterestRateMechanism();
    }

    @Override
    public List<IOperation> getOperationHistory() {
        return decoratedAccount.getOperationHistory();
    }

    @Override
    public void addOperationToHistory(IOperation operation) {
        decoratedAccount.getOperationHistory().add(operation);
        Collections.sort(decoratedAccount.getOperationHistory(), new OperationComparator());
    }

    /*
        @return Returns 1 if operation is valid. For example: Withdrawing 1400 zl from 50zl account is NOT valid
     */
    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        BigDecimal temp = decoratedAccount.getBalanceWithDebit().subtract(amount);
        return temp.compareTo(BigDecimal.ZERO) >= 0;
    }

    @Override
    public String toString() {
        return "Właściciel ID: " + decoratedAccount.getOwnerId() + "\nNumer rachunku: "
                + decoratedAccount.getProductNumber()
                + " Saldo: " + decoratedAccount.getBalance().toString();
    }

    @Override
    public void accept(IProductVisitor visitor) {
        visitor.visit(this);
    }
}
