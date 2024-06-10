package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TicketPricePage {
    private WebDriver driver;

    public TicketPricePage(WebDriver driver) {
        this.driver = driver;
    }

    private String ticketWithSeat = "//table[@class='NoBorder']//tr[td[2][text()='%s']]//td[a]";

    public void bookTicketInTicketPrice(String seatType) {
        By bookTicketLink = By.xpath(String.format(ticketWithSeat, seatType));
        driver.findElement(bookTicketLink).click();
    }
}
