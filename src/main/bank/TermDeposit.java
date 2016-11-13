package bank;

import java.math.BigDecimal;

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
    //dodaj historie operacji

    public TermDeposit(Account associatedAccount, BigDecimal originalAmount, String termDepositNumber) {
        this.associatedAccount = associatedAccount;
        associatedAccount.productWithdrawal(originalAmount); //TYMCZASOWE
        productDeposit(originalAmount);
        this.termDepositNumber = termDepositNumber;
        this.isTermDepositActive = true;
    }

    @Override
    public String getProductNumber() {
        return termDepositNumber;
    }

    @Override
    public boolean isBalancePositive(BigDecimal amount) {
        return false;
    }

    @Override
    public void productWithdrawal(BigDecimal amount) {
        if (termDepositPeriod > 0) {
            associatedAccount.productDeposit(originalAmount);
            //rozwiaz lokate
        }
        else {
            BigDecimal amountToWithdraw =
                    originalAmount.multiply((BigDecimal.ONE.add(interestRate)).pow(termDepositPeriod));
            associatedAccount.productDeposit(amountToWithdraw);
        }
        isTermDepositActive = false;
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
