package operations;

import bank.Credit;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-22.
 */
public class RepayCredit implements IOperation {
    private final Integer operationTypeId = 7;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private Credit creditToRepay;

    public RepayCredit(Credit creditToRepay) {
        this.executionDate = LocalDate.now();
        this.description = "Operation ID: " + operationTypeId + "\nZakończony kredyt: " + creditToRepay.toString();
        this.creditToRepay = creditToRepay;
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
        BigDecimal repayAmount = creditToRepay.getAmountToPayback();
        BigDecimal accountsMoney = creditToRepay.getAssociatedAccount().getBalance();
        if (accountsMoney.subtract(repayAmount).compareTo(BigDecimal.ZERO) >= 0) {
            creditToRepay.getAssociatedAccount().setBalance(accountsMoney.subtract(repayAmount));

            creditToRepay.setIsCreditActive(false);
            wasExecuted = true;
            creditToRepay.addOperationToHistory(this);
            return wasExecuted;
        }
        else
            return false;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
