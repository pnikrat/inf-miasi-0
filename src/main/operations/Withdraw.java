package operations;

import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class Withdraw implements IOperation {
    private final Integer operationTypeId = 2;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct withdrawTargetProduct;
    private BigDecimal withdrawAmount;

    public Withdraw(IProduct withdrawTargetProduct, BigDecimal withdrawAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId + " " + withdrawTargetProduct.toString();
        this.withdrawTargetProduct = withdrawTargetProduct;
        this.withdrawAmount = withdrawAmount;

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

        if (withdrawTargetProduct.getBalance().compareTo(withdrawAmount) >= 0) { //got 100$ wanna withdraw 80$
            withdrawTargetProduct.setBalance(withdrawTargetProduct.getBalance().subtract(withdrawAmount));
        }
        else { //got 100$ wanna withdraw 120$ (not enough money, check if have debit)
            //check if have debit
            if (withdrawTargetProduct.getBalanceWithDebit().compareTo(withdrawAmount) >= 0) { //got 100$ and 50$ debit withdraw 120$
                BigDecimal regularBalance = withdrawTargetProduct.getBalance(); //might be 0$ or more
                withdrawTargetProduct.setBalance(withdrawTargetProduct.getBalance().subtract(regularBalance));
                BigDecimal amountToBeSubtractedFromDebit = withdrawAmount.subtract(regularBalance);
                withdrawTargetProduct.setDebit(withdrawTargetProduct.getDebit().subtract(amountToBeSubtractedFromDebit));
            }
            else { //not enough money even with debit - return to avoid adding op to history and executing
                return;
            }
        }

        wasExecuted = true;
        withdrawTargetProduct.addOperationToHistory(this);
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
