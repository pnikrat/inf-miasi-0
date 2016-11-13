package bank;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by student on 05.11.2016.
 */
public class Operation {
    public enum operationType {
        TRANSFER,
        DEPOSIT,
        WITHDRAW,
        INTEREST_INCOME,
        CHANGE_INTEREST,
        CREATE_TERM_DEPOSIT,
        DESTROY_TERM_DEPOSIT,
        TAKE_CREDIT,
        REPAY_CREDIT,
        CREATE_DEBIT,
        CREATE_REPORT
    }
    private operationType type;
    private LocalDate executionDate;
    private String description;

    public Operation(operationType type, LocalDate executionDate, String description) {
        this.type = type;
        this.description = description;
        this.executionDate = executionDate;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    @Override
    public String toString() {
        return "Typ operacji: " + this.description + " Data realizacji: " + this.executionDate.toString();
    }
}

