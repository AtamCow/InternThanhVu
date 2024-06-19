package chapter3;

import base.BaseSetup;
import config.ConfigTest;
import enums.DepartDate;
import enums.Location;
import enums.SeatType;
import enums.TicketAmount;
import models.Ticket;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;


public class Exchap3 extends BaseSetup {
    private ConfigTest cf;
    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private PageBase pageBase;
    private RegisterPage registerPage;
    private GuerrillamailPage guerrillamailPage;

    private Ticket ticketInfo;

    private User registerUser;

    String departDate = DepartDate.DAY_25.getDate();
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = TicketAmount.ONE.getAmount();

    @BeforeClass
    public void setUp() {
        super.setup();
        cf = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        registerPage = new RegisterPage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        pageBase = new PageBase(getDriver());

        ticketInfo = new Ticket(departDate, departStation, arriveStation, seatType, ticketAmount);

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);

    }

    @Test
    public void TC1() {
        cf.navigateRailway();

        registerPage.changePage();

        registerPage.register(registerUser);

        cf.navigateQuerrilMail();

        // Confirm account
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.confirmEmailWithTd();


        pageBase.changeToTab(1);

        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        bookTicketPage.changePage();

        bookTicketPage.bookTicket(ticketInfo);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}