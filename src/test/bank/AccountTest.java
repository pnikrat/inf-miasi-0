package bank;

import operations.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2016-11-13.
 */
public class AccountTest {

    private Account tester;
    private Account tester2;
    private BigDecimal testNumber;
    private BigDecimal testNumber2;
    private Deposit testDepo1;
    private Deposit testDepo2;
    private MonthlyInterestRate testRate;
    private YearlyInterestRate testRate2;
    private YearlyInterestRate testRate3;

    @Before
    public void setUp() throws  Exception {
        testRate = new MonthlyInterestRate(new BigDecimal("0.02").setScale(2, BigDecimal.ROUND_HALF_UP));
        testRate2 = new YearlyInterestRate(new BigDecimal("0.03").setScale(2, BigDecimal.ROUND_HALF_UP));
        testRate3 = new YearlyInterestRate(new BigDecimal("0.04").setScale(2, BigDecimal.ROUND_HALF_UP));
        tester = new Account("5678", 2, testRate);
        tester2 = new Account("1234", 3, testRate2);
        testNumber = new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber2 = new BigDecimal("864.56").setScale(2, BigDecimal.ROUND_HALF_UP);

        testDepo1 = new Deposit(tester, testNumber);
        testDepo2 = new Deposit(tester2, testNumber);
    }

    @Test
    public void testProductDeposit() throws Exception {
        Deposit tempDepo = new Deposit(tester, testNumber2);
        assertEquals(2364.56, tester.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testProductWithdrawal() throws Exception {
        Withdraw tempWithdraw = new Withdraw(tester, testNumber2);
        assertEquals(635.44, tester.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testLocalTransfers() throws Exception {
        Transfer tempTransfer = new Transfer(tester, tester2, testNumber2);
        assertEquals(635.44, tester.getBalance().doubleValue(), 0.001);
        assertEquals(2364.56, tester2.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testMonthlyInterestCapitalisation() throws Exception {
        InterestCapitalisation tempCapitalisation = new InterestCapitalisation(tester);
        assertEquals(1502.50, tester.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testYearlyInterestCapitalisation() throws Exception {
        InterestCapitalisation tempCapitalisation = new InterestCapitalisation(tester2);
        assertEquals(1545.00, tester2.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testInterestRateMechanismChange() throws Exception {
        InterestMechanismChange tempChange = new InterestMechanismChange(tester, testRate3);
        assertEquals(testRate2.getNumberOfCapitalisations(),
                tester.getInterestRateMechanism().getNumberOfCapitalisations());
    }

    @Test
    public void testOperationHistoryContainsDepositRecord() throws Exception {
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(1))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsWithdrawRecord() throws Exception {
        Withdraw tempWithdraw = new Withdraw(tester, testNumber2);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(2))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsTransferRecords() throws Exception {
        Transfer tempTransfer = new Transfer(tester, tester2, testNumber2);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(3))
                .findFirst().isPresent());
        assertTrue(tester2.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(3))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsInterestCapitalisationRecord() throws Exception {
        InterestCapitalisation tempCapitalisation = new InterestCapitalisation(tester);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(8))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsInterestChangeRecord() throws Exception {
        InterestMechanismChange tempChange = new InterestMechanismChange(tester, testRate3);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(9))
            .findFirst().isPresent());
    }
}