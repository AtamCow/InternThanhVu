package chapter10;

import base.BaseSetup;
import config.ConfigTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.*;

public class ResetPasswordTC extends BaseSetup {
    private ConfigTest cf;

    private LoginPage loginPage;
    private PageBase pageBase;
    private GuerrillamailPage guerrillamailPage;
    private ChangePasswordPage changePasswordPage;
    private ForgotPasswordPage forgotPasswordPage;

    @Before
    public void setUp() {
        super.setup();
        cf  = new ConfigTest(getDriver());
        loginPage = new LoginPage(getDriver());
        pageBase = new PageBase(getDriver());
        guerrillamailPage = new GuerrillamailPage(getDriver());
        changePasswordPage = new ChangePasswordPage(getDriver());
        forgotPasswordPage = new ForgotPasswordPage(getDriver());
    }

    @Test //User create and activate account
    public void TC10() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        changePasswordPage.checkPasswordChangeFormShown();
        changePasswordPage.enterNewPassword(cf.logPassword, cf.logPassword);
        changePasswordPage.checkWarningSamePassMessageShown();
    }

    @Test //Reset password shows error if the new password and confirm password doesn't match
    public void TC11() {
        cf.navigateRailway();
        loginPage.changePage();

        forgotPasswordPage.changeToForgotPasswordPage();
        forgotPasswordPage.sendMail(cf.validLogEmail);

        cf.navigateQuerrilMail();
        guerrillamailPage.confirmAccount(cf.idEmail, cf.hostEmail);
        guerrillamailPage.resetEmailWithTd();

        pageBase.changeToTab(1);

        changePasswordPage.checkPasswordChangeFormShown();
        changePasswordPage.enterNewPassword(cf.logPassword, cf.invalidPassword);
        changePasswordPage.checkErrorMessageAbove(cf.messageErrorAbove);
        changePasswordPage.checkErrorMessageNextto(cf.messageErrorNexttoConfirmPass, "confirmPassword");
    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
