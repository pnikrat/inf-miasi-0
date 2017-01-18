package interfaces;

import java.time.LocalDate;

/**
 * Created by pnikrat on 18.01.17.
 */
public interface ICreditable extends IProduct{
    IDebitable getAssociatedAccount();
    LocalDate getEndDate();

    boolean getIsCreditableProductActive();
    void setIsCreditableProductActive(boolean value);
}
