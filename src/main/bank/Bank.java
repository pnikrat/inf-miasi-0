package bank;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Bank implements IBank {
    // TODO: Change these to private once reports are implemented
    public List<IProduct> BankProducts = new ArrayList<IProduct>();
    public Map<IProduct, List<Operation>> BankOperations = new HashMap<>();

    @Override
    public void createAccount(String accountNumber, Integer ownerId) {
        Account tempName = new Account(accountNumber, ownerId);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createTermDeposit(Account associatedAccount, BigDecimal originalAmount, String termDepositNumber) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, termDepositNumber);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                             String creditNumber, BigDecimal interestRate) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, creditNumber, interestRate);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }
}
