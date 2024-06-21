package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pages.*;

public class CreateAccountTC extends BaseSetup {
    private ConfigTest cf;

    private HomePage homePage;
    private RegisterPage registerPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;

    private User registerUser;
    private User registerBlankPassPidUser;

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());
        pageBase = new PageBase(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());

        registerUser = new User(cf.reEmail, cf.logPassword, cf.rePid);
        registerBlankPassPidUser = new User(cf.reEmail, "", "");
    }

    @Test //User can't create account with an already in-use email
    public void TC07() {
        cf.navigateRailway();

        registerPage.changePage();
        registerPage.register(registerUser);

        Assert.assertTrue(registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail));
    }

    @Test //User can't create account while password and PID fields are empty
    public void TC08() {
        cf.navigateRailway();

        registerPage.changePage();
        registerPage.register(registerBlankPassPidUser);

        Assert.assertTrue(registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail));

        Assert.assertTrue(registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password"),"Error message display out of the field");
        Assert.assertTrue(registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid"),"Error message display out of the field");

    }

    @Test //User create and activate account
    public void TC09() {
        cf.navigateRailway();

        Assert.assertNotNull(homePage.register());
        Assert.assertEquals(homePage.register().getText(), "create an account");
        homePage.register().click();


        pageBase.checkCurrentPage("Register");
        registerPage.register(registerUser);
        Assert.assertTrue(registerPage.checkTitle(cf.messageTitleAfterRegister), "The title message is not shown");

        cf.navigateQuerrilMail();
        int originNumTabs = pageBase.getTabNumber();
        guerrillamailPage.confirmAccount(cf.reIdEmail, cf.reHostEmail);
        guerrillamailPage.confirmEmailWithTd();
        int newNumTabs = pageBase.getTabNumber();

        Assert.assertEquals(newNumTabs, originNumTabs + 1);
        pageBase.changeToTab(1);

        Assert.assertTrue(registerPage.checkConfirmed(cf.messageConfirmedRegister), "The confirm message is not shown");
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
