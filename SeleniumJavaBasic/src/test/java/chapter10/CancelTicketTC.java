package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.Ticket;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class CancelTicketTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private MyTicketPage myTicketPage;

    private Ticket ticketInfo;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        myTicketPage = new MyTicketPage(getDriver());

        ticketInfo = new Ticket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmount);

    }

    @Test //User can cancel a ticket
    public void TC16() {
        cf.navigateRailway();
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();
        bookTicketPage.bookTicket(ticketInfo);

        myTicketPage.changePage();
        myTicketPage.cancelTicket(cf.departFrom, cf.arriveAt, cf.seatType, cf.departDate, cf.ticketAmount);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
