package bank;

import interfaces.IInterestRate;
import interfaces.IProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    public Integer getNumberOfCapitalisations() {
        return numberOfCapitalisationsInYear;
    }

    @Override
    public BigDecimal capitalisation(IProduct currentProduct) {
        return currentProduct.getBalance().multiply(interestRate.add(BigDecimal.ONE))
                .subtract(currentProduct.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateFinalValue(IProduct currentProduct, LocalDate endDate) {
        Period totalInvestmentTime = Period.between(currentProduct.getCreationDate(), endDate);
        int years = totalInvestmentTime.getYears();
        BigDecimal powerResult = new BigDecimal("0.00"); //drop scale 2 constraint for power factor calculation
        powerResult = powerResult.add((interestRate.add(BigDecimal.ONE)).pow(years));
        return currentProduct.getBalance().multiply(powerResult).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
