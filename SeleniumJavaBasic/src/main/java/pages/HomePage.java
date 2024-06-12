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
    private String messageWelcome = "//div[@class='account']//strong";

    public void checkWelcomeMessage (String email) {
        By welcomeMessageText = By.xpath(messageWelcome);

        String recordMessage = driver.findElement(welcomeMessageText).getText();

        Assert.assertEquals(String.format("Welcome %s", email), recordMessage);
    }

    public void register() {
        WebElement createAccount = driver.findElement(By.xpath(createAccountLink));
        Assert.assertNotNull(createAccount);
        Assert.assertEquals(createAccount.getText(), creataAccountText);
        createAccount.click();
    }
}
