package bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by student on 19.11.2016.
 */
public class CreditTest {

    private Credit tester;
    private Account baseAccountForTester;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedBorrowedAmount;

    @Before
    public void setUp() throws Exception {
        MonthlyInterestRate testRate = new MonthlyInterestRate(new BigDecimal("0.02").setScale(2, BigDecimal.ROUND_HALF_UP));
        YearlyInterestRate testRate2 = new YearlyInterestRate(new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP));
        baseAccountForTester = new Account("1234", 123, testRate);
        startingMoneyForBaseAccount = new BigDecimal("5000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        Deposit testDepo = new Deposit(baseAccountForTester, startingMoneyForBaseAccount);

        testedBorrowedAmount = new BigDecimal("2150.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester = new Credit(baseAccountForTester, testedBorrowedAmount, LocalDate.of(2020, 7, 23), "CRED:001", testRate2);
    }

    @Test
    public void testAmountToPaybackIsSetCorrectly() throws Exception {
        tester.repayCredit();
        assertEquals(2560.68, tester.getAmountToPayback().doubleValue(), 0.001);
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
    public void testRepayCreditWithEnoughMoneyOnAccountYearlyInterest() throws Exception {
        assertTrue(tester.repayCredit());
        assertEquals(4589.32, baseAccountForTester.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testRepayCreditWithEnoughMoneyOnAccountMonthlyInterest() throws Exception {
        //TODO
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