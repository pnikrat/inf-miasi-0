package interfaces;

/**
 * Created by pnikrat on 11.01.17.
 */
public interface IKir {
    void acceptNewNode(IBank node);
    boolean executeInterBankTransfer(IOperation transfer);
}
