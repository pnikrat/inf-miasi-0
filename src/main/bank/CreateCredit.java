package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-22.
 */
public class CreateCredit implements IOperation {
    private final Integer operationTypeId = 6;
    private LocalDate executionDate;
    private String description;

    public CreateCredit(Account associatedAccount, Credit createdCredit, BigDecimal creditAmount) {
        associatedAccount.setBalance(associatedAccount.getBalance().add(creditAmount));
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nStworzony kredyt: " + createdCredit.toString();
        associatedAccount.addOperationToHistory(this);
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
