package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private String createAccountLink = "//div[@id='content']//a";
    private String creataAccountText = "create an account";
    private String messageWelcome = "//div[@id='content']//h1";

    public void checkWelcomeMessage (String expectedMessage) {
        By welcomeMessageText = By.xpath(messageWelcome);

        String recordMessage = driver.findElement(welcomeMessageText).getText();

        Assert.assertEquals(expectedMessage, recordMessage);
    }

    public void register() {
        WebElement createAccount = driver.findElement(By.xpath(createAccountLink));
        Assert.assertNotNull(createAccount);
        Assert.assertEquals(createAccount.getText(), creataAccountText);
        createAccount.click();
    }
}
