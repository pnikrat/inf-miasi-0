package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by student on 05.11.2016.
 */
public interface IBank {
    void createAccount(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism);
    void createAccount(String accountNumber, Integer ownerId);

    boolean createTermDeposit(IProduct associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                           String termDepositNumber, IInterestRate interestRateMechanism);
    boolean createTermDeposit(IProduct associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                              String termDepositNumber);

    void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism);
    void createCredit(IProduct associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber);

    void createDebitAccount(IProduct decoratedAccount, BigDecimal maximumDebit);
}
