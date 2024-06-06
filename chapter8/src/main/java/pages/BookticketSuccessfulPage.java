package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.assertEquals;


public class BookticketSuccessfulPage {
    private WebDriver driver;
    public BookticketSuccessfulPage(WebDriver driverChrome) {
        this.driver = driverChrome;
    }

    private String titlePath = "//div[@id='content']//h1";
    private String departStationInfoPath = "//div[@id='content']//tbody//tr[2]//td[%s]";
    private String arriveStationInfoPath = "//div[@id='content']//tbody//tr[2]//td[%s]";
    private String seatTypeInfoPath = "//div[@id='content']//tbody//tr[2]//td[%s]";
    private String departDateInfoPath = "//div[@id='content']//tbody//tr[2]//td[%s]";
    private String amountInfoPath = "//div[@id='content']//tbody//tr[2]//td[%s]";

    private String expectedTitle = "Ticket booked successfully!";

    private String tdDepartStation = "1";
    private String tdArriveStation = "2";
    private String tdSeatType = "3";
    private String tdDepartDate = "4";
    private String tdAmount = "7";


    public void checkTicketInformations(String checkedDepartStation, String checkedArriveStation, String checkedSeatType, String checkedDepartDate, String checkedAmount) {
        By titleText = By.xpath(titlePath);
        By deparStationText = By.xpath(String.format(departStationInfoPath, tdDepartStation));
        By arriveStationText = By.xpath(String.format(arriveStationInfoPath, tdArriveStation));
        By seatTypeText = By.xpath(String.format(seatTypeInfoPath, tdSeatType));
        By deparDateText = By.xpath(String.format(departDateInfoPath, tdDepartDate));
        By amountText = By.xpath(String.format(amountInfoPath, tdAmount));

        String title = driver.findElement(titleText).getText();
        String departStation = driver.findElement(deparStationText).getText();
        String arriveStation = driver.findElement(arriveStationText).getText();
        String seatType = driver.findElement(seatTypeText).getText();
        String departDate = driver.findElement(deparDateText).getText();
        String amount = driver.findElement(amountText).getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkedDate = LocalDate.now().plusDays(Integer.parseInt(checkedDepartDate)).format(formatter);

        int checkValue = 0;
        if (title != expectedTitle) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", title, expectedTitle));
        }
        if (departStation != checkedDepartStation) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", departStation, checkedDepartStation));
        }if (arriveStation != checkedArriveStation) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", arriveStation, checkedArriveStation));
        }if (seatType != checkedSeatType) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", seatType, checkedSeatType));
        }if (departDate != checkedDate) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", departDate, checkedDate));
        }if (amount != checkedAmount) {
            checkValue += 1;
            System.out.println(String.format("Record value: %s, Expected value: %s", amount, checkedAmount));
        }

        assertEquals(0, checkValue);
    }
}
