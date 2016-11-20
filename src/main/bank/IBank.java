package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by student on 05.11.2016.
 */
public interface IBank {
    void createAccount(String accountNumber, Integer ownerId);
    boolean createTermDeposit(Account associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                           String termDepositNumber);
    void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                      String creditNumber, BigDecimal interestRate);
}
