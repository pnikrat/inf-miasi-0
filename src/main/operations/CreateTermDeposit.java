package operations;

import interfaces.IDebitable;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;
import bank.TermDeposit;

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

    private IDebitable associatedAccount;
    private TermDeposit createdTermDeposit;
    private BigDecimal termDepositAmount;

    public CreateTermDeposit(IDebitable associatedAccount, TermDeposit createdTermDeposit, BigDecimal termDepositAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                            + "\nStworzona lokata: " + createdTermDeposit.toString();
        this.associatedAccount = associatedAccount;
        this.createdTermDeposit = createdTermDeposit;
        this.termDepositAmount = termDepositAmount;
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
        associatedAccount.setBalance(associatedAccount.getBalance().subtract(termDepositAmount));
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
