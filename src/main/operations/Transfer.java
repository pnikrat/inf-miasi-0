package operations;

import bank.Account;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by pnikrat on 20.11.16.
 */
public class Transfer implements IOperation {
    private final Integer operationTypeId = 3;
    private LocalDate executionDate;
    private String description;
    private boolean wasExecuted = false;

    private IProduct transferOriginProduct;
    private IProduct transferTargetProduct;
    private BigDecimal transferAmount;

    public Transfer(IProduct transferOriginProduct, IProduct transferTargetProduct, BigDecimal transferAmount) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId + " " + "\nZ konta: " + transferOriginProduct.toString()
                            + "\nNa konto: " + transferTargetProduct.toString();
        this.transferOriginProduct = transferOriginProduct;
        this.transferTargetProduct = transferTargetProduct;
        this.transferAmount = transferAmount;
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
        if (transferOriginProduct instanceof Account && transferTargetProduct instanceof Account) {
            if (transferOriginProduct.isBalancePositive(transferAmount)) {
                transferOriginProduct.setBalance(transferOriginProduct.getBalance().subtract(transferAmount));
                transferTargetProduct.setBalance(transferTargetProduct.getBalance().add(transferAmount));
                wasExecuted=true;
                transferOriginProduct.addOperationToHistory(this);
                transferTargetProduct.addOperationToHistory(this);
            }
        }
        return wasExecuted;
    }

    @Override
    public void accept(IOperationVisitor visitor) {
        visitor.visit(this);
    }
}
