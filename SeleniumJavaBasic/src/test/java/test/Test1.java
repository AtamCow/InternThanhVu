package test;

import base.BaseConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


public class Test1 extends BaseTest {


    @Test
    public void TC3() {
//        WebDriver driver = new EdgeDriver();
        String railwayUrl = BaseConfig.getProperty("railwayUrl");
        driver.get(railwayUrl);
//        navigateToMailPage();
        driver.quit();

    }
    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
