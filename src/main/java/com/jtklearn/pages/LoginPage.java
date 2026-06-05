package com.jtklearn.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(css = "input[type='email']")
    public WebElement inputEmail;

    @FindBy(css = "input[type='password']")
    public WebElement inputPassword;

    @FindBy(css = "button[type='submit']")
    public WebElement btnLogin;

    // Mencari elemen pop-up error atau invalid-feedback secara lebih luas
    @FindBy(css = ".alert, .alert-danger, .invalid-feedback, .error-message, [role='alert']")
    public WebElement alertError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
