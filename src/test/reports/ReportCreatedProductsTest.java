package reports;

import bank.Bank;
import interfaces.IBank;
import interfaces.IDebitable;
import interfaces.IOperationVisitor;
import interfaces.IProduct;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Przemek on 2017-01-13.
 */
public class ReportCreatedProductsTest {
    private IBank testBank;
    private ReportCreatedProducts testReport;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank();
        testBank.createAccount("123", 34);
        testBank.createCredit((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 5, 12), "CRED01");
        testBank.createTermDeposit((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("200.00").setScale(2, BigDecimal.ROUND_HALF_UP), LocalDate.of(2018, 2, 12), "DEPO02");
        testBank.createDebitAccount((IDebitable) testBank.getBankProduct("123"),
                new BigDecimal("300.00").setScale(2, BigDecimal.ROUND_HALF_UP));
        testReport = new ReportCreatedProducts(testBank);
    }

    @Test
    public void testReportReturnsCorrectNumberOfCreatedProducts() throws Exception {
        assertEquals(3, testReport.getReportResult());
    }

}