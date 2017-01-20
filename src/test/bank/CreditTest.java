package bank;

import interfaces.IBank;
import interfaces.IDebitable;
import operations.Deposit;
import operations.RepayCredit;
import operations.Withdraw;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by student on 19.11.2016.
 */
public class CreditTest {

    private IBank testBank;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedBorrowedAmount;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        MonthlyInterestRate testRate = new MonthlyInterestRate(new BigDecimal("0.02").setScale(2, BigDecimal.ROUND_HALF_UP));
        YearlyInterestRate testRate2 = new YearlyInterestRate(new BigDecimal("0.06").setScale(2, BigDecimal.ROUND_HALF_UP));

        testBank.createAccount("1234", 123, testRate);
        startingMoneyForBaseAccount = new BigDecimal("5000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testBank.executeIOperation(new Deposit(testBank.getBankDebitable("1234"), startingMoneyForBaseAccount));

        testedBorrowedAmount = new BigDecimal("2150.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testBank.createCredit(testBank.getBankDebitable("1234"), testedBorrowedAmount,
                LocalDate.of(2020, 7, 23), "CRED:001", testRate2);
    }

    @Test
    public void testAmountToPaybackIsSetCorrectly() throws Exception {
        Credit tester = (Credit)testBank.getBankCreditable("CRED:001");
        testBank.executeIOperation(new RepayCredit(tester));
        assertEquals(2560.68, tester.getAmountToPayback().doubleValue(), 0.001);
    }

    @Test
    public void testAccountReceivedCreditMoney() throws Exception {
        assertEquals(startingMoneyForBaseAccount.add(testedBorrowedAmount),
                testBank.getBankProduct("1234").getBalance());
    }

    @Test
    public void testRepayCreditWithNotEnoughMoneyOnAccount() throws Exception {
        //withdraw some money so that account has not enough money for repayment
        testBank.executeIOperation(new Withdraw(testBank.getBankDebitable("1234"), startingMoneyForBaseAccount));
        assertFalse(testBank.executeIOperation(new RepayCredit((Credit)testBank.getBankCreditable("CRED:001"))));
    }

    @Test
    public void testRepayCreditWithEnoughMoneyOnAccountYearlyInterest() throws Exception {
        assertTrue(testBank.executeIOperation(new RepayCredit((Credit)testBank.getBankCreditable("CRED:001"))));
        assertEquals(4589.32, testBank.getBankProduct("1234").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testCreditRepaymentIsAddedToOperationHistory() throws Exception {
        testBank.executeIOperation(new RepayCredit((Credit)testBank.getBankCreditable("CRED:001")));
        assertTrue(testBank.getBankProduct("CRED:001").getOperationHistory().stream().
                anyMatch(x -> x.getOperationTypeId().equals(7)));
    }

    @Test
    public void testCreditCreationIsAddedToOperationHistory() throws Exception {
        assertTrue(testBank.getBankProduct("1234").getOperationHistory().stream()
                .anyMatch(x -> x.getOperationTypeId().equals(6)));
    }

}