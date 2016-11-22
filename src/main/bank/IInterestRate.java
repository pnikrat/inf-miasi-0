package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-22.
 */
public interface IInterestRate {
    /*
    Used formula
    V = V0 * (1 + r/m)^(m*n)
     */

    public BigDecimal capitalisation(BigDecimal currentProductBalance);
    public BigDecimal calculateFinalValue(LocalDate startDate, LocalDate endDate);
}
