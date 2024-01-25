package com.DemoLogin;

import Utilities.BaseClass;
import org.testng.annotations.Test;

public class BaseClassTest1 extends BaseClass {

    @Test
    public void myTest(){

        driver.get("https://www.youtube.com.co/");

    }
}
