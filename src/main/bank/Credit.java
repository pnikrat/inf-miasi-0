package bank;

import java.math.BigDecimal;

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

    public Credit(Account associatedAccount, BigDecimal borrowedAmount, String creditNumber, BigDecimal interestRate) {
        this.associatedAccount = associatedAccount;
        associatedAccount.productDeposit(borrowedAmount);
        this.borrowedAmount = borrowedAmount;
        this.creditNumber = creditNumber;
        this.interestRate = interestRate;
        this.isCreditActive = true;
        BigDecimal tempNumber = borrowedAmount.add(interestRate);
        this.amountToPayback = tempNumber;
    }

    @Override
    public String getProductNumber() {
        return creditNumber;
    }

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
        if (amount.compareTo(amountToPayback) == 0) {
            amountToPayback = BigDecimal.ZERO;
            isCreditActive = false;
            return true;
        }
        return false;
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
