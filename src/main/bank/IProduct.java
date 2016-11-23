package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by student on 05.11.2016.
 */
public interface IProduct {
    String getProductNumber();
    List<IOperation> getOperationHistory();
    BigDecimal getBalance();
    void setBalance(BigDecimal amount);
    IInterestRate getInterestRateMechanism();
    LocalDate getCreationDate();

    //TODO: Rethink usage of productDeposit, productWithdrawal, getBalance, setBalance
    void productDeposit(BigDecimal amount);
    void productWithdrawal(BigDecimal amount);

    boolean canDepositMoney();
    boolean canWithdrawMoney();
    boolean isBalancePositive(BigDecimal amount);
    String toString();

    void addOperationToHistory(IOperation operation);
}
