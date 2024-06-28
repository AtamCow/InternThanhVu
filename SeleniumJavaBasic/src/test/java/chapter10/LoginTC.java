package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.User;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;

public class LoginTC extends BaseSetup {
    private ConfigTest cf;
    private PageBase pageBase;
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
    public void setUp() throws IOException {
        super.setup();
        cf  = new ConfigTest(getDriver());
        pageBase = new PageBase(getDriver());
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
        pageBase.logOut();
    }

    @Test //User cannot login with blank "Username" textbox
    public void TC02() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(blankEmailUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
    }

    @Test //User cannot log into Railway with invalid password
    public void TC03() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(invalidPassUser);

        Assert.assertEquals(cf.messageErrorLoginform, loginPage.checkErrorMessage());
    }

    @Test //System shows message when user enters wrong password many times
    public void TC04() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();

        for (int i = 0; i < 4; i++) {
            loginPage.login(invalidPassUser);
            Assert.assertEquals(cf.messageInvalidLoginform, loginPage.checkErrorMessage());
        }
        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
        Assert.assertTrue(loginPage.checkExistMessage(cf.messageWarningLoginform), "Could not find message");

    }

    @Test //User can't login with an account hasn't been activated
    public void TC05() {
        cf.navigateRailway();

        //Register
        registerPage.changePage();
        registerPage.register(registerUser);

        // Login
        loginPage.changePage();
        loginPage.login(inactiveUser);

        Assert.assertTrue(loginPage.checkLoginYet(), "Not Login yet");
        Assert.assertEquals(cf.messageInvalidLoginform, loginPage.checkErrorMessage());
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
