package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ChangePasswordPage {
    private WebDriver driver;

    public ChangePasswordPage(WebDriver driver){
        this.driver = driver;
    }

    private String passwordChangeFormText = "//div[@id='content']//form//legend[text()='Password Change Form']";
    private String passwordResetTokenpath = "//div[@id='content']//form//input[@id='resetToken']";
    private String newPasswordInputpath = "//div[@id='content']//form//input[@id='newPassword']";
    private String confirmPasswordInputpath = "//div[@id='content']//form//input[@id='%s']";
    private String resetButton = "//div[@id='content']//form//input[@type='submit']";
    private String warningSamePassMessage = "The new password cannot be the same with the current password";
    private String errorMessageLabelNextto = "//div[@id='content']//label[@for='%s' and @class='validation-error']";

    By errorMessageAbove = By.xpath("//div[@id='content']//p[@class='message error']");
    By passwordChangeForm = By.xpath("//div[@id='content']//form");


    public void checkPasswordChangeFormShown() {
        List<WebElement> changeForm = driver.findElements(By.xpath(passwordChangeFormText));
        int changeFormNumbers = changeForm.size();
        Assert.assertEquals(1, changeFormNumbers);

        WebElement inputElement = driver.findElement(By.xpath(passwordResetTokenpath));
        String inputValue = inputElement.getAttribute("value");

        Assert.assertNotNull(inputValue,"Reset Password token do not display");
    }

    public void enterNewPassword(String newPassword, String confirmPassword) {
        WebElement newPass = driver.findElement(By.xpath(newPasswordInputpath));
        WebElement confirmPass = driver.findElement(By.xpath(String.format(confirmPasswordInputpath, "confirmPassword")));
        WebElement button = driver.findElement(By.xpath(resetButton));

        newPass.sendKeys(newPassword);
        confirmPass.sendKeys(confirmPassword);
        button.click();
    }

    public void checkWarningSamePassMessageShown() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//*[contains(text(),'%s')]", warningSamePassMessage))));

        Assert.assertNotNull(message);

        String actualMessage = message.getText();

        Assert.assertEquals(actualMessage, warningSamePassMessage, "Message did not appear as expected");
    }

    public void checkErrorMessageAbove(String expectedMessage) {
        WebElement errorMessageText = driver.findElement(errorMessageAbove);
        String recordMessage = errorMessageText.getText();
        Assert.assertEquals(expectedMessage, recordMessage);

        WebElement form = driver.findElement(passwordChangeForm);

        int errorMessageY = errorMessageText.getLocation().getY();
        int formY = form.getLocation().getY();

        Assert.assertTrue(errorMessageY < formY);
    }

    public void checkErrorMessageNextto(String expectedMessage, String position) {
        By errorMessageNextto = By.xpath(String.format(errorMessageLabelNextto, position));
        WebElement errorMessageText = driver.findElement(errorMessageNextto);
        String recordMessage = errorMessageText.getText();
        Assert.assertEquals(expectedMessage, recordMessage);

        By confirmPassInput = By.xpath(String.format(confirmPasswordInputpath, position));
        WebElement input = driver.findElement(confirmPassInput);

        int errorMessageY = errorMessageText.getLocation().getY();
        int inputY = input.getLocation().getY();
        int check = Math.abs(errorMessageY - inputY);
        Assert.assertTrue("Error message display out of the field", check < 10);
    }
}
