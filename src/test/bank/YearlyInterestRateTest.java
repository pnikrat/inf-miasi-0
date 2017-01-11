package bank;

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
    private Account tester;
    private BigDecimal testNumber;
    private BigDecimal testInterestRate;
    private YearlyInterestRate testInterest;
    private LocalDate beginTest;
    private LocalDate endTest;

    @Before
    public void setUp() throws Exception {
        testInterestRate = new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP);
        testInterest = new YearlyInterestRate(testInterestRate);
        tester = new Account("123", 15, testInterest);
        testNumber = new BigDecimal("10000.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        Deposit testDepo = new Deposit(tester, testNumber);

        beginTest = LocalDate.of(2012, 1, 20);
        endTest = LocalDate.of(2015, 4, 12);
    }

    @Test
    public void testSingleRateIsCalculatedCorrectly() throws Exception {
        assertEquals(new BigDecimal("600.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.capitalisation(tester));
    }

    @Test
    public void testMultipleRatesAreCalculatedCorrectly() throws Exception {
        tester.setCreationDate(beginTest);
        assertEquals(new BigDecimal("11910.16").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.calculateFinalValue(tester, endTest));
    }

}