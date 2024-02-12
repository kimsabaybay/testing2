package Utilities;


import Managers.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseClass {

    public static WebDriver driver; // WebDriver instance

    public static ExtentReports extent = ExtentManager.getInstance();
    public static ExtentTest test;


    @Parameters("browser")
    @BeforeMethod
    public void setUp(Method method,@Optional("Chrome") String browser) {
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            driver = new EdgeDriver();
        }else {
            throw new IllegalArgumentException("Broser not supported" +browser);
        }
        //WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        // Common setup steps, e.g., maximize window, set implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test = extent.createTest(method.getName());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus()== ITestResult.FAILURE){
            TestUtils.takeScreensot(driver,result.getName());
            test.fail("Details of Failure", MediaEntityBuilder.createScreenCaptureFromPath("pathToScreenshotHere").build());
        }else{
            test.pass("Test Pass successfully");
        }
        extent.flush();
        driver.quit();

    }

    public void captureScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destDir = "screenshots";
        new File(destDir).mkdirs();
        String destFile = testName + ".png";

        try {
            FileUtils.copyFile(source, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
