package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public void checkTicketInfo (String expectedMessage, String departstationExpected, String arrivestationExpected, String seattypeExpected, String departdateExpected, String amountExpected) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkedAmountDate = LocalDate.now().plusDays(Integer.parseInt(departdateExpected)).format(formatter);

        By h1Message = By.xpath(h1MessageXpath);
        By departStation = By.xpath(String.format(tdInformation, tdDepartStation));
        By arriveStation = By.xpath(String.format(tdInformation,tdArriveStation));
        By seatType = By.xpath(String.format(tdInformation,tdseatType));
        By departDate = By.xpath(String.format(tdInformation,tdDepartDate));
        By amount = By.xpath(String.format(tdInformation,tdAmount));

        String successMessage = driver.findElement(h1Message).getText();
        String departStationValue = driver.findElement(departStation).getText();
        String arriveStationValue = driver.findElement(arriveStation).getText();
        String seatTypeValue = driver.findElement(seatType).getText();
        String departDateValue = driver.findElement(departDate).getText();
        String amountValue = driver.findElement(amount).getText();

        int checkValue = 0;

        if (!expectedMessage.equals(successMessage)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",successMessage, expectedMessage));
        }
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
        if (!checkedAmountDate.equals(departDateValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",departDateValue, checkedAmountDate));
        }
        if (!amountExpected.equals(amountValue)) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s",amountValue, amountExpected));
        }
        Assert.assertEquals(0, checkValue);
    }
}
