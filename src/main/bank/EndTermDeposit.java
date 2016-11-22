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
        if (LocalDate.now().isAfter(termDepositToEnd.getEndDate())) { //get money with interest
            BigDecimal accountsMoney = termDepositToEnd.getAssociatedAccount().getBalance();
            BigDecimal tempInterest = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getBalance()
                    .add(tempInterest).add(accountsMoney));
        }
        else { //get money without interest
            BigDecimal accounstMoney = termDepositToEnd.getAssociatedAccount().getBalance();
            termDepositToEnd.getAssociatedAccount().setBalance(termDepositToEnd.getBalance().add(accounstMoney));
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
