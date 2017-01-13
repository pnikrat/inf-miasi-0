package bank;

import interfaces.IBank;
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
    private IBank testBank;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedTermDepositAmount;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        MonthlyInterestRate testRate = new MonthlyInterestRate(new BigDecimal("0.08").setScale(2, BigDecimal.ROUND_HALF_UP));
        YearlyInterestRate testRate2 = new YearlyInterestRate(new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP));

        testBank.createAccount("1234", 91, testRate);
        startingMoneyForBaseAccount = new BigDecimal("5437.86").setScale(2, BigDecimal.ROUND_HALF_UP);
        testBank.executeIOperation(new Deposit(testBank.getBankProduct("1234"), startingMoneyForBaseAccount));

        testedTermDepositAmount = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testBank.createTermDeposit(testBank.getBankProduct("1234"),
                testedTermDepositAmount, LocalDate.of(2020, 7, 23), "LOCO:002", testRate2);
    }

    @Test
    public void testEndTermDepositBeforePeriod() throws Exception {
        testBank.executeIOperation(new EndTermDeposit((TermDeposit) testBank.getBankProduct("LOCO:002")));
        TermDeposit tester = (TermDeposit) testBank.getBankProduct("LOCO:002");
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(startingMoneyForBaseAccount, testBank.getBankProduct("1234").getBalance());
    }

    @Test
    public void testEndTermDepositAfterPeriodWithYearlyCapitalisation() throws Exception {
        testBank.getBankProduct("LOCO:002").setCreationDate(LocalDate.of(2010, 7, 23));
        TermDeposit tester = (TermDeposit) testBank.getBankProduct("LOCO:002");
        tester.setEndDate(LocalDate.of(2014, 8, 19));
        testBank.executeIOperation(new EndTermDeposit(tester));
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(5962.81, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testEndTermDepositAfterPeriodWithMonthlyCapitalisation() throws Exception {
        //TODO Change Credit and TermDeposit to calculate amount to payback at repayment/end
    }

    @Test
    public void testCreateTermDepositOperationIsAddedToBaseAccountHistory() throws Exception {
        assertTrue(testBank.getBankProduct("1234").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(4)).findFirst().isPresent());
    }

    @Test
    public void testEndTermDepositOperationIsAddedToHistory() throws Exception {
        testBank.executeIOperation(new EndTermDeposit((TermDeposit)testBank.getBankProduct("LOCO:002")));
        assertTrue(testBank.getBankProduct("LOCO:002").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(5)).findFirst().isPresent());
    }

}