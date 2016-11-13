package bank;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by student on 05.11.2016.
 */
public class Operation {
    public enum operationType {
        PRZELEW,
        WPLATA,
        WYPLATA,
        NALICZENIE_ODSETEK
    }
    private operationType type;
    private LocalDate executionDate;
    private String description;

    public Operation(operationType type, LocalDate executionDate, String description) {
        this.type = type;
        this.description = description;
        this.executionDate = executionDate;
    }

    @Override
    public String toString() {
        return "Typ operacji: " + this.description + " Data realizacji: " + this.executionDate.toString();
    }
}
