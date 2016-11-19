package bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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
        baseAccountForTester.productDeposit(startingMoneyForBaseAccount);

        testedTermDepositAmount = new BigDecimal("2000.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        tester = new TermDeposit(baseAccountForTester, testedTermDepositAmount, "LOCO:002");
    }

    @Test
    public void testEndTermDepositBeforePeriod() throws Exception {
    
    }

    public void testEndTermDepositAfterPeriod() throws Exception {

    }

    @Test
    public void testCreateTermDepositOperationIsAddedToHistory() throws Exception {
        assertTrue(tester.getOperationHistory().stream().filter(x -> x.getDescription().equals("Zalozenie lokaty")).findFirst().isPresent());
    }

}