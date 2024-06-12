package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class Chapter10 extends BaseSetup {
    private static final Logger log = LoggerFactory.getLogger(Chapter10.class);
    private ConfigTest cf;

    private LoginPage loginPage;
    private HomePage homePage;
    private RegisterPage registerPage;
    private TimetablePage timetablePage;
    private TicketPricePage ticketPricePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private PageBase pageBase;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        pageBase = new PageBase(getDriver());
    }

    @Test //User can log into Railway with valid username and password
    public void TC1() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.logPassword);

        homePage.checkWelcomeMessage(cf.wellcomMessage);
    }

    @Test //User cannot login with blank "Username" textbox
    public void TC2() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");
        loginPage.login("", cf.logPassword);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
        loginPage.checkLoginYet();
    }

    @Test //User cannot log into Railway with invalid password
    public void TC3() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.invalidPassword);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
    }

    @Test //System shows message when user enters wrong password many times
    public void TC4() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");

        for (int i = 0; i < 4; i++) {
            loginPage.login(cf.validLogEmail, cf.invalidPassword);
            loginPage.checkErrorMessage(cf.messageInvalidLoginform);
        }
        loginPage.checkLoginYet();
        loginPage.checkExistMessage(cf.messageWarningLoginform);

    }

    @Test //User can't login with an account hasn't been activated
    public void TC5() {
        cf.navigateRailway();

        //Register
        pageBase.changePage("Register");
        registerPage.register(cf.validLogEmail, cf.logPassword, cf.rePid);

        // Login
        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.logPassword);

        loginPage.checkLoginYet();
        loginPage.checkErrorMessage(cf.messageInvalidLoginform);
    }

    @Test //User is redirected to Home page after logging out
    public void TC6() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.logPassword);

        pageBase.changePage("FAQ");
        pageBase.changePage("Log out");

        pageBase.waitMiliSec(1000);
        pageBase.checkCurrentPage("Home");
        pageBase.checkTabExisted("Log out");
    }

    @Test //User can't create account with an already in-use email
    public void TC7() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Register");
        registerPage.register(cf.validLogEmail, cf.logPassword, cf.rePid);

        registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail);
    }

    @Test //User can't create account while password and PID fields are empty
    public void TC8() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Register");
        registerPage.register(cf.validLogEmail, "", "");

        registerPage.checkErrorMessageAbove(cf.messageErrorRegister);
        registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password");
        registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid");
    }

    @Test //User create and activate account
    public void TC9() {
        cf.navigateRailway();

        homePage.register();
        pageBase.checkCurrentPage("Register");
        registerPage.register(cf.reEmail, cf.logPassword, cf.rePid);
        registerPage.checkTitle(cf.messageTitleAfterRegister);


        registerPage.checkErrorMessageAbove(cf.messageErrorRegister);
        registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password");
        registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid");
    }




    @After
    public void tearDown() {
        super.tearDown();
    }
}
