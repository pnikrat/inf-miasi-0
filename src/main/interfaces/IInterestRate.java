package interfaces;

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

    Integer getNumberOfCapitalisations();

    BigDecimal capitalisation(IProduct currentProduct);
    BigDecimal calculateFinalValue(IProduct currentProduct, LocalDate endDate);
}
