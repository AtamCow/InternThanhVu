package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.Location;
import enums.SeatType;
import models.Ticket;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;

public class CancelTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private MyTicketPage myTicketPage;

    private Ticket ticketInfo;
    private User validUser;

    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";

    @BeforeClass
    public void setUp() throws IOException {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        myTicketPage = new MyTicketPage(getDriver());

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);

        validUser = new User(cf.validLogEmail, cf.logPassword);
    }

    @Test //User can cancel a ticket
    public void TC16() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(validUser);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        myTicketPage.changePage();
        myTicketPage.cancelTicket(ticketInfo);
        Assert.assertTrue(myTicketPage.checkTicketCanceled(ticketInfo), "Ticket is not deleted");
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
