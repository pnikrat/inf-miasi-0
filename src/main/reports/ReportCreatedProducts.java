package reports;

import interfaces.IBank;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import operations.*;

import java.util.List;

/**
 * Created by Przemek on 2017-01-12.
 */
public class ReportCreatedProducts implements IOperationVisitor {

    private int numberOfCreatedProducts;

    public ReportCreatedProducts(IBank bank) {
        for (List<IOperation> productOps : bank.getBankOperations().values()) {
            for (IOperation op : productOps) {
                op.accept(this);
            }
        }
    }

    public int getReportResult() { return numberOfCreatedProducts; }

    @Override
    public void visit(CreateCredit element) {
        numberOfCreatedProducts++;
    }

    @Override
    public void visit(CreateTermDeposit element) {
        numberOfCreatedProducts++;
    }

    @Override
    public void visit(CreateDebit element) {
        numberOfCreatedProducts++;
    }

    @Override
    public void visit(Deposit element) {

    }

    @Override
    public void visit(EndTermDeposit element) {

    }

    @Override
    public void visit(InterestCapitalisation element) {

    }

    @Override
    public void visit(InterestMechanismChange element) {

    }

    @Override
    public void visit(RepayCredit element) {

    }

    @Override
    public void visit(Transfer element) {

    }

    @Override
    public void visit(Withdraw element) {

    }
}
