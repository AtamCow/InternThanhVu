package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private PageBase pageBase;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//div[@id='content']//input[@value='login']");
    private By messageErrorLoginForm = By.xpath("//div[@id='content']//p[@class='message error LoginForm']");

    public void login(String email, String password) {
        WebElement loginEmail = driver.findElement(usernameField);
        WebElement loginPassword = driver.findElement(passwordField);
        WebElement loginBtn = driver.findElement(loginButton);

        loginEmail.sendKeys(email);

        pageBase.scrollView(loginPassword);
        loginPassword.sendKeys(password);

        pageBase.scrollView(loginBtn);
        loginBtn.click();
    }

    public void checkErrorMessage(String expectedMessage) {
        String recordMessage = driver.findElement(messageErrorLoginForm).getText();

        Assert.assertEquals(expectedMessage, recordMessage);
    }

    public void checkLoginYet() {
        int check = 0;
        boolean loginTabExist = pageBase.checkTabExisted("Home");
        boolean logoutTabExist = pageBase.checkTabExisted("Log out");
        if (loginTabExist == true && logoutTabExist == false)
            check += 1;

        Assert.assertEquals(0, check);
    }

    public void checkExistMessage(String expectedMessage) {
        By textContains = By.xpath(String.format("//*[contains(text(), '%s')]", expectedMessage));

        int exitsText = driver.findElements(textContains).size();

        Assert.assertEquals(1, exitsText);
        if (exitsText == 1)
            System.out.println("Could not find message");
    }

}
