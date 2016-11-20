package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Bank implements IBank {
    // TODO: Change these to private once reports are implemented
    public List<IProduct> BankProducts = new ArrayList<IProduct>();
    public Map<IProduct, List<IOperation>> BankOperations = new HashMap<>();

    @Override
    public void createAccount(String accountNumber, Integer ownerId) {
        Account tempName = new Account(accountNumber, ownerId);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public boolean createTermDeposit(Account associatedAccount, BigDecimal originalAmount,
                                  LocalDate endDate, String termDepositNumber) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return false;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, endDate, termDepositNumber);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
        return true;
    }

    @Override
    public void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                             String creditNumber, BigDecimal interestRate) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, creditNumber, interestRate);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }
}
