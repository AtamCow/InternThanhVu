package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class CreateAccountTC extends BaseSetup {
    private ConfigTest cf;

    private HomePage homePage;
    private RegisterPage registerPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        homePage = new HomePage(getDriver());
        registerPage = new RegisterPage(getDriver());
        pageBase = new PageBase(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
    }

    @Test //User can't create account with an already in-use email
    public void TC07() {
        cf.navigateRailway();

        // Login
        registerPage.changePage();
        registerPage.register(cf.validLogEmail, cf.logPassword, cf.rePid);

        registerPage.checkErrorMessageAbove(cf.messageErrorRegisterWithInvalidEmail);
    }

    @Test //User can't create account while password and PID fields are empty
    public void TC08() {
        cf.navigateRailway();

        // Login
        registerPage.changePage();
        registerPage.register(cf.validLogEmail, "", "");

        registerPage.checkErrorMessageAbove(cf.messageErrorRegister);
        registerPage.checkErrorMessageNextto(cf.messageInvalidPasswordLenghtRegister, "password");
        registerPage.checkErrorMessageNextto(cf.messageInvalidPidlenghtRegister, "pid");
    }

    @Test //User create and activate account
    public void TC09() {
        cf.navigateRailway();

        homePage.register();
        pageBase.checkCurrentPage("Register");
        registerPage.register(cf.reEmail, cf.logPassword, cf.rePid);
        registerPage.checkTitle(cf.messageTitleAfterRegister);

        cf.navigateQuerrilMail();
        int originNumTabs = pageBase.getTabNumber();
        guerrillamailPage.confirmAccount(cf.reIdEmail, cf.reHostEmail);
        guerrillamailPage.confirmEmailWithTd();
        int newNumTabs = pageBase.getTabNumber();

        Assert.assertEquals(newNumTabs, originNumTabs + 1);
        pageBase.changeToTab(1);

        registerPage.checkConfirmed(cf.messageConfirmedRegister);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
