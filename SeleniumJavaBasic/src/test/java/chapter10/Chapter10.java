package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.*;
import models.*;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class Chapter10 extends BaseSetup {
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
    private MyTicketPage myTicketPage;

    private Ticket ticketInfo;
    private Ticket ticketsInfo;

    private User validUser;
    private User blankEmailUser;
    private User blankPassUser;
    private User invalidPassUser;
    private User registerUser;
    private User inactiveUser;

    String departDate = DepartDate.DAY_25.getDate();
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = TicketAmount.ONE.getAmount();
    String ticketAmounts = TicketAmount.TWO.getAmount();

    @BeforeClass
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
        myTicketPage = new MyTicketPage(getDriver());
        forgotPasswordPage = new ForgotPasswordPage(getDriver());

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);
        ticketsInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmounts);


        validUser = new User(cf.validLogEmail, cf.logPassword);
        blankEmailUser = new User("", cf.logPassword);
        blankPassUser = new User(cf.validLogEmail, "");
        invalidPassUser = new User(cf.validLogEmail, cf.invalidPassword);

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);
        inactiveUser = new User(cf.reEmail, cf.logPassword);
    }

    @Test //User can log into Railway with valid username and password
    public void TC01() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(validUser);

        Assert.assertEquals(String.format("Welcome %s", cf.validLogEmail), homePage.checkWelcomeMessage());
    }

    @Test //User cannot login with blank "Username" textbox
    public void TC02() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(blankEmailUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
    }

    @Test //User cannot log into Railway with invalid password
    public void TC03() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(invalidPassUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
    }

    @Test //System shows message when user enters wrong password many times
    public void TC04() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();

        for (int i = 0; i < 4; i++) {
            loginPage.login(invalidPassUser);
            Assert.assertEquals(cf.messageInvalidLoginform, loginPage.checkErrorMessage());
        }
        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
        Assert.assertTrue(loginPage.checkExistMessage(cf.messageWarningLoginform), "Could not find message");

    }

    @Test //User can't login with an account hasn't been activated
    public void TC05() {
        cf.navigateRailway();

        //Register
        registerPage.changePage();
        registerPage.register(registerUser);

        // Login
        loginPage.changePage();
        loginPage.login(inactiveUser);

        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
        Assert.assertEquals(cf.messageInvalidLoginform, loginPage.checkErrorMessage());
    }

    @Test //User is redirected to Home page after logging out
    public void TC06() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(validUser);

        faqPage.changePage();
        pageBase.logOut();

        pageBase.waitMiliSec(1000);
        Assert.assertTrue(pageBase.checkCurrentPage("Home"), "Home page do not display");
        Assert.assertFalse(pageBase.checkTabExisted("Log out"), "Log out tab is appeared");
    }
////////////////////////////////////////////////
    @Test //User can't create account with an already in-use email
    public void TC07() {
        cf.navigateRailway();

        // Login
        registerPage.changePage();
        registerPage.register(registerUser);

        registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail);
    }

    @Test //User can't create account while password and PID fields are empty
    public void TC08() {
        cf.navigateRailway();

        // Login
        registerPage.changePage();
        registerPage.register(cf.validLogEmail, "", "");

        registerPage.checkErrorMessageAbove(cf.messageErrorRegister);
        registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password");
        registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid");
    }

    @Test //User create and activate account
    public void TC09() {
        cf.navigateRailway();

        homePage.register();
        pageBase.checkCurrentPage("Register");
        registerPage.register(cf.reEmail, cf.logPassword, cf.rePid);
        registerPage.checkTitle(cf.messageTitleAfterRegister);

        cf.navigateQuerrilMail();
        int originNumTabs = pageBase.getTabNumber();
        guerrillamailPage.confirmAccount(cf.reIdEmail, cf.reHostEmail);
        guerrillamailPage.confirmEmailWithTd();
        int newNumTabs = pageBase.getTabNumber();

        Assert.assertEquals(newNumTabs, originNumTabs + 1);
        pageBase.changeToTab(1);

        registerPage.checkConfirmed(cf.messageConfirmedRegister);
    }

    @Test //User create and activate account
    public void TC10() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        changePasswordPage.checkPasswordChangeFormShown();
        changePasswordPage.enterNewPassword(cf.logPassword, cf.logPassword);
        changePasswordPage.checkWarningSamePassMessageShown();
    }

    @Test //Reset password shows error if the new password and confirm password doesn't match
    public void TC11() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        changePasswordPage.checkPasswordChangeFormShown();
        changePasswordPage.enterNewPassword(cf.logPassword, cf.invalidPassword);
        changePasswordPage.checkErrorMessageAbove(cf.messageErrorAbove);
        changePasswordPage.checkErrorMessageNextto(cf.messageErrorNexttoConfirmPass, "confirmPassword");
    }

    @Test //User can book 1 ticket at a time
    public void TC12() {
        cf.navigateRailway();
        loginPage.changePage();

        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();

        bookTicketPage.bookTicket(ticketInfo);

        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
        bookTicketSuccessfulPage.checkTicketInfo(ticketInfo);
    }

    @Test //User can book many tickets at a time
    public void TC13() {
        cf.navigateRailway();
        loginPage.changePage();

        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketsInfo);

        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
        bookTicketSuccessfulPage.checkTicketInfo(ticketsInfo);
    }

    @Test //User can check price of ticket from Timetable
    public void TC14() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        timetablePage.changePage();

        timetablePage.checkPrice(departStation, arriveStation);

        timetablePage.checkPriceSeattypeTable();

    }

    @Test //User can book ticket from Timetable
    public void TC15() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        timetablePage.changePage();

        timetablePage.bookTicketInTimetable(departStation, arriveStation);

        bookTicketPage.checkInfoFromTimetable(departStation, arriveStation);

        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectSeatType(seatType);
        bookTicketPage.selectAmount(ticketAmount);
        bookTicketPage.clickBookticketButton();

        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
        bookTicketSuccessfulPage.checkTicketInfo(ticketInfo);
    }

    @Test //User can cancel a ticket
    public void TC16() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        myTicketPage.changePage();
        myTicketPage.cancelTicket(departStation, arriveStation, seatType, departDate, ticketAmount);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
