package services;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Utils {
    public void takeScreenshot(WebDriver driver, String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir") + "/screenshots/" + testName + ".png");
        try {
            FileUtils.copyFile(src, target);
        } catch (IOException e) {
            System.out.println("Unable to copy the source file to the destination folder" + e.getMessage());
        }
    }
}
