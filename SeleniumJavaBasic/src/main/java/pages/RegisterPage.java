package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;

public class RegisterPage {
    private WebDriver driver;
    private PageBase pageBase;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    By registerForm = By.xpath("//div[@id='content']//form[@id='RegisterForm']");
    By errorMessage = By.xpath("//div[@id='content']//p[@class='message error']");

    public void register(String mail, String pass, String pid) {
        // Enter informations
        By registerEmailById = By.id("email");
        By registerPasswordById = By.id("password");
        By registerConfirmPassById = By.id("confirmPassword");
        By registerPidById = By.id("pid");
        By registerButtonByXpath = By.xpath("//form[@id='RegisterForm']//input[@value='Register']");

        WebElement registerEmail = driver.findElement(registerEmailById);
        WebElement registerPassword = driver.findElement(registerPasswordById);
        WebElement registerConfirmPassword = driver.findElement(registerConfirmPassById);
        WebElement registerPid = driver.findElement(registerPidById);
        WebElement registerButton = driver.findElement(registerButtonByXpath);

        registerEmail.sendKeys(mail);
        registerPassword.sendKeys(pass);
        registerConfirmPassword.sendKeys(pass);
        registerPid.sendKeys(pid);

        pageBase.scrollView(registerButton);
        registerButton.click();
    }

    public void checkErrorMessage(String expectedMessage) {
        WebElement errorMessageText = driver.findElement(errorMessage);
        String recordMessage = errorMessageText.getText();
        System.out.println(recordMessage);
        Assert.assertEquals(expectedMessage, recordMessage);

        WebElement form = driver.findElement(registerForm);

        int errorMessageY = errorMessageText.getLocation().getY();
        int formY = form.getLocation().getY();

        Assert.assertTrue(errorMessageY < formY);
    }
}
