package bank;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by student on 05.11.2016.
 */
public interface IProduct {
    String getProductNumber();
    List<Operation> getOperationHistory();
//  TODO: Should I add GetBalance to this interface?


    void productDeposit(BigDecimal amount);
    void productWithdrawal(BigDecimal amount);

    boolean initiateLocalTransfer(IProduct targetBankProduct, BigDecimal amount);
    boolean acceptLocalTransfer(BigDecimal amount);

    boolean isBalancePositive(BigDecimal amount);
    String toString();

    void addOperationToHistory(Operation operation);
}
