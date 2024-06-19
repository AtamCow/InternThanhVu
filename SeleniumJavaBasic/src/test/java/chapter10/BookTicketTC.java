package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.DepartDate;
import enums.Location;
import enums.SeatType;
import enums.TicketAmount;
import models.Ticket;
//import org.junit.*;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import org.testng.asserts.SoftAssert;


public class BookTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private TimetablePage timetablePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;

    private User validUser;

    String departDate = DepartDate.DAY_25.getDate();
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = TicketAmount.ONE.getAmount();
    String ticketAmounts = TicketAmount.TWO.getAmount();

    private Ticket ticketInfo;
    private Ticket ticketsInfo;

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());

        validUser = new User(cf.validLogEmail, cf.logPassword);

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);
        ticketsInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmounts);

    }

    @Test //User can book 1 ticket at a time
    public void TC12() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketInfo));
    }

    @Test //User can book many tickets at a time
    public void TC13() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketsInfo);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticketsInfo));

    }

    @Test //User can check price of ticket from Timetable
    public void TC14() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        timetablePage.changePage();
        timetablePage.checkPrice(departStation, arriveStation);
        Assert.assertTrue(timetablePage.checkPriceSeattypeTable());
    }

    @Test //User can book ticket from Timetable
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
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
