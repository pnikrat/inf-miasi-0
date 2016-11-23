package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class EndTermDeposit implements IOperation {
    private final Integer operationTypeId = 5;
    private LocalDate executionDate;
    private String description;

    public EndTermDeposit(TermDeposit termDepositToEnd) {
        BigDecimal accountsMoney = termDepositToEnd.getAssociatedAccount().getBalance();
        if (LocalDate.now().isAfter(termDepositToEnd.getEndDate())) {
            //get money with interest
            InterestCapitalisation capitalisation = new InterestCapitalisation(termDepositToEnd);
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getFinalAmount().add(accountsMoney));
        }
        else //get money without interest
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getBalance().add(accountsMoney));

        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nZakończona lokata: " + termDepositToEnd.toString();
        termDepositToEnd.setIsTermDepositActive(false);
        termDepositToEnd.addOperationToHistory(this);
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
