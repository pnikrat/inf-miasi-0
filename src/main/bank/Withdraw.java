package bank;

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
        if (withdrawTargetProduct instanceof Account) {
            if (withdrawTargetProduct.isBalancePositive(withdrawAmount)) {
                this.executionDate = LocalDate.now();
                this.description = "OperationID: " + operationTypeId + " " + withdrawTargetProduct.toString();
                this.withdrawTargetProduct = withdrawTargetProduct;
                this.withdrawAmount = withdrawAmount;

                executeOperation();
            }
            // TODO: Add DEBIT possibility - decorator mechanism first
        }
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
        withdrawTargetProduct.setBalance(withdrawTargetProduct.getBalance().subtract(withdrawAmount));
        wasExecuted = true;
        withdrawTargetProduct.addOperationToHistory(this);
    }
}
