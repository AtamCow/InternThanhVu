package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Set;

public class PageBase {
    private WebDriver driver;

    public PageBase(WebDriver driver){
        this.driver = driver;
    }

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
    }

    public boolean checkTabExisted(String tabName) {
        int tabExist = driver.findElements(By.linkText(tabName)).size();
        if (tabExist == 0)
            return false;
        else
            return true;
    }

    public void checkCurrentPage(String currentPage) {
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
        Assert.assertFalse(check);
    }

    public void logOut() {
        By tabNameChange = By.xpath("//div[@id='menu']//span[text()='Log out']");
        driver.findElement(tabNameChange).click();
    }


}
