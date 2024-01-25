package Managers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import freemarker.core.Environment;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance(){

        if (extent == null){
            createInstance("test-output/extent");
        }
        return extent;
    }

    public static ExtentReports createInstance(String filename){
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filename);
        htmlReporter.config().setDocumentTitle("Automation Testing Report");
        htmlReporter.config().setReportName("Regression Testing");
        htmlReporter.config().setTheme(Theme.DARK);

        extent= new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environemnt", "Production");
        extent.setSystemInfo("Platform","Windows");
        return extent;
    }
}
