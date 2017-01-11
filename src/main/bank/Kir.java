package bank;

import interfaces.IBank;
import interfaces.IKir;
import interfaces.IOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnikrat on 11.01.17.
 */
public class Kir implements IKir {

    private List<IBank> bankNodes = new ArrayList<IBank>();

    @Override
    public void acceptNewNode(IBank node) {
        bankNodes.add(node);
    }

    @Override
    public boolean executeInterBankTransfer(IOperation transfer) {
        return false;
    }
}
