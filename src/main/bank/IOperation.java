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
     */
    Integer getOperationTypeId();
    LocalDate getExecutionDate();
    String getDescription();
}
