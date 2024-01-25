package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class TestUtils {

    public static String takeScreensot(WebDriver driver, String fileName) throws IOException {
        File scrFile= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destFile = System.getProperty("user.dir") + "/test-output/screenshots/" + fileName + "png";
        FileUtils.copyFile(scrFile,new File(destFile));
        return destFile;
    }
}
