package operations;

import bank.*;
import interfaces.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-23.
 */
public class InterestCapitalisation implements IOperation {
    private final Integer operationTypeId = 8;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IInterestRate interestRateMechanism;
    private IProduct productToCapitalise;

    public InterestCapitalisation(IProduct productToCapitalise) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nNaliczenie odsetek dla: " + productToCapitalise.toString();
        this.interestRateMechanism = productToCapitalise.getInterestRateMechanism();
        this.productToCapitalise = productToCapitalise;
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
        if(productToCapitalise instanceof IDebitable)
            capitaliseAccount((IDebitable) productToCapitalise);
        else if(productToCapitalise instanceof ICreditable)
            capitaliseCreditable((ICreditable) productToCapitalise);
        wasExecuted = true;
        productToCapitalise.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }

    private void capitaliseAccount(IDebitable accountToCapitalise) {
        BigDecimal currentAccountBalance = accountToCapitalise.getBalance();
        accountToCapitalise.setBalance(currentAccountBalance
                .add(interestRateMechanism.capitalisation(accountToCapitalise)));
    }

    private void capitaliseCreditable(ICreditable creditableToCapitalise) {
        BigDecimal creditableFinalAmount = interestRateMechanism.calculateFinalValue(creditableToCapitalise,
                creditableToCapitalise.getEndDate());
        creditableToCapitalise.setBalance(creditableFinalAmount);
    }
}
