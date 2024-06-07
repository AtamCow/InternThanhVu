package chapter3;

import base.BaseSetup;
import chapter8.ConfigTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;


public class Exchap3 extends BaseSetup {
    private static final ConfigTest cf = new ConfigTest();

    private LoginPage loginPage;
    private HomePage homePage;
    private RegisterPage registerPage;
    private TimetablePage  timetablePage;
    private TicketPricePage ticketPricePage;
    private BookTicketPage bookTicketPage;
    private BookTicketSuccessfulPage bookTicketSuccessfulPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;

    @Before
    public void setUp() {
        super.setup();
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());
        timetablePage = new TimetablePage(getDriver());
        ticketPricePage = new TicketPricePage(getDriver());
        bookTicketPage = new BookTicketPage(getDriver());
        bookTicketSuccessfulPage = new BookTicketSuccessfulPage(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        pageBase = new PageBase(getDriver());
    }

    @Test
    public void TC1() {
        getDriver().get("http://saferailway.somee.com/");

        // Login
        pageBase.changeTab("Register");
        registerPage.register(cf.logEmail, cf.logPassword, cf.rePid);

        getDriver().navigate().to("https://www.guerrillamail.com/inbox");

        // Confirm account
        confirmAccount(cf.idEmail, cf.hostEmail);

        pageBase.changeToTab(1);

        pageBase.changeTab("Login");


    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    public void main(String[] args) {
        // Login with new account
        changeTab("Login");
        login(cf.logEmail, cf.logPassword);

        // Book tickets
        changeTab("Book ticket");
        bookTicket(cf.departDate, cf.departFrom, cf.arriveAt, cf.seatType, cf.ticketAmount);

        driverChrome.quit();
    }

    public static void changeTab(String tabname) {
        tabName = tabname;
        driverChrome.findElement(By.linkText(tabName)).click();
    }

    public static void register(String mail, String pass, String pid) {
        // Enter informations
        By registerEmailById = By.id("email");
        By registerPasswordById = By.id("password");
        By registerConfirmPassById = By.id("confirmPassword");
        By registerPidById = By.id("pid");
        By registerButtonByXpath = By.xpath("//form[@id='RegisterForm']//input[@value='Register']");

        WebElement registerEmail = driverChrome.findElement(registerEmailById);
        WebElement registerPassword = driverChrome.findElement(registerPasswordById);
        WebElement registerConfirmPassword = driverChrome.findElement(registerConfirmPassById);
        WebElement registerPid = driverChrome.findElement(registerPidById);
        WebElement registerButton = driverChrome.findElement(registerButtonByXpath);

        registerEmail.sendKeys(mail);
        registerPassword.sendKeys(pass);
        registerConfirmPassword.sendKeys(pass);
        registerPid.sendKeys(pid);

        scrollView(registerButton);
        registerButton.click();
    }

    public static void confirmAccount(String idmail, String hostmail) {
        // Navigate to guerrillamail
        driverChrome.navigate().to("https://www.guerrillamail.com/inbox");

        // Create id Email
        By idEmailButtonByXpath = By.xpath("//span[@id='inbox-id']");
        WebElement idEmailButton = driverChrome.findElement(idEmailButtonByXpath);
        idEmailButton.click();

        By idEmailInputByXpath = By.xpath("//span[@id='inbox-id']//input");
        By idEmailSaveButtonByXpath = By.xpath("//span[@id='inbox-id']//button[@class='save button small']");
        By hostEmailOptionByXpath = By.xpath(String.format("//select[@id='gm-host-select']/option[@value='%s']", hostmail));

        WebElement idEmailInput = driverChrome.findElement(idEmailInputByXpath);
        WebElement idEmailSaveButton = driverChrome.findElement(idEmailSaveButtonByXpath);
        WebElement hostEmailOption = driverChrome.findElement(hostEmailOptionByXpath);

        idEmailInput.sendKeys(idmail);
        idEmailSaveButton.click();
        hostEmailOption.click();

        // Wait
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Select 1st email in tbody
        WebElement firstRow = driverChrome.findElement(By.xpath("//*[@id='email_list']//tr[1]"));

        firstRow.findElement(By.xpath("//*[@id=\"email_list\"]//tr[1]//td[@class='td2']")).click();

        // Wait for confirm link
        WebDriverWait wait = new WebDriverWait(driverChrome, Duration.ofSeconds(5));
        WebElement linkConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='display_email']//div[@class='email_body']//a")));

        driverChrome.findElement(By.xpath("//div[@id='display_email']//div[@class='email_body']//a")).click();
    }

    public static void login(String email, String password) {
        // Enter informations
        WebElement loginEmail = driverChrome.findElement(By.id("username"));
        WebElement loginPassword = driverChrome.findElement(By.id("password"));
        WebElement loginButton = driverChrome.findElement(By.xpath("//div[@id='content']//input[@value='login']"));

        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginButton.click();
    }

    public static void bookTicket(String departdate, String departfrom, String arriverat, String seattype, String ticketamount) {
        // Book ticket
        By dateSelect = By.xpath(String.format("//select[@name='Date']//option[@value='%s']", departdate));
        By ticketDepartStationSelect = By.xpath(String.format("//select[@name='DepartStation']//option[text()='%s']", departfrom));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        By ticketArriveStationSelect = By.xpath(String.format("//select[@name='ArriveStation']//option[text()='%s']", arriverat));
        By ticketAmountSelect = By.xpath(String.format("//select[@name='TicketAmount']//option[text()='%s']", ticketamount));
        By bookTicketInput = By.xpath("//div[@id='content']//form//input[@value='Book ticket']");

        driverChrome.findElement(dateSelect).click();
        driverChrome.findElement(ticketDepartStationSelect).click();
        driverChrome.findElement(ticketArriveStationSelect).click();


        WebElement amountTicketOption = driverChrome.findElement(ticketAmountSelect);
        scrollView(amountTicketOption);
        amountTicketOption.click();

        WebElement bookTicketButton = driverChrome.findElement(bookTicketInput);
        scrollView(bookTicketButton);
        bookTicketButton.click();
    }

    public static void scrollView(Object element) {
        JavascriptExecutor js = (JavascriptExecutor) driverChrome;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}