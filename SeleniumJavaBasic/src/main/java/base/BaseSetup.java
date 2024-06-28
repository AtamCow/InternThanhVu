package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseSetup {
    private static WebDriver driver;// = new ChromeDriver();
    private static Properties properties = new Properties();


    public static void setup() throws IOException {
//        String browser = System.getProperty("browser", "chrome");
//        if (browser.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        }
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

//    public static WebDriver getDriver() {
//        return driver;
//    }

    public static WebDriver getDriver() throws IOException {
//        FileInputStream configStream = new FileInputStream("configs.properties");
//        properties.load(configStream);
//        String browser = properties.getProperty("browser");

//        String browser = BaseConfig.getProperty("browser").toLowerCase();
//        if (browser.equals("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        } else if (browser.equals("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        } else {
//            throw new IllegalArgumentException("Unsupported browser type: " + browser);
//        }
        return driver;
    }

}
