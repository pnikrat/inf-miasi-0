package interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by student on 05.11.2016.
 */
public interface IBank {

    boolean executeIOperation(IOperation operationToExecute);

    void createAccount(String accountNumber, Integer ownerId, IInterestRate interestRateMechanism);
    void createAccount(String accountNumber, Integer ownerId);

    boolean createTermDeposit(IDebitable associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                           String termDepositNumber, IInterestRate interestRateMechanism);
    boolean createTermDeposit(IDebitable associatedAccount, BigDecimal originalAmount, LocalDate endDate,
                              String termDepositNumber);

    void createCredit(IDebitable associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism);
    void createCredit(IDebitable associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber);

    void createDebitAccount(IDebitable decoratedAccount, BigDecimal maximumDebit);

    IProduct getBankProduct(String productNumber);
    IDebitable getBankDebitable(String productNumber);
    ICreditable getBankCreditable(String productNumber);

    Map<String, IProduct> getBankProducts();

    List<IOperation> getListOfOperationsByProduct(IProduct product);
    List<IOperation> getListOfOperationsByProductNumber(String productNumber);
    Map<IProduct, List<IOperation>> getBankOperations();

    void subscribeToMediator(IKir mediatorToConnect);
    boolean executeKirTransfer(IOperation transfer, IProduct targetProduct);
}
