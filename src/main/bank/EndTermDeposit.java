package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class EndTermDeposit implements IOperation {
    private Integer operationTypeId = 5;
    private LocalDate executionDate;
    private String description;

    public EndTermDeposit(TermDeposit termDepositToEnd) {
        if (LocalDate.now().isAfter(termDepositToEnd.getEndDate())) { //get money with interest
            //TODO ADD MONEY BACK TO ACCOUNT
        }
        else { //get money without interest
            //TODO ADD MONEY BACK TO ACCOUNT
            BigDecimal tempDecimal = termDepositToEnd.getAssociatedAccount().getBalance();
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getBalance().add(tempDecimal));
        }
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
