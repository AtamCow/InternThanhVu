package pages;

import models.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.*;

public class BookTicketPage {
    private WebDriver driver;
    private  PageBase pageBase;

    public BookTicketPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Book ticket']");
        driver.findElement(tabNameChange).click();
    }

    private String departDateSelect = "//select[@name='Date']//option[@value='%s']";
    private String departFromSelect = "//select[@name='DepartStation']//option[text()='%s']";
    private String arriverAtSelect = "//select[@name='ArriveStation']//option[text()='%s']";
    private String seatTypeSelect = "//select[@name='SeatType']//option[text()='%s']";
    private String ticketamountSelect = "//select[@name='TicketAmount']//option[@value='%s']";
    private String bookTicketButton = "//div[@id='content']//form//input[@value='Book ticket']";

    public void bookTicket(Ticket ticket) {
        selectDepartDate(ticket.getDepartDate());
        selectDepartStation(ticket.getDepartStation());

        pageBase.waitMiliSec(2000);

        selectArriveStation(ticket.getArriveAt());
        selectSeatType(ticket.getSeatType());
        selectAmount(ticket.getTicketAmount());

        clickBookticketButton();
    }

    public void selectDepartDate(String departDate){
        By dateSelect = By.xpath(String.format(departDateSelect, departDate));
        driver.findElement(dateSelect).click();
    }

    public void selectDepartStation(String departStation) {
        By ticketDepartStationSelect = By.xpath(String.format(departFromSelect, departStation));
        driver.findElement(ticketDepartStationSelect).click();

    }

    public void selectArriveStation(String arriveStation) {
        By ticketArriveStationSelect = By.xpath(String.format(arriverAtSelect, arriveStation));
        driver.findElement(ticketArriveStationSelect).click();

    }

    public void selectSeatType(String seattypeSelect) {
        By ticketSeatTypeSelect = By.xpath(String.format(seatTypeSelect, seattypeSelect));
        driver.findElement(ticketSeatTypeSelect).click();
    }

    public void selectAmount(String amount) {
        By ticketAmountSelect = By.xpath(String.format(ticketamountSelect, amount));
        WebElement amountTicketOption = driver.findElement(ticketAmountSelect);
        pageBase.scrollView(amountTicketOption);
        amountTicketOption.click();

    }

    public void clickBookticketButton(){
        By bookTicketInput = By.xpath(bookTicketButton);
        WebElement bookTicketButton = driver.findElement(bookTicketInput);
        pageBase.scrollView(bookTicketButton);
        bookTicketButton.click();
    }

    public void checkInfoFromTimetable(String departStation, String arriverAt) {
        WebElement departsation = driver.findElement(By.xpath("//select[@name='DepartStation']"));
        Select selectDepartStation = new Select(departsation);
        String departStationSelect = selectDepartStation.getFirstSelectedOption().getText();

        WebElement arrivestation = driver.findElement(By.xpath("//select[@name='ArriveStation']"));
        Select selectArriveStation = new Select(arrivestation);
        String arriveStationSelect = selectArriveStation.getFirstSelectedOption().getText();

        Assert.assertEquals(departStation, departStationSelect);
        Assert.assertEquals(arriverAt, arriveStationSelect);
    }

}
