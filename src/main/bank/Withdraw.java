package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class Withdraw implements IOperation {
    private Integer operationTypeId = 2;
    private LocalDate executionDate;
    private String description;

    public Withdraw(IProduct withdrawTargetProduct, BigDecimal withdrawAmount) {
        BigDecimal targetProductMoney = withdrawTargetProduct.getBalance();
        BigDecimal difference = targetProductMoney.subtract(withdrawAmount);
        if (difference.compareTo(BigDecimal.ZERO) >= 0) {
            this.executionDate = LocalDate.now();
            this.description = "OperationID: " + operationTypeId + " " + withdrawTargetProduct.toString();
            withdrawTargetProduct.setBalance(difference);
            withdrawTargetProduct.addOperationToHistory(this);
        }
        // TODO: Add DEBIT possibility
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
