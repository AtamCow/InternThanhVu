package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

public class ResetPasswordTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;
    private ChangePasswordPage changePasswordPage;
    private ForgotPasswordPage forgotPasswordPage;

    @BeforeClass
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        pageBase = new PageBase(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        changePasswordPage = new ChangePasswordPage(getDriver());
        forgotPasswordPage = new ForgotPasswordPage(getDriver());
    }

    @Test(description = "User create and activate account")
    public void TC10() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        Assert.assertTrue(changePasswordPage.checkPasswordChangeFormShown(), "Reset Password token do not display");
        changePasswordPage.enterNewPassword(cf.logPassword, cf.logPassword);
        Assert.assertTrue(changePasswordPage.checkWarningSamePassMessageShown(), "Message did not appear as expected");
        pageBase.logOut();
    }

    @Test(description = "Reset password shows error if the new password and confirm password doesn't match")
    public void TC11() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        Assert.assertTrue(changePasswordPage.checkPasswordChangeFormShown(), "Reset Password token do not display");

        changePasswordPage.enterNewPassword(cf.logPassword, cf.invalidPassword);

        Assert.assertTrue(changePasswordPage.checkErrorMessageAbove(cf.messageErrorAbove));
        Assert.assertTrue(changePasswordPage.checkErrorMessageNextto(cf.messageErrorNexttoConfirmPass, "confirmPassword"),"Error message display out of the field");
    }

    @AfterClass
    public void tearDown() {
        super.tearDown();
    }
}
