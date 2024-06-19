package pages;

import models.User;
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

    public void changePage() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Register']");
        driver.findElement(tabNameChange).click();
    }

    private String errorMessageLabelNextto = "//div[@id='content']//label[@for='%s' and @class='validation-error']";
    private String inputTextfield = "//div[@id='content']//input[@id='%s']";
    private String titleText = "//div[@id='content']//h1";
    private String confirmedMessage = "//div[@id='content']//p";

    By registerForm = By.xpath("//div[@id='content']//form[@id='RegisterForm']");
    By errorMessageAbove = By.xpath("//div[@id='content']//p[@class='message error']");

    public void register(User user) {
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

        registerEmail.sendKeys(user.getEmail());
        registerPassword.sendKeys(user.getPassword());
        registerConfirmPassword.sendKeys(user.getPassword());
        registerPid.sendKeys(user.getPid());

        pageBase.scrollView(registerButton);
        registerButton.click();
    }

    public boolean checkErrorMessageAbove(String expectedMessage) {
        WebElement errorMessageText = driver.findElement(errorMessageAbove);
        String recordMessage = errorMessageText.getText();
        boolean message = expectedMessage.equals(recordMessage);

        WebElement form = driver.findElement(registerForm);
        int errorMessageY = errorMessageText.getLocation().getY();
        int formY = form.getLocation().getY();
        boolean position = errorMessageY < formY;

        return message && position;
    }

    public boolean checkErrorMessageNextto(String expectedMessage, String position) {
        By errorMessageNextto = By.xpath(String.format(errorMessageLabelNextto, position));
        WebElement errorMessageText = driver.findElement(errorMessageNextto);
        String recordMessage = errorMessageText.getText();
        boolean message = expectedMessage.equals(recordMessage);

        By registerInput = By.xpath(String.format(inputTextfield, position));
        WebElement input = driver.findElement(registerInput);

        int errorMessageY = errorMessageText.getLocation().getY();
        int inputY = input.getLocation().getY();
        int check = Math.abs(errorMessageY - inputY);
        boolean pos = check < 10;

        return message && pos;
    }

    public boolean checkTitle(String expectedMessage) {
        String message = driver.findElement(By.xpath(titleText)).getText();
        return expectedMessage.equals(message);
    }

    public boolean checkConfirmed(String expectedMessage) {
        String message = driver.findElement(By.xpath(confirmedMessage)).getText();
        return expectedMessage.equals(message);
    }
}
