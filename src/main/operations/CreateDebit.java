package operations;

import interfaces.IDebitable;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by student on 10.12.2016.
 */
public class CreateDebit implements IOperation {
    private final Integer operationTypeId = 10;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IDebitable associatedAccount;
    private BigDecimal maximumDebit;

    public CreateDebit(IDebitable associatedAccount, BigDecimal maximumDebit) {

        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nStworzony debet dla konta: " + associatedAccount.toString();
        this.associatedAccount = associatedAccount;
        this.maximumDebit = maximumDebit;
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
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
