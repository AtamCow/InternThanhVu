package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class BaseSetup {
    protected WebDriver driverChrome;

    protected void setup() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    protected void tearDown() {
        if(driverChrome != null)
            driverChrome.quit();
    }

    protected WebDriver getDriverChrome() {
        return driverChrome;
    }

}
