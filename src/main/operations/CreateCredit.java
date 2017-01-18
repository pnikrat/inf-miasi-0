package operations;

import bank.*;
import interfaces.IDebitable;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
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

    private IDebitable associatedAccount;
    private Credit createdCredit;
    private BigDecimal creditAmount;

    public CreateCredit(IDebitable associatedAccount, Credit createdCredit, BigDecimal creditAmount) {

        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nStworzony kredyt: " + createdCredit.toString();
        this.associatedAccount = associatedAccount;
        this.createdCredit = createdCredit;
        this.creditAmount = creditAmount;
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
    public boolean executeOperation() {
        Deposit depoCreditAmount = new Deposit(associatedAccount, creditAmount);
        depoCreditAmount.executeOperation();

        InterestCapitalisation amountToPaybackCapitalisation = new InterestCapitalisation(createdCredit);
        amountToPaybackCapitalisation.executeOperation();
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
