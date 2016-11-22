package bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by student on 19.11.2016.
 */
public class CreditTest {

    private Credit tester;
    private Account baseAccountForTester;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedBorrowedAmount;
    private BigDecimal testedInterestRate;

    @Before
    public void setUp() throws Exception {
        baseAccountForTester = new Account("1234", 123);
        startingMoneyForBaseAccount = new BigDecimal("5000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        Deposit testDepo = new Deposit(baseAccountForTester, startingMoneyForBaseAccount);

        testedBorrowedAmount = new BigDecimal("2150.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testedInterestRate = new BigDecimal("3.50").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester = new Credit(baseAccountForTester, testedBorrowedAmount, "CRED:001", testedInterestRate);
    }

    @Test
    public void testAmountToPaybackIsSetCorrectly() throws Exception {
        assertEquals(testedBorrowedAmount.add(testedInterestRate), tester.getAmountToPayback());
    }

    @Test
    public void testAccountReceivedCreditMoney() throws Exception {
        assertEquals(startingMoneyForBaseAccount.add(testedBorrowedAmount), baseAccountForTester.getBalance());
    }

    @Test
    public void testRepayCreditWithNotEnoughMoneyOnAccount() throws Exception {
        //withdraw some money so that account has not enough money for repayment
        Withdraw testWithdraw = new Withdraw(baseAccountForTester, startingMoneyForBaseAccount);
        assertFalse(tester.repayCredit());
    }

    @Test
    public void testRepayCreditWithEnoughMoneyOnAccount() throws Exception {
        assertTrue(tester.repayCredit());
        assertEquals( new BigDecimal("4996.50").setScale(2, BigDecimal.ROUND_HALF_UP), baseAccountForTester.getBalance());
    }

    @Test
    public void testCreditRepaymentIsAddedToOperationHistory() throws Exception {
        tester.repayCredit();
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(7))
                    .findFirst().isPresent());
    }

    @Test
    public void testCreditCreationIsAddedToOperationHistory() throws Exception {
        assertTrue(baseAccountForTester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(6))
                .findFirst().isPresent());
    }

}