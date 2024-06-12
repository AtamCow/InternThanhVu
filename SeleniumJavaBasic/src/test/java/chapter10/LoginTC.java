package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class LoginTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private FAQPage faqPage;
    private HomePage homePage;
    private RegisterPage registerPage;
    private TimetablePage timetablePage;
    private TicketPricePage ticketPricePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;
    private ChangePasswordPage changePasswordPage;
    private ForgotPasswordPage forgotPasswordPage;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        faqPage = new FAQPage(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        pageBase = new PageBase(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        changePasswordPage = new ChangePasswordPage(getDriver());
        forgotPasswordPage = new ForgotPasswordPage(getDriver());
    }

    @Test //User can log into Railway with valid username and password
    public void TC01() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        homePage.checkWelcomeMessage(cf.validLogEmail);
    }

    @Test //User cannot login with blank "Username" textbox
    public void TC02() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login("", cf.logPassword);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
        loginPage.checkLoginYet();
    }

    @Test //User cannot log into Railway with invalid password
    public void TC03() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.invalidPassword);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
    }

    @Test //System shows message when user enters wrong password many times
    public void TC04() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();

        for (int i = 0; i < 4; i++) {
            loginPage.login(cf.validLogEmail, cf.invalidPassword);
            loginPage.checkErrorMessage(cf.messageInvalidLoginform);
        }
        loginPage.checkLoginYet();
        loginPage.checkExistMessage(cf.messageWarningLoginform);

    }

    @Test //User can't login with an account hasn't been activated
    public void TC05() {
        cf.navigateRailway();

        //Register
        registerPage.changePage();
        registerPage.register(cf.validLogEmail, cf.logPassword, cf.rePid);

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        loginPage.checkLoginYet();
        loginPage.checkErrorMessage(cf.messageInvalidLoginform);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
