package com.DemoLogin;

import Utilities.BaseClass;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseClassTest2 extends BaseClass {

    @Test
    public void myTest(){

        driver.get("https://www.google.com.co/");

        if(driver.getCurrentUrl().contains("youtube")){
            test.log(Status.FAIL, "Incorrect URL");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("youtube"));

    }
}
