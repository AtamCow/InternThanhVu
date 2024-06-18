package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.User;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class LoginTC extends BaseSetup {
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

        homePage.checkWelcomeMessage(cf.validLogEmail);
    }

    @Test //User cannot login with blank "Username" textbox
    public void TC02() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        user.setEmail("");

        loginPage.login(validUser);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
        loginPage.checkLoginYet();
    }

    @Test //User cannot log into Railway with invalid password
    public void TC03() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();
        loginPage.login(blankPassUser);

        loginPage.checkErrorMessage(cf.messageErrorLoginform);
    }

    @Test //System shows message when user enters wrong password many times
    public void TC04() {
        cf.navigateRailway();

        // Login
        loginPage.changePage();

        for (int i = 0; i < 4; i++) {
            loginPage.login(invalidPassUser);
            loginPage.checkErrorMessage(cf.messageInvalidLoginform);
        }
        loginPage.checkLoginYet();
        loginPage.checkExistMessage(cf.messageWarningLoginform);
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

        loginPage.checkLoginYet();
        loginPage.checkErrorMessage(cf.messageInvalidLoginform);
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
