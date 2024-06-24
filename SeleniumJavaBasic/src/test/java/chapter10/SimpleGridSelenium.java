package chapter10;

import config.ConfigTest;
import models.Ticket;
import models.User;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;
import base.*;

import java.net.MalformedURLException;
import java.net.URL;

public class SimpleGridSelenium extends BaseSetup {
    private WebDriver driver;

    private ConfigTest cf;



    @BeforeClass
    public void setUp() throws MalformedURLException {
        super.setup();
        cf = new ConfigTest(getDriver());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.ANY);

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

    }

    @Test
    public void testGoogleSearch() {

        cf.navigateRailway();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
