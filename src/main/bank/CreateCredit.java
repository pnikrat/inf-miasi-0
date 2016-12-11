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
    private boolean wasExecuted = false;

    private IProduct associatedAccount;
    private Credit createdCredit;
    private BigDecimal creditAmount;

    public CreateCredit(IProduct associatedAccount, Credit createdCredit, BigDecimal creditAmount) {

        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nStworzony kredyt: " + createdCredit.toString();
        this.associatedAccount = associatedAccount;
        this.createdCredit = createdCredit;
        this.creditAmount = creditAmount;

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
        associatedAccount.setBalance(associatedAccount.getBalance().add(creditAmount));
        InterestCapitalisation amountToPaybackCapitalisation = new InterestCapitalisation(createdCredit);
        wasExecuted = true;
        associatedAccount.addOperationToHistory(this);
    }
}
