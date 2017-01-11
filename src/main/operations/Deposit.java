package operations;

import bank.DebitAccount;
import interfaces.IOperation;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class Deposit implements IOperation {
    private final Integer operationTypeId = 1;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct depositTargetProduct;
    private BigDecimal depositAmount;

    public Deposit(IProduct depositTargetProduct, BigDecimal depositAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId + " " + depositTargetProduct.toString();
        this.depositTargetProduct = depositTargetProduct;
        this.depositAmount = depositAmount;

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
            if (depositTargetProduct instanceof DebitAccount) { //possible debit
                //depo 100$, have to lower debit for 50$ and add 50$ to regular acc
                BigDecimal depositLeftAfterDebit = depositAmount.subtract(depositTargetProduct.getDebit().abs());
                if (depositLeftAfterDebit.compareTo(BigDecimal.ZERO) > 0) { //still money left, add to regular acc
                    depositTargetProduct.setDebit(BigDecimal.ZERO);
                    depositTargetProduct.setBalance(depositTargetProduct.getBalance().add(depositLeftAfterDebit));
                }
                else if (depositLeftAfterDebit.compareTo(BigDecimal.ZERO) <= 0) {  //depo too low to cover whole debit
                    depositTargetProduct.setDebit(depositTargetProduct.getDebit().add(depositAmount));
                }
            }
            else {
                depositTargetProduct.setBalance(depositTargetProduct.getBalance().add(depositAmount));
            }
        wasExecuted = true;
        depositTargetProduct.addOperationToHistory(this);
    }
}