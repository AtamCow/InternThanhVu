package finalTestCases;

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

public class FinalTestCase extends BaseSetup {
    private ConfigTest cf;
    private PageBase pageBase;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private MyTicketPage myTicketPage;
    private TicketPricePage ticketPricePage;

    private User validUser;

    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeClass
    public void setUp() throws IOException {
        super.setup();
        cf  = new ConfigTest(getDriver());
        pageBase = new PageBase(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        loginPage = new LoginPage(getDriver());
        myTicketPage = new MyTicketPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());

        validUser = new User(cf.validLogEmail, cf.logPassword);

        ObjectMapper mapper = new ObjectMapper();
        MainData mainData = mapper.readValue(new File("src/test/data/User.json"), MainData.class);

        Ticket ticketInfo1 = mainData.getTicketsInfo().get("ticket1");
        Ticket ticketInfo2 = mainData.getTicketsInfo().get("ticket2");

        ticket1 = new Ticket(ticketInfo1.getDepartDate(), ticketInfo1.getDepartStation(),
                ticketInfo1.getArriveAt(), ticketInfo1.getSeatType(),
                ticketInfo1.getTicketAmount());

        ticket2 = new Ticket(ticketInfo2.getDepartDate(), ticketInfo2.getDepartStation(),
                ticketInfo2.getArriveAt(), ticketInfo2.getSeatType(),
                ticketInfo2.getTicketAmount());
    }

    @Test(description = "User can filter 'Manage ticket' table with both Arrive station and Depart date")
    public void FTTC702() {
        navigateToRailWay();

        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTickets(6, ticket1);

        myTicketPage.changePage();
        myTicketPage.selectDepartStationInFilter(ticket1.getDepartStation());
        myTicketPage.selectDepartDateInFilter(ticket1.getDepartDate());
        myTicketPage.submitFilter();

        Assert.assertTrue(myTicketPage.isManageTicketShowCorrectTicket(ticket1.getDepartDate(), ticket1.getDepartStation()));

        pageBase.logOut();
    }

    @Test (description = "User can book ticket with known price")
    public void FTTC703() {
        navigateToRailWay();

        loginPage.changePage();
        loginPage.login(validUser);

        ticketPricePage.changePage();
        ticketPricePage.checkPriceTicket(ticket2.getDepartStation(), ticket2.getArriveAt());

        String price = ticketPricePage.getSeatPrice(ticket2.getSeatType());
        ticketPricePage.bookTicketInTicketPrice(ticket2.getSeatType());


        bookTicketPage.selectAmount(ticket2.getTicketAmount());
        bookTicketPage.clickBookticketButton();

        Assert.assertTrue(bookTicketSuccessfulPage.checkTicketPrice(price, ticket2.getTicketAmount()));

        pageBase.logOut();
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
