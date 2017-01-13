package reports;

import bank.Bank;
import interfaces.IBank;
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
    private ReportLastMonthsDeposits testReport;
    private List<IOperation> expectedOps = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("123", 34);
        Deposit testDepo = new Deposit(testBank.getBankProduct("123"),
                new BigDecimal("2345.45").setScale(2, BigDecimal.ROUND_HALF_UP));
        testBank.createCredit(testBank.getBankProduct("123"),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 5, 12), "CRED01");
        testBank.createTermDeposit(testBank.getBankProduct("123"),
                new BigDecimal("200.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 2, 12), "DEPO02");
        testBank.createDebitAccount(testBank.getBankProduct("123"),
                new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP));
        Deposit testDepo2 = new Deposit(testBank.getBankProduct("123"),
                new BigDecimal("999.99").setScale(2, BigDecimal.ROUND_HALF_UP));
        testBank.createAccount("345", 45);
        Deposit testDepo3 = new Deposit(testBank.getBankProduct("345"),
                new BigDecimal("888.88").setScale(2, BigDecimal.ROUND_HALF_UP));
        testDepo.setExecutionDate(LocalDate.of(2016, 12, 22));
        testDepo2.setExecutionDate(LocalDate.of(2016,7,23));
        //testDepo3 left with now()
        expectedOps.add(testDepo);
        expectedOps.add(testDepo3);
        testReport = new ReportLastMonthsDeposits(testBank);
    }

    @Test
    public void testReportReturnsCorrectNumberOfDeposits() throws Exception {
        assertEquals(2, testReport.getReportResult().size());
    }

    @Test
    public void testReportReturnsCorrectListOfOperations() throws Exception {
        assertEquals(expectedOps, testReport.getReportResult());
    }

}