package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MONTHS;

/**
 * Created by Przemek on 2016-11-22.
 */
public class MonthlyInterestRate implements IInterestRate {
    private BigDecimal startingCapital;
    private BigDecimal interestRate;
    private final Integer numberOfCapitalisationsInYear = 12;
    private BigDecimal capitaFactor;

    public MonthlyInterestRate(IProduct associatedProduct, BigDecimal interestRate) {
        this.startingCapital = associatedProduct.getBalance();
        this.interestRate = interestRate;
        this.capitaFactor = new BigDecimal(numberOfCapitalisationsInYear);
    }

    @Override
    public BigDecimal capitalisation(BigDecimal currentProductBalance) {
        return currentProductBalance.multiply((interestRate.divide(capitaFactor, 10, BigDecimal.ROUND_HALF_UP))
                .add(BigDecimal.ONE)).subtract(currentProductBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateFinalValue(LocalDate startDate, LocalDate endDate) {
        int months = (int) MONTHS.between(startDate, endDate);
        BigDecimal powerResult = new BigDecimal("0.00"); //drop scale 2 constraint for power factor calculation
        powerResult = powerResult.add(((interestRate.divide(capitaFactor, 10, BigDecimal.ROUND_HALF_UP))
                .add(BigDecimal.ONE)).pow(months));
        return startingCapital.multiply(powerResult).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
