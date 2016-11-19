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



//        TODO: Create tests for TermDeposit behaviour
//        TODO: Create tests for BankOperations being filled after Products' operations are made



        for (IProduct x : mbank.BankOperations.keySet()) {
            if (x.getProductNumber().equals("1234 0451 1111 1111 0345 3456")) {
                for (Operation y : x.getOperationHistory()) {
                    System.out.println(y);
                }
            }
        }
    }
}
