package operations;

import bank.*;
import interfaces.IOperation;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-22.
 */
public class CreateCredit implements IOperation {
    private final Integer operationTypeId = 6;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct associatedAccount;
    private Credit createdCredit;
    private BigDecimal creditAmount;

    public CreateCredit(IProduct associatedAccount, Credit createdCredit, BigDecimal creditAmount) {

        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nStworzony kredyt: " + createdCredit.toString();
        this.associatedAccount = associatedAccount;
        this.createdCredit = createdCredit;
        this.creditAmount = creditAmount;

        executeOperation();
    }

    @Override
    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    @Override
    public LocalDate getExecutionDate() {
        return executionDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean getWasExecuted() {
        return wasExecuted;
    }

    @Override
    public void executeOperation() {
        //associatedAccount.setBalance(associatedAccount.getBalance().add(creditAmount));

        if (associatedAccount instanceof DebitAccount) { //possible debit
            //depo 100$, have to lower debit for 50$ and add 50$ to regular acc
            BigDecimal depositLeftAfterDebit = creditAmount.subtract(associatedAccount.getDebit().abs());
            if (depositLeftAfterDebit.compareTo(BigDecimal.ZERO) > 0) { //still money left, add to regular acc
                associatedAccount.setDebit(BigDecimal.ZERO);
                associatedAccount.setBalance(associatedAccount.getBalance().add(depositLeftAfterDebit));
            }
            else if (depositLeftAfterDebit.compareTo(BigDecimal.ZERO) <= 0) {  //depo too low to cover whole debit
                associatedAccount.setDebit(associatedAccount.getDebit().add(creditAmount));
            }
        }
        else {
            associatedAccount.setBalance(associatedAccount.getBalance().add(creditAmount));
        }

        InterestCapitalisation amountToPaybackCapitalisation = new InterestCapitalisation(createdCredit);
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
    }
}
