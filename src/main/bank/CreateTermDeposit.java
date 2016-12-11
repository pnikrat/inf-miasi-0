package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class CreateTermDeposit implements IOperation {
    private final Integer operationTypeId = 4;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct associatedAccount;
    private TermDeposit createdTermDeposit;
    private BigDecimal termDepositAmount;

    public CreateTermDeposit(IProduct associatedAccount, TermDeposit createdTermDeposit, BigDecimal termDepositAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                            + "\nStworzona lokata: " + createdTermDeposit.toString();
        this.associatedAccount = associatedAccount;
        this.createdTermDeposit = createdTermDeposit;
        this.termDepositAmount = termDepositAmount;

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
        associatedAccount.setBalance(associatedAccount.getBalance().subtract(termDepositAmount));
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
    }
}
