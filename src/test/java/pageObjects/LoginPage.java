package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import tests.BaseClass;

public class LoginPage extends BaseClass {

    By username = By.id("user-name");
    By password = By.xpath("//*[@id='password']");
    By clickLogIn = By.id("login-button");
    By correctScreen = By.xpath("//*[text()='Products']");

    public LoginPage(WebDriver driver) {
        BaseClass.driver = driver;
    }

    public void enterUserName(String name) {
        try {
            driver.findElement(username).clear();
            driver.findElement(username).sendKeys(name);
        } catch (Exception e) {
            System.out.println("Unable to enter username");
            Assert.fail("Exception in entering the username : " + e.getMessage());
        }
    }

    public void enterPaasword(String pwd) {
        try {
            driver.findElement(password).clear();
            driver.findElement(password).sendKeys(pwd);
        } catch (Exception e) {
            System.out.println(" Unable to enter the provided password");
            Assert.fail("Exception in entering the password : " + e.getMessage());
        }
    }

    public void clickLoginBtn() {
        try {
            driver.findElement(clickLogIn).click();
        } catch (Exception e) {
            System.out.println(" Unable to click on login button");
            Assert.fail("Exception in clicking the loginBtn : " + e.getMessage());
        }
    }

    public void correctScreenAfterLogin(String expectedText) {
        try {
            String text = driver.findElement(correctScreen).getText();
            System.out.println("Text showing on inventory page " + text);
            Assert.assertEquals(text, expectedText);
        } catch (Exception e) {
            System.out.println(" Unable to navigate to correct screen");
            Assert.fail("Exception in navigating to correct screen : " + e.getMessage());
        }
    }
}
