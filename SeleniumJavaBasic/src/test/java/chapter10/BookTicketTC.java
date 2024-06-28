package chapter10;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.*;

import java.io.File;
import java.io.IOException;


public class BookTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private PageBase pageBase;
    private TimetablePage timetablePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;

    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";
    String ticketAmounts = "2";

    private User validUser;
    private Ticket ticket;
    private Ticket tickets;


    @BeforeClass
    public void setUp() throws IOException {
        super.setup();
        cf  = new ConfigTest(getDriver());
        pageBase = new PageBase(getDriver());
        loginPage = new LoginPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());


    }

    @DataProvider(name = "User and Ticket information")
    public Object[][] getData() throws IOException {
        // Read JSON data
        ObjectMapper mapper = new ObjectMapper();
        MainData mainData = mapper.readValue(new File("src/test/data/User.json"), MainData.class);

        User validUserInfo = mainData.getUsersInfo().get("validUser");
        Ticket ticketInfo = mainData.getTicketsInfo().get("ticket");
        Ticket ticketsInfo = mainData.getTicketsInfo().get("tickets");

        validUser = new User(validUserInfo.getEmail(), validUserInfo.getPassword());

        ticket = new Ticket(ticketInfo.getDepartDate(), ticketInfo.getDepartStation(),
                ticketInfo.getArriveAt(), ticketInfo.getSeatType(),
                ticketInfo.getTicketAmount());

        tickets = new Ticket(ticketsInfo.getDepartDate(), ticketsInfo.getDepartStation(),
                ticketsInfo.getArriveAt(), ticketsInfo.getSeatType(),
                ticketsInfo.getTicketAmount());

        return new Object[][]{
                {validUser, ticket},
                {validUser, tickets}
        };
    }

    @Test(dataProvider = "User and Ticket information", description = "Use DataProvider")
    public void ExTC(User user, Ticket ticket) {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(user);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticket);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticket));
        pageBase.logOut();
    }

    @Test(description = "User can book 1 ticket at a time")
    public void TC12() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticket);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticket));
        pageBase.logOut();
    }

    @Test(description = "User can book many tickets at a time")
    public void TC13() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(tickets);

        Assert.assertEquals(cf.bookSuccessfullMessage, bookTicketSuccessfulPage.checkBookedSuccessfulMessage());
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(tickets));

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
        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketInfo(ticket));
        pageBase.logOut();
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
