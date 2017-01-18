package bank;

import com.sun.xml.internal.bind.v2.model.core.ID;
import interfaces.IBank;
import interfaces.IDebitable;
import operations.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2016-11-13.
 */
public class AccountTest {
    private IBank testBank;
    private IDebitable acc1;
    private BigDecimal testNumber;
    private BigDecimal testNumber2;
    private MonthlyInterestRate testRate;
    private YearlyInterestRate testRate2;
    private YearlyInterestRate testRate3;

    @Before
    public void setUp() throws  Exception {
        testBank = new Bank();
        testRate = new MonthlyInterestRate(new BigDecimal("0.02").setScale(2, BigDecimal.ROUND_HALF_UP));
        testRate2 = new YearlyInterestRate(new BigDecimal("0.03").setScale(2, BigDecimal.ROUND_HALF_UP));
        testRate3 = new YearlyInterestRate(new BigDecimal("0.04").setScale(2, BigDecimal.ROUND_HALF_UP));

        testBank.createAccount("5678", 2, testRate);
        acc1 = (IDebitable) testBank.getBankProduct("5678");
        testBank.createAccount("1234", 3, testRate2);

        testNumber = new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber2 = new BigDecimal("864.56").setScale(2, BigDecimal.ROUND_HALF_UP);

        testBank.executeIOperation(new Deposit((IDebitable) testBank.getBankProduct("5678"), testNumber));
        testBank.executeIOperation(new Deposit((IDebitable) testBank.getBankProduct("1234"), testNumber));
    }

    @Test
    public void testProductDeposit() throws Exception {
        testBank.executeIOperation(new Deposit(acc1, testNumber2));
        assertEquals(2364.56, acc1.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testProductWithdrawal() throws Exception {
        testBank.executeIOperation(new Withdraw(acc1, testNumber2));
        assertEquals(635.44, testBank.getBankProduct("5678").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testLocalTransfers() throws Exception {
        testBank.executeIOperation(new Transfer(testBank.getBankProduct("5678"), testBank.getBankProduct("1234"),
                testNumber2));
        assertEquals(635.44, testBank.getBankProduct("5678").getBalance().doubleValue(), 0.001);
        assertEquals(2364.56, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testMonthlyInterestCapitalisation() throws Exception {
        testBank.executeIOperation(new InterestCapitalisation(testBank.getBankProduct("5678")));
        assertEquals(1502.50, testBank.getBankProduct("5678").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testYearlyInterestCapitalisation() throws Exception {
        testBank.executeIOperation(new InterestCapitalisation(testBank.getBankProduct("1234")));
        assertEquals(1545.00, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testInterestRateMechanismChange() throws Exception {
        testBank.executeIOperation(new InterestMechanismChange(testBank.getBankProduct("5678"), testRate3));
        assertEquals(testRate2.getNumberOfCapitalisations(),
                testBank.getBankProduct("5678").getInterestRateMechanism().getNumberOfCapitalisations());
    }

    @Test
    public void testOperationHistoryContainsDepositRecord() throws Exception {
        assertTrue(testBank.getBankProduct("5678").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(1))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsWithdrawRecord() throws Exception {
        testBank.executeIOperation(new Withdraw(acc1, testNumber2));
        assertTrue(testBank.getBankProduct("5678").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(2))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsTransferRecords() throws Exception {
        testBank.executeIOperation(new Transfer(testBank.getBankProduct("5678"), testBank.getBankProduct("1234"),
                testNumber2));
        assertTrue(testBank.getBankProduct("5678").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(3))
                .findFirst().isPresent());
        assertTrue(testBank.getBankProduct("1234").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(3))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsInterestCapitalisationRecord() throws Exception {
        testBank.executeIOperation(new InterestCapitalisation(testBank.getBankProduct("5678")));
        assertTrue(testBank.getBankProduct("5678").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(8))
                .findFirst().isPresent());
    }

    @Test
    public void testOperationHistoryContainsInterestChangeRecord() throws Exception {
        testBank.executeIOperation(new InterestMechanismChange(testBank.getBankProduct("5678"), testRate3));
        assertTrue(testBank.getBankProduct("5678").getOperationHistory().stream()
                .filter(x -> x.getOperationTypeId().equals(9))
            .findFirst().isPresent());
    }
}