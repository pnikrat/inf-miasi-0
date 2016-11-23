package bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2016-11-13.
 */
public class BankTest {

    private Bank tester;
    private Account accountForOperationTesting;

    @Before
    public void setUp() throws Exception {
        tester = new Bank();
        tester.createAccount("1234", 1);
        accountForOperationTesting = (Account) tester.BankProducts.get(0);

        tester.createCredit((Account) tester.BankProducts.get(0),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP), "CRED001",
                new BigDecimal("3.5").setScale(1, BigDecimal.ROUND_HALF_UP));
        tester.createTermDeposit((Account) tester.BankProducts.get(0),
                                new BigDecimal("1000.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                                LocalDate.now(), "LOC001");

        BigDecimal tempDepoAmount = new BigDecimal("5000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        Deposit tempDepo = new Deposit(accountForOperationTesting, tempDepoAmount);
    }

    @Test
    public void testBankProductsContainsNewAccount() throws Exception {
        assertTrue(tester.BankProducts.stream().filter(x -> x.getProductNumber().equals("1234")).findFirst().isPresent());
    }

    @Test
    public void testBankProductsContainsNewTermDeposit() throws Exception {
        assertTrue(tester.BankProducts.stream().filter(x -> x.getProductNumber().equals("LOC001")).findFirst().isPresent());
    }

    @Test
    public void testBankProductsContainsNewCredit() throws Exception {
        assertTrue(tester.BankProducts.stream().filter(x -> x.getProductNumber().equals("CRED001")).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsDepositOperationOnAccount() throws Exception {
        assertTrue(tester.BankOperations.get(accountForOperationTesting).stream().filter(x -> x.getOperationTypeId()
            .equals(1)).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsCreateTermDepositOperationOnAccount() throws Exception {
        assertTrue(tester.BankOperations.get(accountForOperationTesting).stream().filter(x -> x.getOperationTypeId()
            .equals(4)).findFirst().isPresent());
    }

    @Test
    public void testBankOperationsContainsCreateCreditOperationOnAccount() throws Exception {
        assertTrue(tester.BankOperations.get(accountForOperationTesting).stream().filter(x -> x.getOperationTypeId()
            .equals(6)).findFirst().isPresent());
    }
}