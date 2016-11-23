package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.IntSummaryStatistics;

/**
 * Created by Przemek on 2016-11-22.
 */
public class YearlyInterestRate implements IInterestRate {
    private BigDecimal interestRate;
    private final Integer numberOfCapitalisationsInYear = 1;


    public YearlyInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public BigDecimal capitalisation(BigDecimal currentProductBalance) {
        return currentProductBalance.multiply(interestRate.add(BigDecimal.ONE))
                .subtract(currentProductBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateFinalValue(BigDecimal startingCapital, LocalDate startDate, LocalDate endDate) {
        Period totalInvestmentTime = Period.between(startDate, endDate);
        int years = totalInvestmentTime.getYears();
        BigDecimal powerResult = new BigDecimal("0.00"); //drop scale 2 constraint for power factor calculation
        powerResult = powerResult.add((interestRate.add(BigDecimal.ONE)).pow(years));
        return startingCapital.multiply(powerResult).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
