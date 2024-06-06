package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ConfigPage {
    private WebDriver driver;

    public ConfigPage(WebDriver driverChrome) {
        this.driver = driverChrome;
    }

    public void scrollView(Object element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void changeTab(String tabname) {
        String tabName = tabname;
        driver.findElement(By.linkText(tabName)).click();
    }
}
