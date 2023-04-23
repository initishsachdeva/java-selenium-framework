package services;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import tests.BaseClass;

import java.io.File;
import java.io.IOException;

public class Utils extends BaseClass {

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
