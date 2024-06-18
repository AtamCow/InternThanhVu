package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.*;
import models.Ticket;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class BookTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private TimetablePage timetablePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;

    private Ticket ticketInfo;
    private Ticket ticketsInfo;




    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());

        ticketInfo = new Ticket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmount);
        ticketsInfo = new Ticket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmounts);

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

        timetablePage.checkPrice(cf.departFrom, cf.arriveAt);

        timetablePage.checkPriceSeattypeTable();

    }

    @Test //User can book ticket from Timetable
    public void TC15() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        timetablePage.changePage();

        timetablePage.bookTicketInTimetable(cf.departFrom, cf.arriveAt);

        bookTicketPage.checkInfoFromTimetable(cf.departFrom, cf.arriveAt);

        bookTicketPage.selectDepartDate(cf.departDate);
        bookTicketPage.selectSeatType(cf.seatType);
        bookTicketPage.selectAmount(cf.ticketAmount);
        bookTicketPage.clickBookticketButton();

        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
        bookTicketSuccessfulPage.checkTicketInfo(ticketInfo);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
