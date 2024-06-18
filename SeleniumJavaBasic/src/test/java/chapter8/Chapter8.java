package chapter8;

import base.BaseSetup;
import config.ConfigTest;
import models.Ticket;
import org.junit.*;

import pages.*;

public class Chapter8 extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private TimetablePage  timetablePage;
    private TicketPricePage ticketPricePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private PageBase pageBase;

    private Ticket ticketInfo;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        pageBase = new PageBase(getDriver());

        ticketInfo = new Ticket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmount);
    }

    @Test
    public void chap8() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        // Change to Timetable tab
        timetablePage.changePage();
        timetablePage.checkPrice(cf.departFrom, cf.arriveAt);

        ticketPricePage.bookTicketInTicketPrice(cf.seatType);

        bookTicketPage.bookTicket(ticketInfo);

        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
        bookTicketSuccessfulPage.checkTicketInfo(ticketInfo);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

}