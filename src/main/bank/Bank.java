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

    // TODO: Add method boolean executeOperation(IOperation) -> Bank should be central repository to manage operations

    @Override
    public void createAccount(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism) {
        Account tempName = new Account(accountNumber, ownerId, interestRateMechanism);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public boolean createTermDeposit(Account associatedAccount, BigDecimal originalAmount,
                                  LocalDate endDate, String termDepositNumber, IInterestRate interestRateMechanism) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return false;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, endDate,
                                                termDepositNumber, interestRateMechanism);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
        return true;
    }

    @Override
    public void createCredit(Account associatedAccount, BigDecimal borrowedAmount,
                             LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, repaymentDate,
                                        creditNumber, interestRateMechanism);
        BankProducts.add(tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    public void createDebitAccount(IProduct decoratedAccount, BigDecimal maximumDebit) {
        DebitAccount tempName = new DebitAccount(decoratedAccount, maximumDebit);

        BankProducts.remove(decoratedAccount);
        BankProducts.add(tempName);
    }
}
