package operations;

import interfaces.IInterestRate;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

import java.time.LocalDate;

/**
 * Created by student on 10.12.2016.
 */
public class InterestMechanismChange implements IOperation {
    private final Integer operationTypeId = 9;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct productToChangeMechanism;
    private IInterestRate newMechanism;

    public InterestMechanismChange(IProduct productToChangeMechanism, IInterestRate newMechanism) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nZmiana mechanizmu naliczania odsetek dla: " + productToChangeMechanism.toString();
        this.productToChangeMechanism = productToChangeMechanism;
        this.newMechanism = newMechanism;
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
        productToChangeMechanism.setInterestRateMechanism(newMechanism);
        wasExecuted = true;
        productToChangeMechanism.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
