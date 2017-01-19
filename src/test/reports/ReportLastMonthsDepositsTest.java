package reports;

import bank.Bank;
import interfaces.IBank;
import interfaces.IDebitable;
import interfaces.IOperation;
import operations.Deposit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Przemek on 2017-01-13.
 */
public class ReportLastMonthsDepositsTest  {
    private IBank testBank;
    private IDebitable acc1;
    private ReportLastMonthsDeposits testReport;
    private List<Deposit> expectedOps = new ArrayList<>();
    private Deposit testDepo;
    private Deposit testDepo2;
    private Deposit testDepo3;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("123", 34);
        acc1 = (IDebitable) testBank.getBankProduct("123");

        testDepo = new Deposit(acc1,
                new BigDecimal("2345.45").setScale(2, BigDecimal.ROUND_HALF_UP));
        testDepo.executeOperation();

        testBank.createCredit((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 5, 12), "CRED01");
        testBank.createTermDeposit((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("200.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 2, 12), "DEPO02");
        testBank.createDebitAccount(acc1,
                new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP));

        testDepo2 = new Deposit(acc1,
                new BigDecimal("999.99").setScale(2, BigDecimal.ROUND_HALF_UP));
        testDepo2.executeOperation();

        testBank.createAccount("345", 45);
        testDepo3 = new Deposit((IDebitable) testBank.getBankProduct("345"),
                new BigDecimal("888.88").setScale(2, BigDecimal.ROUND_HALF_UP));
        testDepo3.executeOperation();

        testDepo.setExecutionDate(LocalDate.of(2016, 12, 22));
        testDepo2.setExecutionDate(LocalDate.of(2016,7,23));
        //testDepo3 left with now()
        expectedOps.add(testDepo);
        expectedOps.add(testDepo3);
        testReport = new ReportLastMonthsDeposits(testBank);
    }

    @Test
    public void testReportReturnsCorrectNumberOfDeposits() throws Exception {
        //createCredit also uses Deposit operation so 3 is expected
        assertEquals(3, testReport.getReportResult().size());
    }

    @Test
    public void testReportReturnsCorrectListOfOperations() throws Exception {
        assertTrue(testReport.getReportResult().stream()
                .anyMatch(x -> x.getDescription().contains(testDepo.getDescription())));
        assertTrue(testReport.getReportResult().stream()
                .anyMatch(x -> x.getDescription().contains(testDepo3.getDescription())));
        assertFalse(testReport.getReportResult().stream()
                .anyMatch(x -> x.getDescription().contains(testDepo2.getDescription())));
    }

}