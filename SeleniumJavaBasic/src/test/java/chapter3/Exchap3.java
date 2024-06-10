package chapter3;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.*;


public class Exchap3 extends BaseSetup {
    private ConfigTest cf;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private PageBase pageBase;
    private RegisterPage registerPage;
    private GuerrillamailPage guerrillamailPage;

    @Before
    public void setUp() {
        super.setup();
        cf = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        registerPage = new RegisterPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        pageBase = new PageBase(getDriver());
    }

    @Test
    public void TC1() {
        cf.navigateRailway();

        pageBase.changePage("Register");

        registerPage.register(cf.validLogEmail, cf.logPassword, cf.rePid);

        cf.navigateQuerrilMail();

        // Confirm account
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);


        pageBase.changeToTab(1);

        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.logPassword);

        pageBase.changePage("Book ticket");

        bookTicketPage.bookTicket(cf.departDate, "Phan Thiết", cf.arriveAt, cf.seatType, cf.ticketAmount);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}