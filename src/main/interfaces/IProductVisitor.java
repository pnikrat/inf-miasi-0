package interfaces;

import bank.Account;
import bank.Credit;
import bank.DebitAccount;
import bank.TermDeposit;

/**
 * Created by Przemek on 2017-01-12.
 */
public interface IProductVisitor {
    void visit(Account element);
    void visit(Credit element);
    void visit(DebitAccount element);
    void visit(TermDeposit element);
}
