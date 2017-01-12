package reports;

import interfaces.IBank;
import interfaces.IOperation;
import interfaces.IOperationVisitor;
import operations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 2017-01-12.
 */
public class ReportLastMonthsDeposits implements IOperationVisitor {

    private List<Deposit> lastMonthDeposits = new ArrayList<>();
    private LocalDate today = LocalDate.now();
    private LocalDate monthAgo = today.minusMonths(1);

    public ReportLastMonthsDeposits(IBank bank) {
        for (List<IOperation> productOps : bank.getBankOperations().values()) {
            for (IOperation op : productOps) {
                op.accept(this);
            }
        }
    }

    public List<Deposit> getReportResult() { return lastMonthDeposits; }

    @Override
    public void visit(CreateCredit element) {

    }

    @Override
    public void visit(CreateTermDeposit element) {

    }

    @Override
    public void visit(CreateDebit element) {

    }

    @Override
    public void visit(Deposit element) {
        if (element.getExecutionDate().isAfter(monthAgo)) {
            lastMonthDeposits.add(element);
        }
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
