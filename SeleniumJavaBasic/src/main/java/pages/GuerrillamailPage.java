package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GuerrillamailPage {
    private WebDriver driver;
    private PageBase pageBase;

    public GuerrillamailPage(WebDriver driver) {
        this.driver = driver;
        this.pageBase = new PageBase(driver);
    }

    private String idEmailButton = "//span[@id='inbox-id']";
    private String idEmailInput = "//span[@id='inbox-id']//input";
    private String idEmailSaveButton = "//span[@id='inbox-id']//button[@class='save button small']";
    private String hostEmailOption = "//select[@id='gm-host-select']/option[@value='%s']";
    private String tdconfirmEmail = "//tbody[@id='email_list']//tr[td[contains(text(), 'Please confirm your account')]][1]";
    private String tdResetEmail = "//tbody[@id='email_list']//td[contains(text(), 'Please reset your password')]";
    private String confirmLink = "//div[@id='display_email']//div[@class='email_body']//a";

    public void confirmAccount(String idmail, String hostmail) {
        // Create id Email
        By idEmailButtonByXpath = By.xpath(idEmailButton);
        By idEmailInputByXpath = By.xpath(idEmailInput);
        By idEmailSaveButtonByXpath = By.xpath(idEmailSaveButton);
        By hostEmailOptionByXpath = By.xpath(String.format(hostEmailOption, hostmail));

        driver.findElement(idEmailButtonByXpath).click();
        driver.findElement(idEmailInputByXpath).sendKeys(idmail);
        driver.findElement(idEmailSaveButtonByXpath).click();
        driver.findElement(hostEmailOptionByXpath).click();

        // Wait
        pageBase.waitMiliSec(12000);
    }

    public void confirmEmailWithTd() {
        // Select 1st email in tbody
        WebElement clickEmail = driver.findElement(By.xpath(tdconfirmEmail));
        clickEmail.click();

        // Wait for confirm link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement linkConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(confirmLink)));

        linkConfirm.click();
    }

    public void resetEmailWithTd() {
        // Select 1st email in tbody
        WebElement clickEmail = driver.findElement(By.xpath(tdResetEmail));
        clickEmail.click();

        // Wait for confirm link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement linkConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(confirmLink)));

        linkConfirm.click();
    }
}
