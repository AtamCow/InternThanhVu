package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class HubSetting {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String HUB_URL = "http://localhost:4444/wd/hub"; // URL của hub Selenium Grid

    public static WebDriver getDriver() {
        return driver.get();
    }
//    private static final int HUB_PORT = 4444;
//
//    public static void main(String[] args) throws IOException {
//        startHub();
//    }

    public static void createDriver(String browser, String filePath) throws MalformedURLException {
//        WebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        JSONArray nodes = JSONReader.readJSON(filePath);

//        switch (browser.toLowerCase()) {
//            case "chrome":
//                capabilities.setBrowserName("chrome");
//                break;
//            case "firefox":
//                capabilities.setBrowserName("firefox");
//                break;
//            // Add more cases for other browsers as needed
//            default:
//                capabilities.setBrowserName("chrome");
//        }
//        for (int i = 0; i < nodes.length(); i++) {
//            JSONObject node = nodes.getJSONObject(i);
//            if (node.getString("browserName").equalsIgnoreCase(browser)) {
//                capabilities.setBrowserName(node.getString("browserName"));
//                String hubUrl = node.getString("hub");
//                driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
//                break;
//            }
//        }

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            for (int i = 0; i < nodes.length(); i++) {
                JSONObject node = nodes.getJSONObject(i);
                if (node.getString("browserName").equalsIgnoreCase(browser)) {
                    chromeOptions.merge(capabilities);
                    chromeOptions.setCapability("browserName", "chrome");
                    driver.set(new RemoteWebDriver(new URL(HUB_URL), chromeOptions));
                    break;
                }
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            for (int i = 0; i < nodes.length(); i++) {
                JSONObject node = nodes.getJSONObject(i);
                if (node.getString("browserName").equalsIgnoreCase(browser)) {
                    firefoxOptions.merge(capabilities);
                    firefoxOptions.setCapability("browserName", "firefox");
                    driver.set(new RemoteWebDriver(new URL(HUB_URL), firefoxOptions));
                    break;
                }
            }
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            for (int i = 0; i < nodes.length(); i++) {
                JSONObject node = nodes.getJSONObject(i);
                if (node.getString("browserName").equalsIgnoreCase(browser)) {
                    edgeOptions.merge(capabilities);
                    edgeOptions.setCapability("browserName", "edge");
                    driver.set(new RemoteWebDriver(new URL(HUB_URL), edgeOptions));
                    break;
                }
            }
        }

        // Kết nối tới Hub
//        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//        return driver;
    }

//    public static void startHub() throws IOException {
//        // Khởi tạo Chrome Driver Service
//        ChromeDriverService service = new ChromeDriverService.Builder()
//                .usingDriverExecutable(new File("path/to/chromedriver.exe"))
//                .usingAnyFreePort()
//                .build();
//        service.start();
//
//        // Tạo Desired Capabilities cho Chrome
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        ChromeOptions options = new ChromeOptions();
//        options.merge(capabilities);
//
//        // Khởi động RemoteWebDriver để chạy Hub
//        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(), options);
//
//        // Chạy Hub trên cổng mặc định 4444
//        driver.executeScript("java -jar selenium-server-standalone-4.x.x.jar hub");
//
//        // Hiển thị thông báo khi Hub được khởi động thành công
//        System.out.println("Selenium Hub is running on port " + HUB_PORT);
//    }
}
