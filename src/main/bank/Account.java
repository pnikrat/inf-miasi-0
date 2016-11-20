package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import bank.Operation.*;

/**
 * Created by student on 05.11.2016.
 */
public class Account implements IProduct {
    private final String accountNumber;
    private BigDecimal balance;
    private LocalDate creationDate;
    private Integer ownerId;
    private BigDecimal interestRate;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    public Account(String accountNumber, Integer ownerId) {
        this.accountNumber = accountNumber;
        this.balance = new BigDecimal("0.0").setScale(2, BigDecimal.ROUND_HALF_UP);
        this.creationDate = LocalDate.now();
        this.ownerId = ownerId;
//        TODO: Add InterestRate to regular Account once Interests are implemented
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
    public BigDecimal getBalance() { return balance; }

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
        return "Wlasciciel ID: " + ownerId + "\nNumer rachunku: " + accountNumber
                + " Saldo: " + balance.toString();
    }

}
