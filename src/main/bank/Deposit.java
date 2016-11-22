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

    public Deposit(IProduct depositTargetProduct, BigDecimal depositAmount) {
        this.executionDate = LocalDate.now();

        depositTargetProduct.setBalance(depositTargetProduct.getBalance().add(depositAmount));
        this.description = "OperationID: " + operationTypeId + " " + depositTargetProduct.toString();
        depositTargetProduct.addOperationToHistory(this);
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
}
