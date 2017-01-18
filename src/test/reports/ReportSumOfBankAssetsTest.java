package reports;

import bank.Bank;
import interfaces.IBank;
import interfaces.IDebitable;
import operations.Deposit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2017-01-13.
 */
public class ReportSumOfBankAssetsTest {
    private IBank testBank;
    private ReportSumOfBankAssets testReport;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("123", 34);
        testBank.executeIOperation(new Deposit((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("2345.45").setScale(2, BigDecimal.ROUND_HALF_UP)));
        testBank.createCredit(testBank.getBankProduct("123"),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2017, 2, 20), "CRED01");
        testBank.createTermDeposit(testBank.getBankProduct("123"),
                new BigDecimal("200.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 2, 12), "DEPO02");
        testBank.createDebitAccount((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP));
        testReport = new ReportSumOfBankAssets(testBank);
    }

    @Test
    public void testReportReturnsCorrectSumOfBankAssets() throws Exception {
        //1503,75 from Credit, 200 from TermDepo, (2345.45-200+1500+300) from DebitAccount, Total 5649,20
        assertEquals(5649.2, testReport.getReportResults().doubleValue(), 0.001);
    }

}