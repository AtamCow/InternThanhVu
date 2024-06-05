package chapter3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;


public class Exchap3 {
    public static final String idEmail = "gfgfdbfd";
    public static final String hostEmail = "grr.la";
    public static final String logEmail = idEmail + "@" + hostEmail;
    public static final String logPassword = "1234qwer";
    public static final String rePid = "12341234";

    public static final WebDriver driverChrome = new ChromeDriver();

    // Ticket informations
    public static String departDate = "7";
    public static String departFrom = "Sài Gòn";
    public static String arriveAt = "Đà Nẵng";
    public static String seatType = "Soft seat";
    public static String ticketAmount = "2";
    public static String tabName;

    public static void main(String[] args) {
        //driverChrome.manage().window().maximize();
        driverChrome.get("http://saferailway.somee.com/");

        // Regíter 1 account
        changeTab("Register");
        register(logEmail, logPassword, rePid);

        // Confirm account
        confirmAccount(idEmail, hostEmail);

        // Get list tab
        Set<String> windowHandles = driverChrome.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<String>(windowHandles);

        // Change tab
        driverChrome.switchTo().window(tabs.get(1));

        // Login with new account
        changeTab("Login");
        login(logEmail, logPassword);

        // Book tickets
        changeTab("Book ticket");
        bookTicket(departDate, departFrom, arriveAt, seatType, ticketAmount);

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