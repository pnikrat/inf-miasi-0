package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-22.
 */
public class RepayCredit implements IOperation {
    private final Integer operationTypeId = 7;
    private LocalDate executionDate;
    private String description;

    public RepayCredit(Credit creditToRepay) {
        this.executionDate = LocalDate.now();
        this.description = "Operation ID: " + operationTypeId + "\nZakończony kredyt: " + creditToRepay.toString();
        BigDecimal repayAmount = creditToRepay.getAmountToPayback();
        creditToRepay.getAssociatedAccount().setBalance
                (creditToRepay.getAssociatedAccount().getBalance().subtract(repayAmount));
        creditToRepay.setIsCreditActive(false);
        creditToRepay.addOperationToHistory(this);
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
