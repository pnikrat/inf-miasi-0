package bank;

import java.math.BigDecimal;

/**
 * Created by student on 05.11.2016.
 */
public interface IProduct {
    String getProductNumber();

    void productDeposit(BigDecimal amount);
    void productWithdrawal(BigDecimal amount);

    boolean initiateLocalTransfer(IProduct targetBankProduct, BigDecimal amount);
    boolean acceptLocalTransfer(BigDecimal amount);

    boolean isBalancePositive(BigDecimal amount);
    String toString();
}
