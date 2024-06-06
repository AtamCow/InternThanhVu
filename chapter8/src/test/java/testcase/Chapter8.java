package testcase;

import base.BaseSetup;
import org.junit.*;
import org.openqa.selenium.By;
import pages.*;


public class Chapter8 extends BaseSetup {
    private ConfigPage configPage;
    private LoginPage loginPage;
    private TimetablePage timetablePage;
    private TicketpricePage ticketPricePage;
    private BookticketPage bookTicketPage;
    private BookticketSuccessfulPage bookticketSuccessfulPage;

    // Login information
    public static final String idEmail = "tienthanhvulcu";
    public static final String hostEmail = "gmail.com";
    public static final String logEmail = idEmail + "@" + hostEmail;
    public static final String logPassword = "1234qwer";

    // Ticket informations
    public static String departDate = "7";
    public static String departFrom = "Sài Gòn";
    public static String arriveAt = "Đà Nẵng";
    public static String seatType = "Soft seat";
    public static String ticketAmount = "1";

    @Before
    public void setUp() {
        super.setup();
        loginPage = new LoginPage(getDriverChrome());
        timetablePage = new TimetablePage(getDriverChrome());
        bookTicketPage = new BookticketPage(getDriverChrome());
        ticketPricePage = new TicketpricePage(getDriverChrome());
        bookticketSuccessfulPage = new BookticketSuccessfulPage(getDriverChrome());
        configPage = new ConfigPage(getDriverChrome());
    }

    @Test
    public void TC01() {
        getDriverChrome().get("http://saferailway.somee.com/");

        configPage.changeTab("Login");
        loginPage.login(logEmail, logPassword);

        getDriverChrome().findElement(By.linkText("Timetable")).click();

        timetablePage.checkPrice(departFrom, arriveAt);
        ticketPricePage.bookTicketInTicketPrice(seatType);
        bookTicketPage.bookTicket(departDate, ticketAmount);
        bookticketSuccessfulPage.checkTicketInformations(departFrom, arriveAt, seatType, departDate, ticketAmount);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}