package com.DemoLogin;

import PageObjects.DemoRegisterFormPage;
import Utilities.BaseClass;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;

public class FillFormTest extends BaseClass {

    DemoRegisterFormPage demoRegisterFormPage;

    @Test
    public void FillForm()  {
        test = extent.createTest("Fill form test", "This test will attempt to fill the form");

        demoRegisterFormPage = new DemoRegisterFormPage();
        driver.get("https://demo.automationtesting.in/Register.html");
        demoRegisterFormPage.fillForm();

        test.log(Status.INFO, "Step1 : Filling the form");
        test.log(Status.PASS, "Form Filled");
        test.log(Status.WARNING, "warning");

        test.assignCategory("Smoke", "Regression");
        test.assignAuthor("Kim Sabaybay");

        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", "Windows10");
    }
}
