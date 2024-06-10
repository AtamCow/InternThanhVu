package pages;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;

public class BookTicketPage {
    private WebDriver driver;
    private  PageBase pageBase;

    public BookTicketPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    private String departDateSelect = "//select[@name='Date']//option[@value='%s']";
    private String departFromSelect = "//select[@name='DepartStation']//option[text()='%s']";
    private String arriverAtSelect = "//select[@name='ArriveStation']//option[text()='%s']";
    private String seatTypeSelect = "//select[@name='SeatType']//option[text()='%s']";
    private String ticketamountSelect = "//select[@name='TicketAmount']//option[text()='%s']";
    private String bookTicketButton = "//div[@id='content']//form//input[@value='Book ticket']";

    public void bookTicket(String departdate, String departfrom, String arriverat, String seattype, String ticketamount) {
        By dateSelect = By.xpath(String.format(departDateSelect, departdate));
        By ticketDepartStationSelect = By.xpath(String.format(departFromSelect, departfrom));
        By ticketArriveStationSelect = By.xpath(String.format(arriverAtSelect, arriverat));
        By ticketSeatTypeSelect = By.xpath(String.format(seatTypeSelect, seattype));
        By ticketAmountSelect = By.xpath(String.format(ticketamountSelect, ticketamount));
        By bookTicketInput = By.xpath(bookTicketButton);

        driver.findElement(dateSelect).click();
        driver.findElement(ticketDepartStationSelect).click();

        pageBase.waitMiliSec(2000);

        driver.findElement(ticketArriveStationSelect).click();
        driver.findElement(ticketSeatTypeSelect).click();

        WebElement amountTicketOption = driver.findElement(ticketAmountSelect);
        pageBase.scrollView(amountTicketOption);
        amountTicketOption.click();

        WebElement bookTicketButton = driver.findElement(bookTicketInput);
        pageBase.scrollView(bookTicketButton);
        bookTicketButton.click();
    }
}
