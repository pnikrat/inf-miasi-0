package interfaces;

import java.math.BigDecimal;

/**
 * Created by pnikrat on 18.01.17.
 */
public interface IDebitable extends IProduct {
    boolean isBalancePositive(BigDecimal amount);
    BigDecimal getBalanceWithDebit();

    BigDecimal getDebit();
    void setDebit(BigDecimal amount);
}
