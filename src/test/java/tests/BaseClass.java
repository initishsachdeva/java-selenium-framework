package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import services.ReadConfig;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BaseClass {
    public static WebDriver driver;
    public static Logger log;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    ReadConfig readConfig = new ReadConfig();
    public String baseUrl = readConfig.getApplicationURL();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();

    @BeforeClass
    @Parameters("browser")
    public void setup(String browserName, ITestContext context) {
        log = Logger.getLogger("java-selenium-framework");
        PropertyConfigurator.configure("log4j.properties");

        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equals("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        driver.manage().window().maximize();
        driver.get(baseUrl);
        extentTest = extentReports.createTest(context.getName());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @BeforeSuite
    public void initializeExtentReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/" + "Automation_Test_Report.html");

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Java Selenium Framework Test Project"); // Tile of report
        sparkReporter.config().setReportName("Functional Test Automation Report"); // name of the report
        sparkReporter.config().setTimeStampFormat("dd-MM-yy");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Host name", "localhost");
        extentReports.setSystemInfo("Environment", "Automation Testing");
        extentReports.setSystemInfo("user", "Nitish");
    }

    @AfterSuite
    public void generateExtentReports() throws IOException {
        extentReports.flush();
        Desktop.getDesktop().browse(new File("reports/Automation_Test_Report.html").toURI());
    }


    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = null;
            String testName = result.getTestContext().getName() + "  " + result.getMethod().getMethodName();
            screenshotPath = takeScreenshot(result.getTestContext().getName() + "  " + result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
            extentTest.fail(result.getThrowable());
            // to attach screenshots to allure report
            Allure.addAttachment(UUID.randomUUID().toString() + " " + testName, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(result.getMethod().getMethodName());
        }

    }

    public String takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";
        File target = new File(destination);
        try {
            FileUtils.copyFile(src, target);
        } catch (IOException e) {
            System.out.println("Unable to copy the source file to the destination folder" + e.getMessage());
        }
        return destination;
    }
}
