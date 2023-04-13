package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Test1 {
    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        System.out.println("executing before test using testng");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void abc() {
        System.out.println("executing first test using testng");
        driver.get("https://google.com");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("executing after test using testng");
        driver.close();
    }
}
