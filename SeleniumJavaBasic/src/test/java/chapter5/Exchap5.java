package chapter5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;


public class Exchap5 {
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
    public static String ticketAmount = "2";

    public static String tabName;

    public static final WebDriver driverChrome = new ChromeDriver();

    public static void main(String[] args) {
        driverChrome.manage().window().maximize();
        driverChrome.get("http://saferailway.somee.com/");

        // Login
        changeTab("Login");
        login(logEmail, logPassword);

        // Change to Timetable tab
        changeTab("Timetable");

        // Book tickets
        checkPriceInTimeTable(departFrom, arriveAt);
        bookTicketInTicketPrice(seatType);
        bookTicket(departDate, ticketAmount);

        driverChrome.quit();
    }

    public static void changeTab(String tabname) {
        tabName = tabname;
        driverChrome.findElement(By.linkText(tabName)).click();
    }

    public static void login(String email, String password) {
        WebElement loginEmail = driverChrome.findElement(By.id("username"));
        WebElement loginPassword = driverChrome.findElement(By.id("password"));
        WebElement loginButton = driverChrome.findElement(By.xpath("//div[@id='content']//input[@value='login']"));

        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginButton.click();
    }

    public static void checkPriceInTimeTable(String departfrom, String arriveat) {
        By checkPriceLink = By.xpath(String.format("//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a", departfrom, arriveat));
        driverChrome.findElement(checkPriceLink).click();
    }

    public static void bookTicketInTicketPrice(String seattype) {
        By bookTicketLink = By.xpath(String.format("//table[@class='NoBorder']//tr[td[2][text()='%s']]//td[a]", seattype));
        driverChrome.findElement(bookTicketLink).click();
    }

    public static void bookTicket(String departdate, String ticketamount) {
        By dateSelect = By.xpath(String.format("//select[@name='Date']//option[@value='%s']", departdate));
        By ticketAmountSelect = By.xpath(String.format("//select[@name='TicketAmount']//option[text()='%s']", ticketamount));
        By bookTicketInput = By.xpath("//div[@id='content']//form//input[@value='Book ticket']");

        driverChrome.findElement(dateSelect).click();

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