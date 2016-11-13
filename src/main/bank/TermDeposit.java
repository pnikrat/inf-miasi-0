package bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import bank.Operation.*;

/**
 * Created by student on 05.11.2016.
 */
public class TermDeposit implements IProduct {
    private Account associatedAccount;
    private BigDecimal originalAmount;
    private String termDepositNumber;
    private Integer termDepositPeriod;
    private BigDecimal interestRate;
    private boolean isTermDepositActive;
    private List<Operation> operationHistory = new ArrayList<Operation>();

    public TermDeposit(Account associatedAccount, BigDecimal originalAmount, String termDepositNumber) {
        this.associatedAccount = associatedAccount;
        associatedAccount.productWithdrawal(originalAmount); //TYMCZASOWE
        productDeposit(originalAmount);
        this.termDepositNumber = termDepositNumber;
        this.isTermDepositActive = true;
        addOperationToHistory(new Operation(operationType.CREATE_TERM_DEPOSIT, LocalDate.now(), "Zalozenie lokaty"));
    }

    @Override
    public String getProductNumber() {
        return termDepositNumber;
    }

    @Override
    public List<Operation> getOperationHistory() {
        return operationHistory;
    }

    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public void productWithdrawal(BigDecimal amount) {
        if (termDepositPeriod > 0) {
            associatedAccount.productDeposit(originalAmount);
            //TODO: Find a way to destroy TermDeposit Object and eliminate it from BankProducts List
        }
        else {
            BigDecimal amountToWithdraw =
                    originalAmount.multiply((BigDecimal.ONE.add(interestRate)).pow(termDepositPeriod));
            associatedAccount.productDeposit(amountToWithdraw);
        }
        isTermDepositActive = false;
        addOperationToHistory(new Operation(operationType.DESTROY_TERM_DEPOSIT, LocalDate.now(), "Zerwanie lokaty"));
    }

    @Override
    public void addOperationToHistory(Operation operation) {
        operationHistory.add(operation);
        Collections.sort(operationHistory, new OperationComparator());
    }

    @Override
    public void productDeposit(BigDecimal amount) {
        this.originalAmount = amount;
    }

    @Override
    public boolean initiateLocalTransfer(IProduct targetBankProduct, BigDecimal amount) {
        return false;
    }

    @Override
    public boolean acceptLocalTransfer(BigDecimal amount) {
        return false;
    }

    @Override
    public String toString() {
        return "Lokata ID: " + termDepositNumber + ". Zalozona na kwote: " + originalAmount + "\n"
                + "Powiazane konto: \n" + associatedAccount.toString();
    }
}
