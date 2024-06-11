package config;

import org.openqa.selenium.WebDriver;

public class ConfigTest {
    private WebDriver driver;

    public  ConfigTest(WebDriver driver) {
        this.driver = driver;
    }

    // Login information
    public String idEmail = "vcasdfe";
    public String hostEmail = "grr.la";
    public String validLogEmail = idEmail + "@" + hostEmail;
    public String reIdEmail = "asfgrdcvsdw";
    public String reHostEmail = "grr.la";
    public String reEmail = reIdEmail + "@" + reHostEmail;
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

    // Home page
    public String wellcomMessage = "Welcome to Safe Railway";

    // Login
    public String messageErrorLoginform = "There was a problem with your login and/or errors exist in your form.";
    public String messageInvalidLoginform = "Invalid username or password. Please try again.";
    public String messageWarningLoginform = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

    // Register
    public String messageErrorRegisterWithInvalidEmail = "This email address is already in use.";
    public String messageErrorRegister = "There're errors in the form. Please correct the errors and try again.";
    public String messageInvalidPasswordLenghtRegister = "Invalid password length.";
    public String messageInvalidPidlenghtRegister = "Invalid ID length.";
    public String messageTitleAfterRegister = "Thank you for registering your account";

    public void navigateRailway() {
        driver.get("http://saferailway.somee.com/");
    }

    public void navigateQuerrilMail() {
        driver.navigate().to("https://www.guerrillamail.com/inbox");
    }


}
