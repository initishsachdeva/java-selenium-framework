package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPage;

public class LoginTest extends BaseClass {
    LoginPage loginPage;

    @BeforeTest
    public void beforeTest() {
        loginPage = new LoginPage(driver);
    }

    /**
     * Verify user is not able to log in when they provide incorrect username and password
     */
    @Test
    public void loginTest1() {
        log.info(" ******** " + " executing test -> " + new Exception().getStackTrace()[0].getMethodName() + " ******** ");
        loginPage.enterUsername("name");
        log.info("entering username");
        loginPage.enterPaasword("password");
        log.info("entering password");
        loginPage.clickLoginBtn();
        log.info("clicking on login button");
        loginPage.correctScreenAfterLogin("Products");
        log.info(" ******** " + " execution finished -> " + new Exception().getStackTrace()[0].getMethodName() + " ******** ");
    }

    /**
     * Verify user is able to log in with correct username and password
     */
    @Test
    public void loginTest2() {
        log.info(" ******** " + " executing test -> " + new Exception().getStackTrace()[0].getMethodName() + " ******** ");
        loginPage.enterUsername(username);
        log.info("entering username");
        loginPage.enterPaasword(password);
        log.info("entering password");
        loginPage.clickLoginBtn();
        log.info("clicking on login button");
        loginPage.correctScreenAfterLogin("Products");
        log.info(" ******** " + " execution finished -> " + new Exception().getStackTrace()[0].getMethodName() + " ******** ");
    }

}
