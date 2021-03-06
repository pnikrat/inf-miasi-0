package interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by student on 05.11.2016.
 */
public interface IProduct {
    Integer getOwnerId();
    String getProductNumber();

    List<IOperation> getOperationHistory();
    void addOperationToHistory(IOperation operation);

    BigDecimal getBalance();
    void setBalance(BigDecimal amount);

    void setInterestRateMechanism(IInterestRate newMechanism);
    IInterestRate getInterestRateMechanism();

    void setCreationDate(LocalDate date); //for testing purposes
    LocalDate getCreationDate();

    String toString();

    void accept(IProductVisitor visitor);
}
