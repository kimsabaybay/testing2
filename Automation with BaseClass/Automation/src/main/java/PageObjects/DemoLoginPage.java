package PageObjects;

import Utilities.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class DemoLoginPage extends BaseClass {




    public DemoLoginPage() {
        PageFactory.initElements(BaseClass.driver, this);
    }

    @FindBy(id = "email")
    WebElement emailTxt;

    @FindBy(id = "enterimg")
    WebElement signUpContinueButton;

    public void singUp(String userName){
        driver.get("https://demo.automationtesting.in/");
        emailTxt.sendKeys(userName);
        signUpContinueButton.click();
        System.out.println(driver.getCurrentUrl());

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/Register.html"),"URL does not end with /Register.html");





    }






}
