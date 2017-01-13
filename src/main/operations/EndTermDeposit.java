package operations;

import interfaces.IOperation;
import bank.TermDeposit;
import interfaces.IOperationVisitor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class EndTermDeposit implements IOperation {
    private final Integer operationTypeId = 5;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private TermDeposit termDepositToEnd;

    public EndTermDeposit(TermDeposit termDepositToEnd) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nZakończona lokata: " + termDepositToEnd.toString();
        this.termDepositToEnd = termDepositToEnd;
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
        BigDecimal accountsMoney = termDepositToEnd.getAssociatedAccount().getBalance();
        if (LocalDate.now().isAfter(termDepositToEnd.getEndDate())) {
            //get money with interest
            InterestCapitalisation capitalisation = new InterestCapitalisation(termDepositToEnd);
            capitalisation.executeOperation();
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getFinalAmount().add(accountsMoney));
        }
        else //get money without interest
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getBalance().add(accountsMoney));


        termDepositToEnd.setIsTermDepositActive(false);
        wasExecuted = true;
        termDepositToEnd.addOperationToHistory(this);
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
