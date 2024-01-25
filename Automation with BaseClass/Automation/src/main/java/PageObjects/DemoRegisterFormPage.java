package PageObjects;

import Utilities.BaseClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DemoRegisterFormPage {



    public DemoRegisterFormPage( ) {

        PageFactory.initElements(BaseClass.driver,this);

    }

    @FindBy(xpath = "//input[@ng-model = 'FirstName']")
    WebElement firstNameTxt;

    @FindBy(xpath = "//input[@ng-model = 'LastName']")
    WebElement lastNameTxt;

    @FindBy(xpath = "//textarea[@ng-model = 'Adress']")
    WebElement addressTxtArea;

    @FindBy(xpath = "//input[@ng-model = 'EmailAdress']")
    WebElement emailAdressTxt;

    @FindBy(xpath = "//input[@ng-model = 'Phone']")
    WebElement phoneTxt;

    @FindBy(xpath = "//input[@value = 'Male']")
    WebElement maleRadioBtn;

    @FindBy(xpath = "//input[@value = 'FeMale']")
    WebElement femaleRadioBtn;

    @FindBy(xpath = "//input[@value = 'Cricket']")
    WebElement cricketCheckbox;

    @FindBy(xpath = "//input[@value = 'Movies']")
    WebElement moviesCheckbox;

    @FindBy(xpath = "//input[@value = 'Hockey']")
    WebElement hockeyCheckbox;

    @FindBy(xpath = "//div[@id='msdd']")
    WebElement languagesList;

    @FindBy(xpath = "//select[@ng-model = 'Skill']")
    WebElement skillsDropdown;

    @FindBy(xpath = "//select[@ng-model = 'country']")
    WebElement countryDropdown;

    @FindBy(xpath = "//select[@ng-model = 'yearbox']")
    WebElement dobYearDropdown;

    @FindBy(xpath = "//select[@ng-model = 'monthbox']")
    WebElement dobMonthDropdown;

    @FindBy(xpath = "//select[@ng-model = 'daybox']")
    WebElement dobDayDropdown;

    @FindBy(xpath = "//input[@ng-model = 'Password']")
    WebElement passwordTxt;

    @FindBy(xpath = "//input[@ng-model = 'CPassword']")
    WebElement confirmPasswordTxt;

    @FindBy(xpath = "//button[@id = 'submitbtn']")
    WebElement submitBtn;

    @FindBy(xpath = "//button[@id = 'Button1']")
    WebElement refreshBtn;

    @FindBy(xpath = "//button[@class='btn btn-danger']")
    WebElement alertOkButton;

    @FindBy(linkText = "SwitchTo")
    WebElement switchToDropDown;

    @FindBy(partialLinkText = "Alerts")
    WebElement alertsSwitchToOption;

    @FindBy(xpath = "//div[@id='dismiss-button']")
    WebElement dismissPopUpButton;

    @FindBy(xpath = "//a[@href='#CancelTab']")
    WebElement alertOkCancel;






    public void alertOkButton() throws InterruptedException {

        switchToDropDown.click();
        alertsSwitchToOption.click();


       // Thread.sleep(10000);
        //dismissPopUpButton.click();

       alertOkButton.click();

        Alert alert = BaseClass.driver.switchTo().alert();
        System.out.println(alert.getText());

        alert.accept();



    }




    public void fillForm(){
        setFirstName("automation");
        setLastName("qa");
        setAddress("Miami Fl 41 Av");
        setEmailAddress("autom@mail.com");
        setPhone("1231235");
        selectGender("Male");
        selectHobby("Movies");
        setPassword("123asd");
        setConfirmPassword("123asd");

    }

    public void setFirstName(String firstName){
        firstNameTxt.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        lastNameTxt.sendKeys(lastName);
    }

    public void setAddress(String address) {
        addressTxtArea.sendKeys(address);
    }

    public void setEmailAddress(String emailAddress) {
        emailAdressTxt.sendKeys(emailAddress);
    }

    public void setPhone(String phone) {
        phoneTxt.sendKeys(phone);
    }

    public void selectGender(String gender) {
        if ("Male".equalsIgnoreCase(gender)) {
            maleRadioBtn.click();
        } else if ("Female".equalsIgnoreCase(gender)) {
            femaleRadioBtn.click();
        }
    }

    public void selectHobby(String hobby) {
        switch (hobby) {
            case "Cricket":
                cricketCheckbox.click();
                break;
            case "Movies":
                moviesCheckbox.click();
                break;
            case "Hockey":
                hockeyCheckbox.click();
                break;
        }
    }

    // You can add methods for other elements like this, such as selecting values from dropdowns, clicking buttons, etc.

    public void setPassword(String pwd) {
        passwordTxt.sendKeys(pwd);
    }

    public void setConfirmPassword(String pwd) {
        confirmPasswordTxt.sendKeys(pwd);
    }

    public void clickSubmit() {
        submitBtn.click();
    }

    public void clickRefresh() {
        refreshBtn.click();
    }











}
