package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Bank implements IBank {
    private Map<String, IProduct> BankProducts = new HashMap<>();
    private Map<IProduct, List<IOperation>> BankOperations = new HashMap<>();

    // TODO: Add method boolean executeOperation(IOperation) -> Bank should be central repository to manage operations

    @Override
    public void createAccount(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism) {
        Account tempName = new Account(accountNumber, ownerId, interestRateMechanism);
        BankProducts.put(accountNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createAccount(String accountNumber, Integer ownerId) {
        Account tempName = new Account(accountNumber, ownerId);
        BankProducts.put(accountNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public boolean createTermDeposit(IProduct associatedAccount, BigDecimal originalAmount,
                                  LocalDate endDate, String termDepositNumber, IInterestRate interestRateMechanism) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return false;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, endDate,
                                                termDepositNumber, interestRateMechanism);
        BankProducts.put(termDepositNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
        return true;
    }

    @Override
    public boolean createTermDeposit(IProduct associatedAccount, BigDecimal originalAmount,
                                     LocalDate endDate, String termDepositNumber) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return false;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, endDate,
                termDepositNumber);
        BankProducts.put(termDepositNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
        return true;
    }

    @Override
    public void createCredit(IProduct associatedAccount, BigDecimal borrowedAmount,
                             LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, repaymentDate,
                                        creditNumber, interestRateMechanism);
        BankProducts.put(creditNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createCredit(IProduct associatedAccount, BigDecimal borrowedAmount,
                             LocalDate repaymentDate, String creditNumber) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, repaymentDate,
                                        creditNumber);
        BankProducts.put(creditNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createDebitAccount(IProduct decoratedAccount, BigDecimal maximumDebit) {
        DebitAccount tempName = new DebitAccount(decoratedAccount, maximumDebit);
        BankProducts.remove(decoratedAccount.getProductNumber());
        BankProducts.put(tempName.getProductNumber(), tempName);
        // TODO: Delete from BankOperations or not? Probably not - historian
    }

    @Override
    public IProduct getBankProduct(String productNumber) {
        return BankProducts.get(productNumber);
    }

    @Override
    public Map<String, IProduct> getBankProducts() {
        return BankProducts;
    }

    @Override
    public List<IOperation> getListOfOperationsByProduct(IProduct product) {
        return BankOperations.get(product);
    }

    @Override
    public List<IOperation> getListOfOperationsByProductNumber(String productNumber) {
        return BankOperations.get(getBankProduct(productNumber));
    }

    @Override
    public Map<IProduct, List<IOperation>> getBankOperations() {
        return BankOperations;
    }
}
