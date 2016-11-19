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
        baseAccountForTester.productDeposit(startingMoneyForBaseAccount);

        testedBorrowedAmount = new BigDecimal("2150.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testedInterestRate = new BigDecimal("3.5").setScale(1, BigDecimal.ROUND_HALF_UP);
        tester = new Credit(baseAccountForTester, testedBorrowedAmount, "CRED:001", testedInterestRate);
    }

    @Test
    public void testAmountToPaybackIsSetCorrectly() throws Exception {
        assertEquals(tester.getAmountToPayback(), testedBorrowedAmount.add(testedInterestRate));
    }

    @Test
    public void testAccountReceivedCreditMoney() throws Exception {
        assertEquals(baseAccountForTester.getBalance(), startingMoneyForBaseAccount.add(testedBorrowedAmount));
    }

    @Test
    public void testCreditIsPaidBackWithExactAmountOfMoney() throws Exception {
        baseAccountForTester.initiateLocalTransfer(tester, testedBorrowedAmount.add(testedInterestRate));
        assertFalse(tester.getIsCreditActive());
    }

    @Test
    public void testCreditIsPaidBackWithNotEnoughMoney() throws Exception {
        baseAccountForTester.initiateLocalTransfer(tester, new BigDecimal("1000.00").setScale(2, BigDecimal.ROUND_HALF_UP));
        assertTrue(tester.getIsCreditActive());
    }

    @Test
    public void testCreditIsPaidBackWithTooMuchMoney() throws Exception {
        baseAccountForTester.initiateLocalTransfer(tester, new BigDecimal("3000.00").setScale(2, BigDecimal.ROUND_HALF_UP));
        assertFalse(tester.getIsCreditActive());
        assertEquals(baseAccountForTester.getBalance(), new BigDecimal("4996.5").setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testCreditRepaymentIsAddedToOperationHistory() throws Exception {
        baseAccountForTester.initiateLocalTransfer(tester, testedBorrowedAmount.add(testedInterestRate));
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getDescription().equals("Splata kredytu"))
                    .findFirst().isPresent());
    }

    @Test
    public void testCreditCreationIsAddedToOperationHistory() throws Exception {
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getDescription().equals("Wziecie kredytu"))
                .findFirst().isPresent());
    }
}