package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BaseSetup {
    private WebDriver driver;

    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

    public void tearDown() {
        if(driver != null)
            driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

}
