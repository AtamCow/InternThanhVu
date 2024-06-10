package chapter8;

import base.BaseSetup;
import config.ConfigTest;
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
    }

    @Test
    public void chap8() {
        cf.navigateRailway();

        // Login
        pageBase.changePage("Login");
        loginPage.login(cf.validLogEmail, cf.logPassword);

        // Change to Timetable tab
        pageBase.changePage("Timetable");
        timetablePage.checkPrice(cf.departFrom, cf.arriveAt);

        ticketPricePage.bookTicketInTicketPrice(cf.seatType);

        bookTicketPage.bookTicket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmount);

        bookTicketSuccessfulPage.checkTicketInfo(cf.loginSuccessfullMessage, cf.departFrom, cf.arriveAt, cf.seatType, cf.departDate, cf.ticketAmount  );
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

}