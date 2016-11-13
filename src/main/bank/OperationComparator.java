package bank;

import java.util.Comparator;

public class OperationComparator implements Comparator<Operation> {
    public int compare(Operation o1, Operation o2) {
        return o1.getExecutionDate().compareTo(o2.getExecutionDate());
    }
}
