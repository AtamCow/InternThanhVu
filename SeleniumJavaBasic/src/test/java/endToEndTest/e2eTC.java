package endToEndTest;

import base.BaseSetup;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigTest;
import data.MainData;
import enums.Location;
import enums.SeatType;
import models.Ticket;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.File;
import java.io.IOException;

public class e2eTC extends BaseSetup {
    private ConfigTest cf;
    private PageBase pageBase;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private GuerrillamailPage guerrillamailPage;
    private MyTicketPage myTicketPage;
    private TicketPricePage ticketPricePage;
    private TimetablePage timetablePage;
    private FAQPage faqPage;
    private HomePage homePage;
    private RegisterPage registerPage;

    private SoftAssert softAssert;


    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";

    private User validUser;
    private User registerUser;
    private User inactiveUser;

    private Ticket ticket;

    @BeforeClass
    public void setUp() throws IOException {
        super.setup();
        cf  = new ConfigTest(getDriver());
        pageBase = new PageBase(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        loginPage = new LoginPage(getDriver());
        faqPage = new FAQPage(getDriver());
        myTicketPage = new MyTicketPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        homePage = new HomePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        registerPage = new RegisterPage(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);
        inactiveUser = new User(cf.reEmail, cf.logPassword);

        ObjectMapper mapper = new ObjectMapper();
        MainData mainData = mapper.readValue(new File("src/test/data/User.json"), MainData.class);

        Ticket ticketInfo = mainData.getTicketsInfo().get("ticket");

        ticket = new Ticket(ticketInfo.getDepartDate(), ticketInfo.getDepartStation(),
                ticketInfo.getArriveAt(), ticketInfo.getSeatType(),
                ticketInfo.getTicketAmount());
    }


    @Test (description = "User create account")
    public void TC01() {
        softAssert = new SoftAssert();

        cf.navigateRailway();

        // Register
        softAssert.assertNotNull(homePage.register());
        softAssert.assertEquals(homePage.register().getText(), "create an account");
        homePage.register().click();


        pageBase.checkCurrentPage("Register");
        registerPage.register(registerUser);
        softAssert.assertTrue(registerPage.checkTitle(cf.messageTitleAfterRegister), "The title message is not shown");

        // Login with inactive account
        loginPage.changePage();
        loginPage.login(inactiveUser);

        softAssert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
        softAssert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");

        // Active account
        cf.navigateQuerrilMail();
        int originNumTabs = pageBase.getTabNumber();
        guerrillamailPage.confirmAccount(cf.reIdEmail, cf.reHostEmail);
        guerrillamailPage.confirmEmailWithTd();
        int newNumTabs = pageBase.getTabNumber();

        softAssert.assertEquals(newNumTabs, originNumTabs + 1);
        pageBase.changeToTab(1);

        softAssert.assertTrue(registerPage.checkConfirmed(cf.messageConfirmedRegister), "The confirm message is not shown");

        // Login with valid account
        loginPage.changePage();
        loginPage.login(inactiveUser);

        softAssert.assertEquals(String.format("Welcome %s", cf.validLogEmail), homePage.checkWelcomeMessage());

        // Check price form TimeTable
        timetablePage.changePage();
        timetablePage.checkPrice(departStation, arriveStation);
        softAssert.assertTrue(timetablePage.checkPriceSeattypeTable());

        // Book ticket
        ticketPricePage.bookTicketInTicketPrice(seatType);
        bookTicketPage.checkInfoFromTimetable(departStation, arriveStation);

        softAssert.assertEquals(bookTicketPage.checkInfoFromTimetable(departStation, arriveStation) ,departStation+arriveStation);

        bookTicketPage.selectDepartDate(departDate);
        bookTicketPage.selectAmount(ticketAmount);
        bookTicketPage.clickBookticketButton();

        softAssert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        softAssert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticket));

        // Cancel ticket
        myTicketPage.changePage();
        myTicketPage.cancelTicket(ticket);
        softAssert.assertTrue(myTicketPage.checkTicketCanceled(ticket), "Ticket is not deleted");

        // Log out
        faqPage.changePage();
        pageBase.logOut();

        pageBase.waitMiliSec(1000);
        pageBase.checkCurrentPage("Home");
        pageBase.checkTabExisted("Log out");

        softAssert.assertAll();
    }


    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
