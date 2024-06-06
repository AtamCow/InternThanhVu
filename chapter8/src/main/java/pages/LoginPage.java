package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//div[@id='content']//input[@value='login']");

    public LoginPage(WebDriver driverChrome) {
        this.driver = driverChrome;
    }

    public void login(String email, String password) {
        WebElement loginEmail = driver.findElement(usernameField);
        WebElement loginPassword = driver.findElement(passwordField);
        WebElement loginBtn = driver.findElement(loginButton);

        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginBtn.click();
    }



}
