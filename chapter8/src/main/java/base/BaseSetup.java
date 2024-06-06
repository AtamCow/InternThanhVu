package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BaseSetup {
    protected WebDriver driverChrome;

    public void setup() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driverChrome.manage().window().maximize();
    }

    public void tearDown() {
        if(driverChrome != null)
            driverChrome.quit();
    }

    public WebDriver getDriverChrome() {
        return driverChrome;
    }

}
