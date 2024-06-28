package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

public class PageBase {
    private WebDriver driver;

    public PageBase(WebDriver driver){
        this.driver = driver;
    }

    private String tabPath = "//div[@id='menu']//a//span[text()='%s']";

    public void scrollView(Object element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public int getTabNumber() {
        Set<String> windowHandles = driver.getWindowHandles();
        int tabNumbers = windowHandles.size();

        return tabNumbers;
    }

    public void changeToTab (int tabNum) {
        // Get list tab
        Set<String> originnalWindowHandles = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<String>(originnalWindowHandles);

        // Change tab
        driver.switchTo().window(tabs.get(tabNum));
    }

    public void waitMiliSec (long miliSec) {
        try {
            Thread.sleep(miliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public WebElement wait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public boolean checkTabExisted(String tabName) {
        int tabExist = driver.findElements(By.xpath(String.format(tabPath, tabName))).size();
        if (tabExist == 0)
            return false;
        else
            return true;
    }

    public boolean checkCurrentPage(String currentPage) {
        WebElement ulElement = driver.findElement(By.xpath("//div[@id='menu']//ul"));
        java.util.List<WebElement> listItems = ulElement.findElements(By.xpath("//div[@id='menu']//ul//li"));

        boolean check = false;

        for (WebElement listItem : listItems) {
            // Kiểm tra xem phần tử có class "selected" không
            if (listItem.getAttribute("class").contains("selected")) {
                // Kiểm tra văn bản của phần tử <li>
                if (listItem.getText().toLowerCase().contains(currentPage)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public String multiplyTwoStringNumber (String num1, String num2) {
        int intNum1 = Integer.parseInt(num1);
        int intNum2 = Integer.parseInt(num2);
        int result = intNum1 * intNum2;

        return String.valueOf(result);

    }

    public void logOut() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Log out']");
        driver.findElement(tabNameChange).click();
    }
}
