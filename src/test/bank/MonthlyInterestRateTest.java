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
public class MonthlyInterestRateTest {
    private Account tester;
    private BigDecimal testNumber;
    private BigDecimal testInterestRate;
    private MonthlyInterestRate testInterest;
    private LocalDate beginTest;
    private LocalDate endTest;

    @Before
    public void setUp() throws Exception {
        testInterestRate = new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP);
        testInterest = new MonthlyInterestRate(testInterestRate);
        tester = new Account("123", 15, testInterest);
        testNumber = new BigDecimal("10000.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        Deposit testDepo = new Deposit(tester, testNumber);

        beginTest = LocalDate.of(2012, 1, 20);
        endTest = LocalDate.of(2015, 1, 20);
    }

    @Test
    public void capitalisation() throws Exception {
        assertEquals(new BigDecimal("50.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.capitalisation(tester));
    }

    @Test
    public void calculateFinalValue() throws Exception {
        tester.setCreationDate(beginTest);
        assertEquals(new BigDecimal("11966.81").setScale(2, BigDecimal.ROUND_HALF_UP),
                testInterest.calculateFinalValue(tester, endTest));
    }

}