package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class BaseSetup {
    private static WebDriver driver;// = new ChromeDriver();

    public static void setup() {
        String browser = System.getProperty("browser", "chrome");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

    public static void initDriver() {
        String browserType = BaseConfig.getProperty("browser").toLowerCase();
        if (browserType.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            BaseSetup.driver = new ChromeDriver();
        } else if (browserType.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            BaseSetup.driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        driver.manage().window().maximize();
    }

    public static void navigateToRailWay() {
        String railwayUrl = BaseConfig.getProperty("railwayUrl");
        BaseSetup.driver.get(railwayUrl);
        // railway = WebDriverConfig.driver.getWindowHandle();
    }

    public static void navigateToMailPage() {
        String mailUrl = BaseConfig.getProperty("tempmail.url");
        BaseSetup.driver.get(mailUrl);
        //email = WebDriverConfig.driver.getWindowHandle();
    }

    public void tearDown() {
        if(driver != null)
            driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
