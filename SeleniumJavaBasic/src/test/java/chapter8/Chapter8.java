package chapter8;

import base.BaseSetup;
import config.ConfigTest;
import enums.Location;
import enums.SeatType;
import models.Ticket;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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

    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        pageBase = new PageBase(getDriver());

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);
    }

    @Test
    public void chap8() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        // Change to Timetable tab
        timetablePage.changePage();
        timetablePage.checkPrice(departStation, arriveStation);

        ticketPricePage.bookTicketInTicketPrice(seatType);

        bookTicketPage.bookTicket(ticketInfo);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketInfo));
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }

}