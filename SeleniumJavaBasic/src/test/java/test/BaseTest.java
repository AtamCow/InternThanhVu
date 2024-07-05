package test;

import base.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class BaseTest {
    protected static WebDriver driver;


//    @BeforeClass
//    @Parameters("browser")
//    public void setUp(String browser) throws IOException {
//        driver = HubSetting.createDriver(browser);
//    }

    @Parameters({"browser", "jsonFilePath"})
    @BeforeClass
    public void setUp(String browser, String jsonFilePath) throws MalformedURLException {
        HubSetting.createDriver(browser, jsonFilePath);
        driver = HubSetting.getDriver();
    }

    public static void navigateToRailWay() {
        String railwayUrl = BaseConfig.getProperty("railwayUrl");
        driver.get(railwayUrl);
        // railway = WebDriverConfig.driver.getWindowHandle();
    }

    @AfterClass
    public void tearDown() {
        WebDriverManager.quitDriver();
    }

}
