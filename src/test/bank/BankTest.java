package bank;

import interfaces.IBank;
import operations.Deposit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2016-11-13.
 */
public class BankTest {
    private IBank tester;

    @Before
    public void setUp() throws Exception {
        tester = new Bank();
        MonthlyInterestRate testRate = new MonthlyInterestRate(new BigDecimal("0.02").setScale(2, BigDecimal.ROUND_HALF_UP));
        BigDecimal testDebit1 = new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP);

        tester.createAccount("1234", 1, testRate);

        tester.createCredit(tester.getBankProduct("1234"),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                LocalDate.of(2020, 7, 23), "CRED001", testRate);
        tester.createTermDeposit(tester.getBankProduct("1234"),
                new BigDecimal("1000.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                LocalDate.of(2019, 7, 23), "LOC001", testRate);
        tester.createDebitAccount(tester.getBankProduct("1234"), testDebit1);


        BigDecimal tempDepoAmount = new BigDecimal("5000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester.executeIOperation(new Deposit(tester.getBankProduct("1234"), tempDepoAmount));
    }

    @Test
    public void testBankProductsContainsNewAccount() throws Exception {
        assertTrue(tester.getBankProducts().containsKey("1234"));
    }

    @Test
    public void testBankProductsContainsNewTermDeposit() throws Exception {
        assertTrue(tester.getBankProducts().containsKey("LOC001"));
    }

    @Test
    public void testBankProductsContainsNewCredit() throws Exception {
        assertTrue(tester.getBankProducts().containsKey("CRED001"));
    }

    @Test
    public void testBankProductsContainsNewDebitAccount() throws Exception {
        assertFalse(tester.getBankProducts().get("1234") instanceof Account);
        assertTrue(tester.getBankProducts().get("1234") instanceof DebitAccount);
    }

    @Test
    public void testBankOperationsContainsDepositOperationOnAccount() throws Exception {
        assertTrue(tester.getBankOperations().get(tester.getBankProduct("1234")).stream()
                .filter(x -> x.getOperationTypeId().equals(1)).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsCreateTermDepositOperationOnAccount() throws Exception {
        assertTrue(tester.getBankOperations().get(tester.getBankProduct("1234")).stream()
                .filter(x -> x.getOperationTypeId().equals(4)).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsCreateCreditOperationOnAccount() throws Exception {
        assertTrue(tester.getBankOperations().get(tester.getBankProduct("1234")).stream()
                .filter(x -> x.getOperationTypeId().equals(6)).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsCreateDebitOperationOnAccount() throws Exception {
        assertTrue(tester.getBankOperations().get(tester.getBankProduct("1234")).stream()
                .filter(x -> x.getOperationTypeId().equals(10)).findFirst().isPresent());
    }
}