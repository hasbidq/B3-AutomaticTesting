package com.jtklearn.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    private WebDriver driver;

    @FindBy(css = ".user-profile-menu")
    public WebElement menuProfile;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    public WebElement btnLogout;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
