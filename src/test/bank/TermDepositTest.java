package bank;

import operations.Deposit;
import operations.EndTermDeposit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by student on 19.11.2016.
 */
public class TermDepositTest {

    private TermDeposit tester;
    private Account baseAccountForTester;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedTermDepositAmount;

    @Before
    public void setUp() throws Exception {
        MonthlyInterestRate testRate = new MonthlyInterestRate(new BigDecimal("0.08").setScale(2, BigDecimal.ROUND_HALF_UP));
        YearlyInterestRate testRate2 = new YearlyInterestRate(new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP));
        baseAccountForTester = new Account("4557 4562", 91, testRate);
        startingMoneyForBaseAccount = new BigDecimal("5437.86").setScale(2, BigDecimal.ROUND_HALF_UP);
        Deposit tempDepo = new Deposit(baseAccountForTester, startingMoneyForBaseAccount);

        testedTermDepositAmount = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester = new TermDeposit(baseAccountForTester, testedTermDepositAmount,
                LocalDate.of(2020, 7, 23), "LOCO:002", testRate2);

    }

    @Test
    public void testEndTermDepositBeforePeriod() throws Exception {
        EndTermDeposit endOperation = new EndTermDeposit(tester);
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(startingMoneyForBaseAccount, baseAccountForTester.getBalance());
    }

    @Test
    public void testEndTermDepositAfterPeriodWithYearlyCapitalisation() throws Exception {
        tester.setCreationDate(LocalDate.of(2010, 7, 23));
        tester.setEndDate(LocalDate.of(2014, 8, 19));
        EndTermDeposit endOperation = new EndTermDeposit(tester);
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(5962.81, baseAccountForTester.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testEndTermDepositAfterPeriodWithMonthlyCapitalisation() throws Exception {
        //TODO Change Credit and TermDeposit to calculate amount to payback at repayment/end
    }

    @Test
    public void testCreateTermDepositOperationIsAddedToBaseAccountHistory() throws Exception {
        assertTrue(baseAccountForTester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(4))
                .findFirst().isPresent());
    }

    @Test
    public void testEndTermDepositOperationIsAddedToHistory() throws Exception {
        EndTermDeposit tempEnd = new EndTermDeposit(tester);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(5))
                .findFirst().isPresent());
    }

}