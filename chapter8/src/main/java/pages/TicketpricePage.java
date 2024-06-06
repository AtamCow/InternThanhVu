package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TicketpricePage {
    private WebDriver driver;

    public TicketpricePage(WebDriver driverChrome) {
        this.driver = driverChrome;
    }

    private String bookTicketPath = "//table[@class='NoBorder']//tr[td[2][text()='%s']]//td[a]";

    public void bookTicketInTicketPrice (String seattype) {
        By bookTicketLink = By.xpath(String.format(bookTicketPath, seattype));
        driver.findElement(bookTicketLink).click();
    }
}
