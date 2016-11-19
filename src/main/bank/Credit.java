package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bank.Operation.*;

/**
 * Created by Przemek on 2016-11-13.
 */
public class Credit implements IProduct {
    private Account associatedAccount;
    private BigDecimal borrowedAmount;
    private BigDecimal amountToPayback;
    private String creditNumber;
    private BigDecimal interestRate;
    private boolean isCreditActive;
    private List<Operation> operationHistory = new ArrayList<Operation>();

    public Credit(Account associatedAccount, BigDecimal borrowedAmount, String creditNumber, BigDecimal interestRate) {
        this.associatedAccount = associatedAccount;
        associatedAccount.productDeposit(borrowedAmount);
        this.borrowedAmount = borrowedAmount;
        this.creditNumber = creditNumber;
        this.interestRate = interestRate;
        this.isCreditActive = true;
        addOperationToHistory(new Operation(operationType.TAKE_CREDIT, LocalDate.now(), "Wziecie kredytu"));
        BigDecimal tempNumber = borrowedAmount.add(interestRate);
        this.amountToPayback = tempNumber;
    }

    @Override
    public String getProductNumber() {
        return creditNumber;
    }

    @Override
    public List<Operation> getOperationHistory() {
        return operationHistory;
    }

    public boolean getIsCreditActive() {return isCreditActive; }

    public BigDecimal getAmountToPayback() {return amountToPayback; }

    @Override
    public void productDeposit(BigDecimal amount) {

    }

    @Override
    public void productWithdrawal(BigDecimal amount) {

    }

    @Override
    public boolean initiateLocalTransfer(IProduct targetBankProduct, BigDecimal amount) {
        return false;
    }

    @Override
    public boolean acceptLocalTransfer(BigDecimal amount) {
        if (amount.compareTo(amountToPayback) >= 0) {
            if (amount.compareTo(amountToPayback) == 1) //more money was transferred for credit repayment than needed
                associatedAccount.productDeposit(amount.subtract(amountToPayback));
            amountToPayback = BigDecimal.ZERO;
            isCreditActive = false;
            addOperationToHistory(new Operation(operationType.REPAY_CREDIT, LocalDate.now(), "Splata kredytu"));
            return true;
        }
        return false; //not enough money
    }

    @Override
    public void addOperationToHistory(Operation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public String toString() {
        return "Kredyt ID: " + creditNumber + ". Pozyczona kwota: " + borrowedAmount + "\n"
                + "Powiazane konto: \n" + associatedAccount.toString();
    }
}
