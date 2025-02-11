package pages;

import models.Ticket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookTicketSuccessfulPage {
    private WebDriver driver;
    private TicketPricePage ticketPricePage;
    private PageBase pageBase;

    public BookTicketSuccessfulPage(WebDriver driver) {
        this.driver = driver;
        this.ticketPricePage = new TicketPricePage(driver);
        this.pageBase = new PageBase(driver);
    }
    private String h1MessageXpath = "//div[@id='content']//h1";
    private String tdInformation = "//div[@id='content']//tbody//tr[2]//td[%s]";

    private String tdDepartStation = "1";
    private String tdArriveStation = "2";
    private String tdseatType = "3";
    private String tdDepartDate = "4";
    private String tdAmount = "7";
    private String tdTotalPrice = "8";



    public boolean checkTicketInfo (Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkedDate = LocalDate.now().plusDays(Integer.parseInt(ticket.getDepartDate())).format(formatter);

        By departStation = By.xpath(String.format(tdInformation, tdDepartStation));
        By arriveStation = By.xpath(String.format(tdInformation,tdArriveStation));
        By seatType = By.xpath(String.format(tdInformation,tdseatType));
        By departDate = By.xpath(String.format(tdInformation,tdDepartDate));
        By amount = By.xpath(String.format(tdInformation,tdAmount));

        String departStationValue = driver.findElement(departStation).getText();
        String arriveStationValue = driver.findElement(arriveStation).getText();
        String seatTypeValue = driver.findElement(seatType).getText();
        String departDateValue = driver.findElement(departDate).getText();
        String amountValue = driver.findElement(amount).getText();

        boolean checkValue = true;

        if (!ticket.getDepartStation().equals(departStationValue)) {
            checkValue = false;
            System.out.println(String.format("Record value: %s, Expected value: %s",departStationValue, ticket.getDepartStation()));
        }
        if (!ticket.getArriveAt().equals(arriveStationValue)) {
            checkValue = false;
            System.out.println(String.format("Record value: %s, Expected value: %s",arriveStationValue, ticket.getArriveAt()));
        }
        if (!ticket.getSeatType().equals(seatTypeValue)) {
            checkValue = false;
            System.out.println(String.format("Record value: %s, Expected value: %s",seatTypeValue, ticket.getSeatType()));
        }
        if (!checkedDate.equals(departDateValue)) {
            checkValue = false;
            System.out.println(String.format("Record value: %s, Expected value: %s",departDateValue, checkedDate));
        }
        if (!ticket.getTicketAmount().equals(amountValue)) {
            checkValue = false;
            System.out.println(String.format("Record value: %s, Expected value: %s",amountValue, ticket.getTicketAmount()));
        }
        return checkValue;
    }

    public String checkBookedSuccessfulMessage() {
        By h1Message = By.xpath(h1MessageXpath);
        return driver.findElement(h1Message).getText();
    }

    public boolean checkTicketPrice(String priceTotal, String ticketAmount) {
        String priceCheck = pageBase.multiplyTwoStringNumber(priceTotal, ticketAmount);

        By price = By.xpath(String.format(tdInformation,tdTotalPrice));
        String totalPrice = driver.findElement(price).getText();

        System.out.println(totalPrice);
        System.out.println(priceCheck);
        return totalPrice.equals(priceCheck);
    }
}
