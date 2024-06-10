package config;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfigTest {
    private WebDriver driver;

    public  ConfigTest(WebDriver driver) {
        this.driver = driver;
    }

    // Login information
    public String idEmail = "vcasdfe";
    public String hostEmail = "grr.la";
    public String validLogEmail = idEmail + "@" + hostEmail;
    public String invalidEmail = "vfsdfrd@cijd.dfe";
    public String logPassword = "1234qwer";
    public String invalidPassword = "12341234";
    public String rePid = "12341234";

    // Ticket informations
    public String departDate = "7";
    public String departFrom = "Sài Gòn";
    public String arriveAt = "Đà Nẵng";
    public String seatType = "Soft seat";
    public String ticketAmount = "1";

    public String loginSuccessfullMessage = "Ticket booked successfully!";
    public String wellcomMessage = "Welcome to Safe Railway";
    public String messageErrorLoginform = "There was a problem with your login and/or errors exist in your form.";
    public String messageInvalidLoginform = "Invalid username or password. Please try again.";
    public String messageWarningLoginform = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
    public String messageErrorRegister = "This email address is already in use.";

    public void navigateRailway() {
        driver.get("http://saferailway.somee.com/");
    }

    public void navigateQuerrilMail() {
        driver.navigate().to("https://www.guerrillamail.com/inbox");
    }


}
