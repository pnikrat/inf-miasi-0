package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class CreateTermDeposit implements IOperation {
    private Integer operationTypeId = 4;
    private LocalDate executionDate;
    private String description;

    public CreateTermDeposit(Account associatedAccount, TermDeposit createdTermDeposit, BigDecimal termDepositAmount) {
        associatedAccount.setBalance(associatedAccount.getBalance().subtract(termDepositAmount));
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                            + "\nStworzona lokata: " + createdTermDeposit.toString();
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
