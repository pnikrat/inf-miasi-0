package bank;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Przemek on 2016-11-23.
 */
public class InterestCapitalisation implements IOperation {
    private final Integer operationTypeId = 8;
    private LocalDate executionDate;
    private String description;
    private IInterestRate interestRateMechanism;

    public InterestCapitalisation(IProduct productToCapitalise) {
        this.executionDate = LocalDate.now();
        this.description = "OperationID: " + operationTypeId
                + "\nNaliczenie odsetek dla: " + productToCapitalise.toString();
        this.interestRateMechanism = productToCapitalise.getInterestRateMechanism();
        if(productToCapitalise instanceof Account)
            capitaliseAccount((Account) productToCapitalise);
        else if(productToCapitalise instanceof TermDeposit)
            capitaliseTermDeposit((TermDeposit) productToCapitalise);
        else if(productToCapitalise instanceof Credit)
            capitaliseCredit((Credit) productToCapitalise);
        productToCapitalise.addOperationToHistory(this);
    }

    @Override
    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    @Override
    public LocalDate getExecutionDate() {
        return executionDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void capitaliseAccount(Account accountToCapitalise) {
        BigDecimal currentAccountBalance = accountToCapitalise.getBalance();
        accountToCapitalise.setBalance(currentAccountBalance
                .add(interestRateMechanism.capitalisation(currentAccountBalance)));
    }

    private void capitaliseTermDeposit(TermDeposit termDepositToCapitalise) {
        BigDecimal originalAmount = termDepositToCapitalise.getBalance();
        BigDecimal finalAmount = interestRateMechanism.calculateFinalValue(originalAmount,
                termDepositToCapitalise.getCreationDate(), termDepositToCapitalise.getEndDate());
        termDepositToCapitalise.setBalance(finalAmount);
    }

    private void capitaliseCredit(Credit creditToCapitalise) {
        BigDecimal borrowedAmount = creditToCapitalise.getBalance();
        BigDecimal amountToPayback = interestRateMechanism.calculateFinalValue(borrowedAmount,
                creditToCapitalise.getCreationDate(), creditToCapitalise.getRepaymentDate());
        creditToCapitalise.setBalance(amountToPayback);
    }
}
