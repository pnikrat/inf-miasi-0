package helpers;

import interfaces.IOperation;

import java.util.Comparator;

public class OperationComparator implements Comparator<IOperation> {
    public int compare(IOperation o1, IOperation o2) {
        return o1.getExecutionDate().compareTo(o2.getExecutionDate());
    }
    // TODO: Add several tests if operations are sorted chronologically
}
