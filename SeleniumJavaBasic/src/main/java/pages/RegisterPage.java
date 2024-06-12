package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.Driver;

public class RegisterPage {
    private WebDriver driver;
    private PageBase pageBase;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    private String errorMessageLabelNextto = "//div[@id='content']//label[@for='%s' and @class='validation-error']";
    private String inputTextfield = "//div[@id='content']//input[@id='%s']";
    private String titleText = "//div[@id='content']//h1";

    By registerForm = By.xpath("//div[@id='content']//form[@id='RegisterForm']");
    By errorMessageAbove = By.xpath("//div[@id='content']//p[@class='message error']");

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

    public void checkErrorMessageAbove(String expectedMessage) {
        WebElement errorMessageText = driver.findElement(errorMessageAbove);
        String recordMessage = errorMessageText.getText();
        System.out.println(recordMessage);
        Assert.assertEquals(expectedMessage, recordMessage);

        WebElement form = driver.findElement(registerForm);

        int errorMessageY = errorMessageText.getLocation().getY();
        int formY = form.getLocation().getY();

        Assert.assertTrue(errorMessageY < formY);
    }

    public void checkErrorMessageNextto(String expectedMessage, String position) {
        By errorMessageNextto = By.xpath(String.format(errorMessageLabelNextto, position));

        WebElement errorMessageText = driver.findElement(errorMessageNextto);
        String recordMessage = errorMessageText.getText();
        System.out.println(recordMessage);
        Assert.assertEquals(expectedMessage, recordMessage);

        By registerInput = By.xpath(String.format(inputTextfield, position));
        WebElement input = driver.findElement(registerForm);

        int errorMessageY = errorMessageText.getLocation().getY();
        int inputY = input.getLocation().getY();
        Assert.assertEquals(errorMessageY, inputY);
    }

    public void checkTitle(String expectedMessage) {
        String message = driver.findElement(By.xpath(titleText)).getText();

        Assert.assertEquals(expectedMessage, message);
    }
}
