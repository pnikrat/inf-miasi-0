package bank;

import interfaces.*;
import operations.CreateCredit;
import operations.CreateDebit;
import operations.CreateTermDeposit;
import org.omg.PortableServer.POAPackage.InvalidPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by student on 05.11.2016.
 */
public class Bank implements IBank {
    private Map<String, IProduct> BankProducts = new HashMap<>();
    private Map<IProduct, List<IOperation>> BankOperations = new HashMap<>();
    private IKir mediator;

    @Override
    public boolean executeIOperation(IOperation operationToExecute) {
        return operationToExecute.executeOperation();
    }

    @Override
    public void createAccount(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism) {
        Account tempName = new Account(accountNumber, ownerId, interestRateMechanism);
        BankProducts.put(accountNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createAccount(String accountNumber, Integer ownerId) {
        this.createAccount(accountNumber, ownerId, null);
    }

    @Override
    public boolean createTermDeposit(IDebitable associatedAccount, BigDecimal originalAmount,
                                  LocalDate endDate, String termDepositNumber, IInterestRate interestRateMechanism) {
        if (!(associatedAccount.isBalancePositive(originalAmount)))
            return false;
        TermDeposit tempName = new TermDeposit(associatedAccount, originalAmount, endDate,
                                                termDepositNumber, interestRateMechanism);
        CreateTermDeposit createOperation = new CreateTermDeposit(associatedAccount, tempName, originalAmount);
        executeIOperation(createOperation);
        BankProducts.put(termDepositNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
        return true;
    }

    @Override
    public boolean createTermDeposit(IDebitable associatedAccount, BigDecimal originalAmount,
                                     LocalDate endDate, String termDepositNumber) {
        return this.createTermDeposit(associatedAccount, originalAmount, endDate, termDepositNumber, null);
    }

    @Override
    public void createCredit(IDebitable associatedAccount, BigDecimal borrowedAmount,
                             LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism) {
        Credit tempName = new Credit(associatedAccount, borrowedAmount, repaymentDate,
                                        creditNumber, interestRateMechanism);
        CreateCredit createOperation = new CreateCredit(associatedAccount, tempName, borrowedAmount);
        executeIOperation(createOperation);
        BankProducts.put(creditNumber, tempName);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public void createCredit(IDebitable associatedAccount, BigDecimal borrowedAmount,
                             LocalDate repaymentDate, String creditNumber) {
        this.createCredit(associatedAccount, borrowedAmount, repaymentDate, creditNumber, null);
    }

    @Override
    public void createDebitAccount(IDebitable decoratedAccount, BigDecimal maximumDebit) {
        DebitAccount tempName = new DebitAccount(decoratedAccount, maximumDebit);
        CreateDebit createOperation = new CreateDebit(decoratedAccount, maximumDebit);
        executeIOperation(createOperation);
        BankProducts.remove(decoratedAccount.getProductNumber());
        BankProducts.put(tempName.getProductNumber(), tempName);
        BankOperations.remove(decoratedAccount);
        BankOperations.put(tempName, tempName.getOperationHistory());
    }

    @Override
    public IProduct getBankProduct(String productNumber) {
        return BankProducts.get(productNumber);
    }

    @Override
    public IDebitable getBankDebitable(String productNumber) {
        for (IProduct x : BankProducts.values()) {
            if (x instanceof IDebitable) {
                if (x.getProductNumber().equals(productNumber))
                    return (IDebitable)x;
            }
        }
        return null;
    }

    @Override
    public ICreditable getBankCreditable(String productNumber) {
        for (IProduct x : BankProducts.values()) {
            if (x instanceof ICreditable) {
                if (x.getProductNumber().equals(productNumber))
                    return (ICreditable)x;
            }
        }
        return null;
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

    @Override
    public void subscribeToMediator(IKir mediatorToConnect) {
        this.mediator = mediatorToConnect;
        mediator.acceptNewNode(this);
    }

    @Override
    public boolean executeKirTransfer(IOperation transfer, IProduct targetProduct) {
        return mediator.executeInterBankTransfer(transfer, targetProduct);
    }
}
