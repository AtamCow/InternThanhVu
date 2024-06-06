package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimetablePage {
    private WebDriver driver;

    private String pricePath = "//tbody//tr[td[2][text()='%s'] and td[3][text()='%s']]//a";


    public TimetablePage(WebDriver driverChrome) {
        this.driver = driverChrome;
    }

    public void checkPrice(String departstation, String arrivestation) {
        By checkPriceLink = By.xpath(String.format(pricePath, departstation, arrivestation));
        driver.findElement(checkPriceLink).click();
    }

}
