package reports;

import bank.Account;
import bank.Credit;
import bank.DebitAccount;
import bank.TermDeposit;
import interfaces.IBank;
import interfaces.IProduct;
import interfaces.IProductVisitor;

import java.math.BigDecimal;

/**
 * Created by Przemek on 2017-01-12.
 */
public class ReportSumOfBankAssets implements IProductVisitor {

    private BigDecimal sumOfAssets = BigDecimal.ZERO;

    public ReportSumOfBankAssets(IBank bank) {
        for (IProduct prod : bank.getBankProducts().values()) {
            prod.accept(this);
        }
    }

    public BigDecimal getReportResults() { return sumOfAssets; }

    @Override
    public void visit(Account element) {
        sumOfAssets = sumOfAssets.add(element.getBalance());
    }

    @Override
    public void visit(Credit element) {
        sumOfAssets = sumOfAssets.add(element.getAmountToPayback());
    }

    @Override
    public void visit(DebitAccount element) {
        sumOfAssets = sumOfAssets.add(element.getBalanceWithDebit());
    }

    @Override
    public void visit(TermDeposit element) {
        sumOfAssets = sumOfAssets.add(element.getBalance());
    }
}
