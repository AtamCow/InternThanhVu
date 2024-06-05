package Chap5;

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


public class ExChap5 {
    public static final String idEmail = "ffsv";
    public static final String hostEmail = "grr.la";
    public static final String logEmail = idEmail + "@" + hostEmail;
    public static final String logPassword = "1234qwer";
    public static final String rePid = "12341234";
    public static String tabName;

    public static final WebDriver driverChrome = new ChromeDriver();

    public static void main(String[] args) {
        //driverChrome.manage().window().maximize();
        driverChrome.get("http://saferailway.somee.com/");

        // Login với account mới
        changeTab("Login");
        login(logEmail, logPassword);

        // Chuyển qua Timetable tab
        changeTab("Timetable");

        // Book tickets
        // Chọn thông tin vé
        String departDate = "6/12/2024";
        String departFrom = "Nha Trang";
        String arriveAt = "Phan Thiết";
        String seatType = "Hard seat";
        String ticketAmount = "2";

        //driverChrome.findElement(By.xpath("//tbody//tr[td[2][text()='Sài Gòn'] and td[3][text()='Đà Nẵng']]//a")).click();
        bookTicketByTimeTable(departDate, departFrom, arriveAt, seatType, ticketAmount);
        //driverChrome.quit();
    }

    public static void  changeTab (String tabname) {
        tabName = tabname;
        driverChrome.findElement(By.linkText(tabName)).click();
    }

    public static void login (String email, String password) {
        // Nhập thông tin
        WebElement loginEmail = driverChrome.findElement(By.id("username"));
        WebElement loginPassword = driverChrome.findElement(By.id("password"));
        WebElement loginButton = driverChrome.findElement(By.xpath("//div[@id='content']//input[@value='login']"));

        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginButton.click();
    }

    public static void bookTicket(String departdate, String departfrom, String arriveat, String seattype, String ticketamount) {
        // Đặt vé
        driverChrome.findElement(By.xpath("//select[@name='Date']//option[text()='" +departdate+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='DepartStation']//option[text()='" +departfrom+ "']")).click();

        // Đợi 2s
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driverChrome.findElement(By.xpath("//select[@name='ArriveStation']//option[text()='" +arriveat+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='SeatType']//option[text()='" +seattype+ "']")).click();
        driverChrome.findElement(By.xpath("//select[@name='TicketAmount']//option[text()='" +ticketamount+ "']")).click();

        WebElement bookTicketButton = driverChrome.findElement(By.xpath("//div[@id='content']//form//input[@value='Book ticket']"));

        JavascriptExecutor js = (JavascriptExecutor) driverChrome;
        js.executeScript("arguments[0].scrollIntoView(true);", bookTicketButton);

        bookTicketButton.click();

    }

    public static void bookTicketByTimeTable(String departdate, String departfrom, String arriveat, String seattype, String ticketamount){
        driverChrome.findElement(By.xpath(String.format("//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a", departfrom, arriveat))).click();
        driverChrome.findElement(By.xpath("//div[@id='content']//table[@class='NoBorder']//tr[td[2][text()='"+seattype+"']]//td[a]")).click();


    }
}