package Managers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ExtentTestNGTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();


    @Override
    public void onStart(ITestContext context) {
        System.out.println("Start of execution of test" + context.getName());
    }


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started " + result.getName());

        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        org.testng.annotations.Test testAnnotation = method.getAnnotation(Test.class);
        String testDescription = testAnnotation.description();

        test.set(extent.createTest(result.getMethod().getMethodName(), testDescription));
        test.set(extent.createTest(result.getMethod().getMethodName(),testAnnotation.description()));
    }

}