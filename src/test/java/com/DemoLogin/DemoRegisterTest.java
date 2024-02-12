package com.DemoLogin;

import PageObjects.DemoLoginPage;
import PageObjects.DemoRegisterFormPage;
import Utilities.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoRegisterTest extends BaseClass {


    DemoLoginPage demoLoginPage;
    DemoRegisterFormPage demoRegisterFormPage;



   // @Test
    public void singUp() throws InterruptedException {
        demoLoginPage = new DemoLoginPage();
        demoLoginPage.singUp("automation@auto.com");
        demoRegisterFormPage = new DemoRegisterFormPage();
        demoRegisterFormPage.alertOkButton();
        demoRegisterFormPage.fillForm();
    }

    @Test
    public void myTest() {
        driver.get("https://demo.automationtesting.in/Register.html");


        // Validate if URL ends with "/Register.html"
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.endsWith("/Register.html"), "URL does not end with /Register.html");
    }


}
