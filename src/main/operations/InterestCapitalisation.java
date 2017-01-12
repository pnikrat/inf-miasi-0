package operations;

import bank.*;
import interfaces.IInterestRate;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

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
        if(productToCapitalise instanceof Account)
            capitaliseAccount((Account) productToCapitalise);
        else if(productToCapitalise instanceof TermDeposit)
            capitaliseTermDeposit((TermDeposit) productToCapitalise);
        else if(productToCapitalise instanceof Credit)
            capitaliseCredit((Credit) productToCapitalise);
        wasExecuted = true;
        productToCapitalise.addOperationToHistory(this);
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }

    private void capitaliseAccount(Account accountToCapitalise) {
        BigDecimal currentAccountBalance = accountToCapitalise.getBalance();
        accountToCapitalise.setBalance(currentAccountBalance
                .add(interestRateMechanism.capitalisation(accountToCapitalise)));
    }

    private void capitaliseTermDeposit(TermDeposit termDepositToCapitalise) {
        //BigDecimal originalAmount = termDepositToCapitalise.getBalance();
        BigDecimal finalAmount = interestRateMechanism.calculateFinalValue(termDepositToCapitalise,
                 termDepositToCapitalise.getEndDate());
        termDepositToCapitalise.setBalance(finalAmount);
    }

    private void capitaliseCredit(Credit creditToCapitalise) {
        //BigDecimal borrowedAmount = creditToCapitalise.getBalance();
        BigDecimal amountToPayback = interestRateMechanism.calculateFinalValue(creditToCapitalise,
                creditToCapitalise.getRepaymentDate());
        creditToCapitalise.setBalance(amountToPayback);
    }
}
