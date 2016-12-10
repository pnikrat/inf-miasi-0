package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class Deposit implements IOperation {
    private final Integer operationTypeId = 1;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct depositTargetProduct;
    private BigDecimal depositAmount;

    public Deposit(IProduct depositTargetProduct, BigDecimal depositAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId + " " + depositTargetProduct.toString();
        this.depositTargetProduct = depositTargetProduct;
        this.depositAmount = depositAmount;

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
        depositTargetProduct.setBalance(depositTargetProduct.getBalance().add(depositAmount));
        wasExecuted = true;
        depositTargetProduct.addOperationToHistory(this);
    }
}
