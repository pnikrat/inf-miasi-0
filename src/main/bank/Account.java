package bank;

import helpers.OperationComparator;
import interfaces.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Account implements IDebitable {
    private final String accountNumber;
    private BigDecimal balance;
    private LocalDate creationDate;
    private Integer ownerId;
    private IInterestRate interestRateMechanism;
    private List<IOperation> operationHistory = new ArrayList<IOperation>();

    /*
    * Constructor with custom interest rate
     */
    public Account(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism) {
        this.accountNumber = accountNumber;
        this.balance = new BigDecimal("0.0").setScale(2, BigDecimal.ROUND_HALF_UP);
        this.creationDate = LocalDate.now();
        this.ownerId = ownerId;
        if (interestRateMechanism == null)
            this.interestRateMechanism = new MonthlyInterestRate(new BigDecimal("0.03")
                .setScale(2, BigDecimal.ROUND_HALF_UP));
        else
            this.interestRateMechanism = interestRateMechanism;
    }
    /*
    * Constructor with default interest rate
     */
    public Account(String accountNumber, Integer ownerId) {
        this(accountNumber, ownerId, null);
    }

    @Override
    public Integer getOwnerId() {
        return ownerId;
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
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public BigDecimal getBalance() { return balance; }

    @Override
    public BigDecimal getBalanceWithDebit() {
        return balance;
    }

    public BigDecimal getDebit() {
        return null;
    }

    public void setDebit(BigDecimal amount) {
        //do nothing on regular account
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
    public List<IOperation> getOperationHistory() {
        return operationHistory;
    }

    @Override
    public void addOperationToHistory(IOperation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
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

    @Override
    public void accept(IProductVisitor visitor) {
        visitor.visit(this);
    }

}
