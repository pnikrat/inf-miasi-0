package bank;

import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by student on 11.12.2016.
 */
public class DebitAccountTest {

    private Bank testBank;
    private Account tester;
    private Account tester2;
    private BigDecimal testNumber;
    private BigDecimal testNumber2;
    private BigDecimal testNumber3;
    private BigDecimal testNumber4;
    private BigDecimal testDebit;
    private Deposit testDepo1;
    private Deposit testDepo2;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("5678", 2);
        testBank.createAccount("1234", 3);
        //tester = new Account("5678", 2);
        //tester2 = new Account("1234", 3);
        testNumber = new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber2 = new BigDecimal("864.56").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber3 = new BigDecimal("1600.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber4 = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        testDebit = new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        testDepo1 = new Deposit(testBank.BankProducts.get(0), testNumber);
        testDepo2 = new Deposit(testBank.BankProducts.get(1), testNumber);
        testBank.createDebitAccount(testBank.BankProducts.get(1), testDebit);
    }

    @Test
    public void testWithdrawBelowDebitLimit() throws Exception {
        Withdraw tempOp = new Withdraw(testBank.BankProducts.get(1), testNumber3);
        assertEquals(0.0, testBank.BankProducts.get(1).getBalance().doubleValue(), 0.001);
        assertEquals(-100.0, testBank.BankProducts.get(1).getDebit().doubleValue(), 0.001);
        //assertEquals(200.0, testBank.BankProducts.get(1).getDebitLeft().doubleValue(), 0.001);
    }

    @Test
    public void testWithdrawWithoutReachingDebit() throws Exception {
        Withdraw tempOp = new Withdraw(testBank.BankProducts.get(1), testNumber2);
        assertEquals(635.44, testBank.BankProducts.get(1).getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testWithdrawAboveDebitLimit() throws Exception {
        Withdraw tempOp = new Withdraw(testBank.BankProducts.get(1), testNumber4);
        assertEquals(1500.00, testBank.BankProducts.get(1).getBalance().doubleValue(), 0.001);
        assertEquals(0.0, testBank.BankProducts.get(1).getDebit().doubleValue(), 0.001);
    }

    @Test
    public void testDepositOnAccountWithoutDebit() throws Exception {
        Deposit tempOp = new Deposit(testBank.BankProducts.get(1), testNumber2);
        assertEquals(2364.56, testBank.BankProducts.get(1).getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testDepositOnAccountWithDebit() throws Exception {
        //withdraw something so that debit creates
        Withdraw tempWithdraw = new Withdraw(testBank.BankProducts.get(1), testNumber3);
        //debit should be -100 now, check with depo 864.56
        Deposit tempOp = new Deposit(testBank.BankProducts.get(1), testNumber2);
        assertEquals(764.56, testBank.BankProducts.get(1).getBalance().doubleValue(), 0.001);
        assertEquals(0.0, testBank.BankProducts.get(1).getDebit().doubleValue(), 0.001);
    }

    //TODO: Add more tests - boundary cases and so on
}