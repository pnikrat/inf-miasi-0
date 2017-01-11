package interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    void createCredit(IProduct associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber, IInterestRate interestRateMechanism);
    void createCredit(IProduct associatedAccount, BigDecimal borrowedAmount,
                      LocalDate repaymentDate, String creditNumber);

    void createDebitAccount(IProduct decoratedAccount, BigDecimal maximumDebit);

    IProduct getBankProduct(String productNumber);
    Map<String, IProduct> getBankProducts();

    List<IOperation> getListOfOperationsByProduct(IProduct product);
    List<IOperation> getListOfOperationsByProductNumber(String productNumber);
    Map<IProduct, List<IOperation>> getBankOperations();

    void subscribeToMediator(IKir mediatorToConnect);
    boolean executeKirTransfer(IOperation transfer, IProduct targetProduct);
}
