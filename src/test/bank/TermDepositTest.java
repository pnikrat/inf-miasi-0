package bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by student on 19.11.2016.
 */
public class TermDepositTest {

    private TermDeposit tester;
    private Account baseAccountForTester;
    private BigDecimal startingMoneyForBaseAccount;
    private BigDecimal testedTermDepositAmount;
    
    @Before
    public void setUp() throws Exception {
        baseAccountForTester = new Account("4557 4562", 91);
        startingMoneyForBaseAccount = new BigDecimal("5437.86").setScale(2, BigDecimal.ROUND_HALF_UP);
        Deposit tempDepo = new Deposit(baseAccountForTester, startingMoneyForBaseAccount);

        testedTermDepositAmount = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester = new TermDeposit(baseAccountForTester, testedTermDepositAmount, LocalDate.now(), "LOCO:002");

    }

    @Test
    public void testEndTermDepositBeforePeriod() throws Exception {
        tester.setEndDate(LocalDate.of(2020, 7, 23));
        EndTermDeposit endOperation = new EndTermDeposit(tester);
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(startingMoneyForBaseAccount, baseAccountForTester.getBalance());
    }

    @Test
    public void testEndTermDepositAfterPeriod() throws Exception {
        tester.setEndDate(LocalDate.of(2010, 7, 23));
        EndTermDeposit endOperation = new EndTermDeposit(tester);
        assertFalse(tester.getIsTermDepositActive());
        assertEquals(startingMoneyForBaseAccount.add(new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP)),
                    baseAccountForTester.getBalance());
    }

    @Test
    public void testCreateTermDepositOperationIsAddedToBaseAccountHistory() throws Exception {
        assertTrue(baseAccountForTester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(4))
                .findFirst().isPresent());
    }

    @Test
    public void testEndTermDepositOperationIsAddedToHistory() throws Exception {
        EndTermDeposit tempEnd = new EndTermDeposit(tester);
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getOperationTypeId().equals(5))
                .findFirst().isPresent());
    }

}