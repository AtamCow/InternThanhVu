package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import enums.DepartDate;
import enums.Location;
import enums.SeatType;
import enums.TicketAmount;
import models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class CancelTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private MyTicketPage myTicketPage;

    private Ticket ticketInfo;

    String departDate = DepartDate.DAY_25.getDate();
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = TicketAmount.ONE.getAmount();

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        myTicketPage = new MyTicketPage(getDriver());

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);

    }

    @Test //User can cancel a ticket
    public void TC16() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        myTicketPage.changePage();
        myTicketPage.cancelTicket(departStation, arriveStation, seatType, departDate, ticketAmount);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
