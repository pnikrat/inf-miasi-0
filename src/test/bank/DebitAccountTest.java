package bank;

import interfaces.IBank;
import operations.Deposit;
import operations.Withdraw;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by student on 11.12.2016.
 */
public class DebitAccountTest {
    private IBank testBank;
    private BigDecimal testNumber;
    private BigDecimal testNumber2;
    private BigDecimal testNumber3;
    private BigDecimal testNumber4;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("5678", 2);
        testBank.createAccount("1234", 3);

        testNumber = new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber2 = new BigDecimal("864.56").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber3 = new BigDecimal("1600.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber4 = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal testDebit = new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        testBank.executeIOperation(new Deposit(testBank.getBankProduct("5678"), testNumber));
        testBank.executeIOperation(new Deposit(testBank.getBankProduct("1234"), testNumber));
        testBank.createDebitAccount(testBank.getBankProduct("1234"), testDebit);
    }

    @Test
    public void testWithdrawBelowDebitLimit() throws Exception {
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber3));
        assertEquals(0.0, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
        assertEquals(-100.0, testBank.getBankProduct("1234").getDebit().doubleValue(), 0.001);
        //assertEquals(200.0, testBank.BankProducts.get(1).getDebitLeft().doubleValue(), 0.001);
    }

    @Test
    public void testWithdrawWithoutReachingDebit() throws Exception {
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber2));
        assertEquals(635.44, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testWithdrawAboveDebitLimit() throws Exception {
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber4));
        assertEquals(1500.00, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
        assertEquals(0.0, testBank.getBankProduct("1234").getDebit().doubleValue(), 0.001);
    }

    @Test
    public void testDepositOnAccountWithoutDebit() throws Exception {
        testBank.executeIOperation(new Deposit(testBank.getBankProduct("1234"), testNumber2));
        assertEquals(2364.56, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testDepositOnAccountWithDebit() throws Exception {
        //withdraw something so that debit creates
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber3));
        //debit should be -100 now, check with depo 864.56
        testBank.executeIOperation(new Deposit(testBank.getBankProduct("1234"), testNumber2));
        assertEquals(764.56, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
        assertEquals(0.0, testBank.getBankProduct("1234").getDebit().doubleValue(), 0.001);
    }

    @Test
    public void testCannotCreateTermDepositFromAccountOnDebit() throws Exception {
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber3));
        //should be -100 now
        assertFalse(testBank.createTermDeposit(testBank.getBankProduct("1234"), testNumber2,
                LocalDate.of(2020, 7, 23), "LOC:002"));
    }

    @Test
    public void testCreditLowersDebitFirstThenAddsToRegularAccount() throws Exception {
        testBank.executeIOperation(new Withdraw(testBank.getBankProduct("1234"), testNumber3));
        //should be -100 now
        testBank.createCredit(testBank.getBankProduct("1234"), testNumber2,
                LocalDate.of(2020, 7, 23), "CRED:003");
        assertEquals(764.56, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    //TODO: Add more tests - boundary cases and so on
}