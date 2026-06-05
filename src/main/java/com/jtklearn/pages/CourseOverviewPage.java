package com.jtklearn.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CourseOverviewPage {
    private WebDriver driver;

    // Input enroll key - berdasarkan class CSS yang ada di CourseOverview.css: .enroll-form input.form-control
    @FindBy(css = ".enroll-form input.form-control")
    public WebElement inputEnrollKey;

    // Tombol enroll - berdasarkan class .button-enroll
    @FindBy(css = ".button-enroll")
    public WebElement btnEnroll;

    // Pesan error enroll (modal atau div error)
    @FindBy(css = ".modal-body, .error-modal, .alert-danger, [class*='error'], [class*='Error']")
    public WebElement alertErrorEnroll;

    // Pesan sukses enroll
    @FindBy(css = ".alert-success, [class*='success'], [class*='Success']")
    public WebElement alertSuccessEnroll;

    public CourseOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
