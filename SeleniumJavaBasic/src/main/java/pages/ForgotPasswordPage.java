package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage {
    private WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private String forgotPasswordPageLink = "//div[@id='content']//a[text()='Forgot Password page']";
    private String emailInputpath = "//div[@id='content']//input[@id='email']";
    private String sendButtonpath = "//div[@id='content']//input[@type='submit']";

    public void changeToForgotPasswordPage() {
        By forgotPass = By.xpath(forgotPasswordPageLink);
        driver.findElement(forgotPass).click();
    }

    public void sendMail(String email) {
        WebElement emailInput = driver.findElement(By.xpath(emailInputpath));
        WebElement sendButton = driver.findElement(By.xpath(sendButtonpath));

        emailInput.sendKeys(email);
        sendButton.click();
    }
}
