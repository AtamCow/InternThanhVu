package helper;

import static helper.DriverUtils.driver;

public class BrowserUtils {
    public void navigateTo(String url) {
        Logger.log("Navigate to:" + url);
        driver.get().get(url);
    }

}
