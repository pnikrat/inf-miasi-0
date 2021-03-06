package bank;

import interfaces.IBank;
import interfaces.IDebitable;
import interfaces.IKir;
import interfaces.IProduct;
import operations.Deposit;
import operations.Transfer;
import operations.Withdraw;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableServer.POAPackage.InvalidPolicy;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pnikrat on 11.01.17.
 */
public class KirTest {

    private IBank originBank;
    private IBank uselessBank;
    private IBank destinationBank;
    private IDebitable notExistingAccount;
    private BigDecimal testNumber;
    private BigDecimal testNumber2;

    @Before
    public void setUp() throws Exception {
        originBank = new Bank();
        uselessBank = new Bank();
        destinationBank = new Bank();
        originBank.createAccount("1234567", 98);
        destinationBank.createAccount("4321", 56);
        //not assigned to any Bank
        notExistingAccount = new Account("00000", 9999);

        for (int i = 0 ; i < 5 ; i++) {
            uselessBank.createAccount("4567" + Integer.toString(i), i);
        }
        for (int i = 10 ; i < 20 ; i++) {
            destinationBank.createAccount("9081456" + Integer.toString(i), i);
        }

        IKir mediator = new Kir();
        originBank.subscribeToMediator(mediator);
        uselessBank.subscribeToMediator(mediator);
        destinationBank.subscribeToMediator(mediator);

        testNumber = new BigDecimal("1500.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        testNumber2 = new BigDecimal("864.56").setScale(2, BigDecimal.ROUND_HALF_UP);
        originBank.executeIOperation(new Deposit(originBank.getBankDebitable("1234567"), testNumber));
    }

    @Test
    public void testDestinationBankReceivesMoney() throws Exception {
        boolean result = originBank.executeKirTransfer(new Transfer(originBank.getBankDebitable("1234567"),
                destinationBank.getBankDebitable("4321"), testNumber2),
                destinationBank.getBankDebitable("4321"));
        assertTrue(result);
        assertEquals(864.56, destinationBank.getBankDebitable("4321").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testOriginBankTransfersMoney() throws Exception {
        boolean result = originBank.executeKirTransfer(new Transfer(originBank.getBankDebitable("1234567"),
                destinationBank.getBankDebitable("4321"), testNumber2),
                destinationBank.getBankDebitable("4321"));
        assertTrue(result);
        assertEquals(635.44, originBank.getBankDebitable("1234567").getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testDestinationBankDoesNotReceiveMoney() throws Exception {
        boolean result = originBank.executeKirTransfer(new Transfer(originBank.getBankDebitable("1234567"),
                notExistingAccount, testNumber2), notExistingAccount);
        assertFalse(result);
        assertEquals(0.00, notExistingAccount.getBalance().doubleValue(), 0.001);
    }

    @Test
    public void testOriginBankDoesNotTransferMoney() throws Exception {
        boolean result = originBank.executeKirTransfer(new Transfer(originBank.getBankDebitable("1234567"),
                notExistingAccount, testNumber2), notExistingAccount);
        assertFalse(result);
        assertEquals(1500.00, originBank.getBankDebitable("1234567").getBalance().doubleValue(), 0.001);
    }

}
