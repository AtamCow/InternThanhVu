package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class LogoutTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private FAQPage faqPage;
    private PageBase pageBase;

    @Before
    public void setUp() {
        super.setup();
        cf = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        faqPage = new FAQPage(getDriver());
        pageBase = new PageBase(getDriver());
    }

    @Test //User is redirected to Home page after logging out
    public void TC06() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(cf.validLogEmail, cf.logPassword);

        faqPage.changePage();
        pageBase.logOut();

        pageBase.waitMiliSec(1000);
        pageBase.checkCurrentPage("Home");
        pageBase.checkTabExisted("Log out");
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
