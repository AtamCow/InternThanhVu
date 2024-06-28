package chapter3;

import base.BaseSetup;
import config.ConfigTest;
import enums.Location;
import enums.SeatType;
import io.github.bonigarcia.wdm.WebDriverManager;
import models.Ticket;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utils.listeners.ReportListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners(ReportListener.class)

public class Exchap3 extends BaseSetup {
    private ConfigTest cf;

    private Properties properties = new Properties();

    private LoginPage loginPage;
    private BookTicketPage bookTicketPage;
    private PageBase pageBase;
    private RegisterPage registerPage;
    private GuerrillamailPage guerrillamailPage;

    private Ticket ticketInfo;

    private User registerUser;

    String departDate = "25";
    String departStation = Location.DA_NANG.getLocation();
    String arriveStation = Location.SAI_GON.getLocation();
    String seatType = SeatType.SOFT_SEAT_AC.getSeatType();
    String ticketAmount = "1";

    @BeforeClass
    public void setUp() throws IOException {
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
//        cf.navigateRailway();
        navigateToRailWay();
//
//        registerPage.changePage();
//
//        registerPage.register(registerUser);
//
//        cf.navigateQuerrilMail();
//
//        // Confirm account
//        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
//        guerrillamailPage.confirmEmailWithTd();
//
//
//        pageBase.changeToTab(1);
//
//        loginPage.changePage();
//        loginPage.login(cf.validLogEmail, cf.logPassword);
//
//        bookTicketPage.changePage();
//
//        bookTicketPage.bookTicket(ticketInfo);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}