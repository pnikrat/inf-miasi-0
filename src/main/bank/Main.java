package bank;

import java.math.BigDecimal;

/**
 * Created by student on 05.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        Bank mbank = new Bank();
        mbank.createAccount("1234 0451 1111 1111 0345 3456", 123);
        mbank.createAccount("1234 5678 9012 3456 6789 0123", 456);
        BigDecimal testNumber = new BigDecimal("45.54").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal testNumber2 = new BigDecimal("1340.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal testNumber3 = new BigDecimal("20.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal testNumber4 = new BigDecimal("950.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal testInterest = new BigDecimal("3.50").setScale(2, BigDecimal.ROUND_HALF_UP);

        for (IProduct x : mbank.BankProducts) {
            x.productDeposit(testNumber2);
            x.productWithdrawal(testNumber3);
            System.out.println(x);
        }


        mbank.BankProducts.get(0).initiateLocalTransfer(mbank.BankProducts.get(1), testNumber);
        mbank.createTermDeposit((Account) mbank.BankProducts.get(1), testNumber2, "LOCO001");
        mbank.createCredit((Account) mbank.BankProducts.get(0), testNumber4, "CRED001", testInterest);

        for (IProduct x: mbank.BankProducts) {
            System.out.println(x);
        }

        for (IProduct x : mbank.BankOperations.keySet()) {
            if (x.getProductNumber().equals("1234 0451 1111 1111 0345 3456")) {
                for (Operation y : x.getOperationHistory()) {
                    System.out.println(y);
                }
            }
        }
    }
}
