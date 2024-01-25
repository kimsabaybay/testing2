package com.DemoLogin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstClass {

    public static void main(String[] args) throws InterruptedException {

        //Set up webDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        //Navigate to a web page
        String url = "https://www.google.com";
        driver.get(url);


        WebElement googleSearchBar =  driver.findElement(By.name("q"));
        googleSearchBar.sendKeys("Selenium automation");
        //googleSearchBar.sendKeys(Keys.ENTER);

        WebElement searchButton = driver.findElement(By.name("btnK"));
        searchButton.click();


        driver.quit();




    }
}
