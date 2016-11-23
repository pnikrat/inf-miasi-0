package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Account implements IProduct {
    private final String accountNumber;
    private BigDecimal balance;
    private LocalDate creationDate;
    private Integer ownerId;
    private IInterestRate interestRateMechanism;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public Account(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism) {
        this.accountNumber = accountNumber;
        this.balance = new BigDecimal("0.0").setScale(2, BigDecimal.ROUND_HALF_UP);
        this.creationDate = LocalDate.now();
        this.ownerId = ownerId;
        this.interestRateMechanism = interestRateMechanism;
    }

    @Override
    public String getProductNumber() {
        return accountNumber;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public BigDecimal getBalance() { return balance; }

    @Override
    public IInterestRate getInterestRateMechanism() {
        return interestRateMechanism;
    }

    @Override
    public List<IOperation> getOperationHistory() {
        return operationHistory;
    }

    @Override
    public void productDeposit(BigDecimal amount) {
        balance = balance.add(amount);
        //addOperationToHistory(new Operation(operationType.DEPOSIT, LocalDate.now(), "Wplata"));
    }

    @Override
    public void productWithdrawal(BigDecimal amount) {
        if (!isBalancePositive(amount))
                return;
        balance = balance.subtract(amount);
        //addOperationToHistory(new Operation(operationType.WITHDRAW, LocalDate.now(), "Wyplata"));
    }

    @Override
    public void addOperationToHistory(IOperation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    @Override
    public boolean canDepositMoney() {
        return true;
    }

    @Override
    public boolean canWithdrawMoney() {
        return true;
    }

    /*
        @return Returns 1 if operation is valid. For example: Withdrawing 1400 zl from 50zl account is NOT valid
        Does NOT YET take into account the possibility of having active DEBIT on Account
     */
    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        BigDecimal temp = balance.subtract(amount);
        return temp.compareTo(BigDecimal.ZERO) >= 0;
    }

    @Override
    public String toString() {
        return "Właściciel ID: " + ownerId + "\nNumer rachunku: " + accountNumber
                + " Saldo: " + balance.toString();
    }

}
