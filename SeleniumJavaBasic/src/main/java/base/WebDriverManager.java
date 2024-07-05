package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class WebDriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver setDriver(String browser) throws IOException {
        WebDriver webDriver;
//        String browser = BaseConfig.getProperty("browser").toLowerCase();
        if (browser.equals("chrome")) {
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
            webDriver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser type: " + browser);
        }
        driver.set(webDriver);
        return webDriver;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

}
