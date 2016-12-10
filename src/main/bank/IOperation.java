package bank;

import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public interface IOperation {
    /*
    * OperationTypeId:
    * 1 - DEPOSIT
    * 2 - WITHDRAW
    * 3 - TRANSFER
    * 4 - CREATE_TERM_DEPOSIT
    * 5 - END_TERM_DEPOSIT
    * 6 - CREATE_CREDIT
    * 7 - REPAY_CREDIT
    * 8 - INTEREST_CAPITALISATION
    * 9 - INTEREST_MECHANISM_CHANGE
     */
    Integer getOperationTypeId();
    LocalDate getExecutionDate();
    String getDescription();
    boolean getWasExecuted();

    void executeOperation();

    //TODO rethink if finalAmount in TermDeposit should be calculated before end of termdeposit
}
