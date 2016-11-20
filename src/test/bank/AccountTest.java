package bank;

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

    @Before
    public void setUp() throws  Exception {
        tester = new Account("5678", 2);
        tester2 = new Account("1234", 3);
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

}