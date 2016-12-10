package bank;

import java.time.LocalDate;

/**
 * Created by student on 10.12.2016.
 */
public class InterestMechanismChange implements IOperation {
    private final Integer operationTypeId = 9;
    private LocalDate executionDate;
    private String description;

    public InterestMechanismChange(IProduct productToChangeMechanism, IInterestRate newMechanism) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nZmiana mechanizmu naliczania odsetek dla: " + productToChangeMechanism.toString();
        productToChangeMechanism.setInterestRateMechanism(newMechanism);
        productToChangeMechanism.addOperationToHistory(this);
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
