package bank;

import interfaces.IBank;
import operations.Deposit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2016-11-22.
 */
public class YearlyInterestRateTest {
    private IBank testBank;
    private YearlyInterestRate testInterest;
    private LocalDate beginTest;
    private LocalDate endTest;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        BigDecimal testInterestRate = new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP);
        testInterest = new YearlyInterestRate(testInterestRate);
        testBank.createAccount("123", 15, testInterest);
        BigDecimal testNumber = new BigDecimal("10000.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        testBank.executeIOperation(new Deposit(testBank.getBankProduct("123"), testNumber));

        beginTest = LocalDate.of(2012, 1, 20);
        endTest = LocalDate.of(2015, 4, 12);
    }

    @Test
    public void testSingleRateIsCalculatedCorrectly() throws Exception {
        assertEquals(new BigDecimal("600.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.capitalisation(testBank.getBankProduct("123")));
    }

    @Test
    public void testMultipleRatesAreCalculatedCorrectly() throws Exception {
        testBank.getBankProduct("123").setCreationDate(beginTest);
        assertEquals(new BigDecimal("11910.16").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.calculateFinalValue(testBank.getBankProduct("123"), endTest));
    }

}