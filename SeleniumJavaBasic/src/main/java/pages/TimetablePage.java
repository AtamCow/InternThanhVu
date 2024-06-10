package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TimetablePage {
    private WebDriver driver;

    public TimetablePage(WebDriver driver) {
        this.driver = driver;
    }

    private String checkTicketPrice = "//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a";

    public void checkPrice(String departFrom, String arriveAt) {
        By checkPriceLink = By.xpath(String.format(checkTicketPrice, departFrom, arriveAt));
        driver.findElement(checkPriceLink).click();
    }
}
