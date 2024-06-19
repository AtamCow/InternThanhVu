package pages;

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

    public String checkWelcomeMessage() {
        By welcomeMessageText = By.xpath(messageWelcome);

        String recordMessage = driver.findElement(welcomeMessageText).getText();
        return recordMessage;
    }

    public WebElement register() {
        WebElement createAccount = driver.findElement(By.xpath(createAccountLink));
        return createAccount;
    }
}
