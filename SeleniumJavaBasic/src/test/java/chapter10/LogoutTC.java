package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class LogoutTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private FAQPage faqPage;
    private PageBase pageBase;

    private User validUser;

    @BeforeClass
    public void setUp() {
        super.setup();
        cf = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        faqPage = new FAQPage(getDriver());
        pageBase = new PageBase(getDriver());

        validUser = new User(cf.validLogEmail, cf.logPassword);
    }

    @Test //User is redirected to Home page after logging out
    public void TC06() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(validUser);

        faqPage.changePage();
        pageBase.logOut();

        pageBase.waitMiliSec(1000);
        pageBase.checkCurrentPage("Home");
        pageBase.checkTabExisted("Log out");
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
