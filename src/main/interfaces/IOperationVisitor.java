package interfaces;

import operations.*;

/**
 * Created by Przemek on 2017-01-12.
 */
public interface IOperationVisitor {
    void visit(CreateCredit element);
    void visit(CreateTermDeposit element);
    void visit(CreateDebit element);
    void visit(Deposit element);
    void visit(EndTermDeposit element);
    void visit(InterestCapitalisation element);
    void visit(InterestMechanismChange element);
    void visit(RepayCredit element);
    void visit(Transfer element);
    void visit(Withdraw element);
}
