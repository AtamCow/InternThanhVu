package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

public class DriverUtils {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public void initDriver(String browser) throws Exception {
        switch (browser) {
            case "chrome": {
                driver.set(new ChromeDriver());
                break;
            }
            case "firefox": {
                driver.set(new FirefoxDriver());
                break;
            }
            default :
                throw new Exception(browser + "is not suported");
        }
    }
}
