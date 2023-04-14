package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage {
    public WebDriver driver;
    @FindBy(id = "user-name")
    @CacheLookup
    WebElement username;
    @FindBy(xpath = "//*[@id='password']")
    @CacheLookup
    WebElement password;
    @FindBy(id = "login-button")
    @CacheLookup
    WebElement clickLogIn;

    @FindBy(xpath = "//*[text()='Products']")
    @CacheLookup
    WebElement correctScreen;


    public LoginPage(WebDriver webdriver) {
        // TODO Auto-generated constructor stub
        this.driver = webdriver;
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String name) {
        try {
            username.clear();
            username.sendKeys(name);
        } catch (Exception e) {
            System.out.println(" Unable to enter the provided username");
            Assert.fail("Exception in entering the username" + e.getMessage());
        }
    }

    public void enterPaasword(String pwd) {
        try {
            password.clear();
            password.sendKeys(pwd);
        } catch (Exception e) {
            System.out.println(" Unable to enter the provided password");
            Assert.fail("Exception in entering the password" + e.getMessage());
        }
    }

    public void clickLoginBtn() {
        try {
            clickLogIn.click();
        } catch (Exception e) {
            System.out.println(" Unable to click on login button");
            Assert.fail("Exception in clicking the loginBtn" + e.getMessage());
        }
    }

    public void correctScreenAfterLogin(String expectedText) {
        try {
            String text = correctScreen.getText();
            System.out.println("Text showing on inventory page" + text);
            Assert.assertEquals(text, expectedText);
        } catch (Exception e) {
            System.out.println(" Unable to navigate to correct screen");
            Assert.fail("Exception in navigating to correct screen" + e.getMessage());
        }
    }

}