package pages;

import models.Ticket;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookTicketSuccessfulPage {
    private WebDriver driver;

    public BookTicketSuccessfulPage(WebDriver driver) {
        this.driver = driver;

    }
    private String h1MessageXpath = "//div[@id='content']//h1";
    private String tdInformation = "//div[@id='content']//tbody//tr[2]//td[%s]";

    private String tdDepartStation = "1";
    private String tdArriveStation = "2";
    private String tdseatType = "3";
    private String tdDepartDate = "4";
    private String tdAmount = "7";

    public void checkTicketInfo (Ticket ticket) {
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

        int checkValue = 0;

        if (!ticket.getDepartStation().equals(departStationValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",departStationValue, ticket.getDepartStation()));
        }
        if (!ticket.getArriveAt().equals(arriveStationValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",arriveStationValue, ticket.getArriveAt()));
        }
        if (!ticket.getSeatType().equals(seatTypeValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",seatTypeValue, ticket.getSeatType()));
        }
        if (!checkedDate.equals(departDateValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",departDateValue, checkedDate));
        }
        if (!ticket.getTicketAmount().equals(amountValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",amountValue, ticket.getTicketAmount()));
        }
        Assert.assertEquals(0, checkValue);
    }

    public void checkTicketInfo (String departDateExpected, String departstationExpected, String arrivestationExpected, String seattypeExpected, String amountExpected) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkedDate = LocalDate.now().plusDays(Integer.parseInt(departDateExpected)).format(formatter);

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

        int checkValue = 0;

        if (!departstationExpected.equals(departStationValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",departStationValue, departstationExpected));
        }
        if (!arrivestationExpected.equals(arriveStationValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",arriveStationValue, arrivestationExpected));
        }
        if (!seattypeExpected.equals(seatTypeValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",seatTypeValue, seattypeExpected));
        }
        if (!checkedDate.equals(departDateValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",departDateValue, checkedDate));
        }
        if (!amountExpected.equals(amountValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",amountValue, amountExpected));
        }
        Assert.assertEquals(0, checkValue);
    }

    public void checkBookedSuccessfulMessage(String expectedMessage) {
        By h1Message = By.xpath(h1MessageXpath);
        String successMessage = driver.findElement(h1Message).getText();
        Assert.assertEquals(expectedMessage, successMessage);
    }
}
