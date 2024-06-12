package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FAQPage {
    private WebDriver driver;

    public FAQPage(WebDriver driver) {
        this.driver = driver;
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='FAQ']");
        driver.findElement(tabNameChange).click();
    }
}
