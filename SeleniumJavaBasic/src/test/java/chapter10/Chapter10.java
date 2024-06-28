package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.*;
import models.*;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.IOException;

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

    private SoftAssert softAssert;

    private Ticket ticketInfo;
    private Ticket ticketsInfo;

    private User validUser;
    private User blankEmailUser;
    private User blankPassUser;
    private User invalidPassUser;
    private User registerUser;
    private User inactiveUser;
    private User registerBlankPassPidUser;

    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";
    String ticketAmounts = "2";

    @BeforeClass
    public void setUp() throws IOException {
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

        ticketInfo = new Ticket(cf.departDate, departStation, arriveStation, seatType, ticketAmount);
        ticketsInfo = new Ticket(cf.departDate, departStation, arriveStation, seatType, ticketAmounts);


        validUser = new User(cf.validLogEmail, cf.logPassword);
        blankEmailUser = new User("", cf.logPassword);
        blankPassUser = new User(cf.validLogEmail, "");
        invalidPassUser = new User(cf.validLogEmail, cf.invalidPassword);

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);
        inactiveUser = new User(cf.reEmail, cf.logPassword);
        registerBlankPassPidUser = new User(cf.reEmail, "", "");

    }

    @Test(description = "User can log into Railway with valid username and password")
    public void TC01() {
        cf.navigateRailway();

        loginPage.changePage();
        loginPage.login(validUser);

        Assert.assertEquals(String.format("Welcome %s", cf.validLogEmail), homePage.checkWelcomeMessage());
        pageBase.logOut();
    }

    @Test(description = "User cannot login with blank \"Username\" textbox")
    public void TC02() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(blankEmailUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
    }

    @Test(description = "User cannot log into Railway with invalid password")
    public void TC03() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(invalidPassUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
    }

    @Test(description = "System shows message when user enters wrong password many times")
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

    @Test(description = "User can't login with an account hasn't been activated")
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

    @Test(description = "User is redirected to Home page after logging out")
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

    @Test(description = "User can't create account with an already in-use email")
    public void TC07() {
        cf.navigateRailway();

        registerPage.changePage();
        registerPage.register(registerUser);

        Assert.assertTrue(registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail));
        pageBase.logOut();
    }

    @Test(description = "User can't create account while password and PID fields are empty")
    public void TC08() {
        cf.navigateRailway();

        registerPage.changePage();
        registerPage.register(registerBlankPassPidUser);

        Assert.assertTrue(registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail));

        Assert.assertTrue(registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password"),"Error message display out of the field");
        Assert.assertTrue(registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid"),"Error message display out of the field");
        pageBase.logOut();

    }

    @Test(description = "User create and activate account")
    public void TC09() {
        cf.navigateRailway();

        Assert.assertNotNull(homePage.register());
        Assert.assertEquals(homePage.register().getText(), "create an account");
        homePage.register().click();


        pageBase.checkCurrentPage("Register");
        registerPage.register(registerUser);
        Assert.assertTrue(registerPage.checkTitle(cf.messageTitleAfterRegister), "The title message is not shown");

        cf.navigateQuerrilMail();
        int originNumTabs = pageBase.getTabNumber();
        guerrillamailPage.confirmAccount(cf.reIdEmail, cf.reHostEmail);
        guerrillamailPage.confirmEmailWithTd();
        int newNumTabs = pageBase.getTabNumber();

        Assert.assertEquals(newNumTabs, originNumTabs + 1);
        pageBase.changeToTab(1);

        Assert.assertTrue(registerPage.checkConfirmed(cf.messageConfirmedRegister), "The confirm message is not shown");
        pageBase.logOut();
    }

    @Test(description = "User create and activate account")
    public void TC10() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        Assert.assertTrue(changePasswordPage.checkPasswordChangeFormShown(), "Reset Password token do not display");
        changePasswordPage.enterNewPassword(cf.logPassword, cf.logPassword);
        Assert.assertTrue(changePasswordPage.checkWarningSamePassMessageShown(), "Message did not appear as expected");
        pageBase.logOut();
    }

    @Test(description = "Reset password shows error if the new password and confirm password doesn't match")
    public void TC11() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        Assert.assertTrue(changePasswordPage.checkPasswordChangeFormShown(), "Reset Password token do not display");

        changePasswordPage.enterNewPassword(cf.logPassword, cf.invalidPassword);

        Assert.assertTrue(changePasswordPage.checkErrorMessageAbove(cf.messageErrorAbove));
        Assert.assertTrue(changePasswordPage.checkErrorMessageNextto(cf.messageErrorNexttoConfirmPass, "confirmPassword"),"Error message display out of the field");
    }

    @Test(description = "User can book 1 ticket at a time")
    public void TC12() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketInfo));
        pageBase.logOut();
    }

    @Test(description = "User can book many tickets at a time")
    public void TC13() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketsInfo);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketsInfo));

        pageBase.logOut();
    }

    @Test(description = "User can check price of ticket from Timetable")
    public void TC14() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        timetablePage.changePage();
        timetablePage.checkPrice(departStation, arriveStation);
        Assert.assertTrue(timetablePage.checkPriceSeattypeTable());
        pageBase.logOut();
    }

    @Test(description = "User can book ticket from Timetable")
    public void TC15() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        timetablePage.changePage();
        timetablePage.bookTicketInTimetable(departStation, arriveStation);

        bookTicketPage.checkInfoFromTimetable(departStation, arriveStation);

        Assert.assertEquals(bookTicketPage.checkInfoFromTimetable(departStation, arriveStation) ,departStation+arriveStation);


        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectSeatType(seatType);
        bookTicketPage.selectAmount(ticketAmount);
        bookTicketPage.clickBookticketButton();

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketInfo));
        pageBase.logOut();
    }

    @Test(description = "User can cancel a ticket")
    public void TC16() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        myTicketPage.changePage();
        myTicketPage.cancelTicket(ticketInfo);
        Assert.assertTrue(myTicketPage.checkTicketCanceled(ticketInfo), "Ticket is not deleted");
        pageBase.logOut();
    }

    @AfterTest
    public void close() {
        pageBase.logOut();
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
