package bank;

import java.math.BigDecimal;

/**
 * Created by student on 05.11.2016.
 */
public interface IBank {
    void createAccount(String accountNumber, Integer ownerId);
    void createTermDeposit(Account associatedAccount, BigDecimal originalAmount, String termDepositNumber);
    void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                      String creditNumber, BigDecimal interestRate);
}
