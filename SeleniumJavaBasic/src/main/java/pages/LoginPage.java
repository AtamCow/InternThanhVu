package pages;

import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private PageBase pageBase;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Login']");
        driver.findElement(tabNameChange).click();
    }

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//div[@id='content']//input[@value='login']");
    private By messageErrorLoginForm = By.xpath("//div[@id='content']//p[@class='message error LoginForm']");

    public void login(User user) {
        WebElement loginEmail = driver.findElement(usernameField);
        WebElement loginPassword = driver.findElement(passwordField);
        WebElement loginBtn = driver.findElement(loginButton);

        loginEmail.sendKeys(user.getEmail());

        pageBase.scrollView(loginPassword);
        loginPassword.sendKeys(user.getPassword());

        pageBase.scrollView(loginBtn);
        loginBtn.click();
    }

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

    public String checkErrorMessage() {
        String recordMessage = driver.findElement(messageErrorLoginForm).getText();
        return recordMessage;
    }

    public boolean checkLoginYet() {
        boolean check = true;
        boolean loginTabExist = pageBase.checkTabExisted("Login");
        boolean logoutTabExist = pageBase.checkTabExisted("Log out");
        if (loginTabExist == true && logoutTabExist == false)
            check = false;

        return check;
    }

    public boolean checkExistMessage(String expectedMessage) {
        By textContains = By.xpath(String.format("//*[contains(text(), '%s')]", expectedMessage));
        int exitsText = driver.findElements(textContains).size();

        if (exitsText == 0)
            return false;
        else
            return true;
    }
}
