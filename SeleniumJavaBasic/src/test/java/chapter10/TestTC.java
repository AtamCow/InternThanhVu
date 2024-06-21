package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.User;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class TestTC extends BaseSetup {
    private ConfigTest cf;
    private LoginPage loginPage;
    private FAQPage faqPage;
    private HomePage homePage;
    private RegisterPage registerPage;

    private User user;

    private User validUser;
    private User blankEmailUser;
    private User blankPassUser;
    private User invalidPassUser;
    private User registerUser;
    private User inactiveUser;

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        faqPage = new FAQPage(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());

        validUser = new User(cf.validLogEmail, cf.logPassword);
        blankEmailUser = new User("", cf.logPassword);
        blankPassUser = new User(cf.validLogEmail, "");
        invalidPassUser = new User(cf.validLogEmail, cf.invalidPassword);

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);
        inactiveUser = new User(cf.reEmail, cf.logPassword);
    }


    @Test //User can log into Railway with valid username and password
    public void TC01() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(validUser);

        Assert.assertEquals(String.format("Welcome %s", cf.validLogEmail), homePage.checkWelcomeMessage());
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
