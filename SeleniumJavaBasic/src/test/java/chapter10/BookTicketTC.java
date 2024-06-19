package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.DepartDate;
import enums.Location;
import enums.SeatType;
import enums.TicketAmount;
import models.Ticket;
//import org.junit.*;
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

        SoftAssert softAssert = new SoftAssert();

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);
        ticketsInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmounts);

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

//    @Test //User can book many tickets at a time
//    public void TC13() {
//        cf.navigateRailway();
//        loginPage.changePage();
//
//        loginPage.login(cf.validLogEmail, cf.logPassword);
//
//        bookTicketPage.changePage();
//        bookTicketPage.bookTicket(ticketsInfo);
//
//        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
//        bookTicketSuccessfulPage.checkTicketInfo(ticketsInfo);
//    }
//
//    @Test //User can check price of ticket from Timetable
//    public void TC14() {
//        cf.navigateRailway();
//        loginPage.changePage();
//        loginPage.login(cf.validLogEmail, cf.logPassword);
//
//        timetablePage.changePage();
//
//        timetablePage.checkPrice(departStation, arriveStation);
//
//        timetablePage.checkPriceSeattypeTable();
//
//    }
//
//    @Test //User can book ticket from Timetable
//    public void TC15() {
//        cf.navigateRailway();
//        loginPage.changePage();
//        loginPage.login(cf.validLogEmail, cf.logPassword);
//
//        timetablePage.changePage();
//
//        timetablePage.bookTicketInTimetable(departStation, arriveStation);
//
//        bookTicketPage.checkInfoFromTimetable(departStation, arriveStation);
//
//        bookTicketPage.selectDepartDate(departDate);
//        bookTicketPage.selectSeatType(seatType);
//        bookTicketPage.selectAmount(ticketAmount);
//        bookTicketPage.clickBookticketButton();
//
//        bookTicketSuccessfulPage.checkBookedSuccessfulMessage(cf.bookSuccessfullMessage);
//        bookTicketSuccessfulPage.checkTicketInfo(ticketInfo);
//    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
