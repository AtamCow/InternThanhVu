package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private String messageWelcome = "//div[@id='content']//h1";

    public void checkWelcomeMessage (String expectedMessage) {
        By welcomeMessageText = By.xpath(messageWelcome);

        String recordMessage = driver.findElement(welcomeMessageText).getText();

        Assert.assertEquals(expectedMessage, recordMessage);
    }
}
