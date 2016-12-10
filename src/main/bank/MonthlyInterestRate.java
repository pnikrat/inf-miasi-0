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
    private BigDecimal interestRate;
    private final Integer numberOfCapitalisationsInYear = 12;
    private BigDecimal capitaFactor;

    public MonthlyInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        this.capitaFactor = new BigDecimal(numberOfCapitalisationsInYear);
    }

    @Override
    public Integer getNumberOfCapitalisations() {
        return numberOfCapitalisationsInYear;
    }

    @Override
    public BigDecimal capitalisation(IProduct currentProduct) {
        return currentProduct.getBalance().multiply((interestRate.divide(capitaFactor, 10, BigDecimal.ROUND_HALF_UP))
                .add(BigDecimal.ONE)).subtract(currentProduct.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateFinalValue(BigDecimal startingCapital, LocalDate startDate, LocalDate endDate) {
        int months = (int) MONTHS.between(startDate, endDate);
        BigDecimal powerResult = new BigDecimal("0.00"); //drop scale 2 constraint for power factor calculation
        powerResult = powerResult.add(((interestRate.divide(capitaFactor, 10, BigDecimal.ROUND_HALF_UP))
                .add(BigDecimal.ONE)).pow(months));
        return startingCapital.multiply(powerResult).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
