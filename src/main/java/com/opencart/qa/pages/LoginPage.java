package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// 1. Initiate Driver

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 2. Page class constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	// private By locators : Page Objects/Page Repositories

	private final By emailId = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.cssSelector("input[type='submit']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By regiterLink = By.linkText("Register");

	// 4. public page actions

	@Step("getting the login page title ... ")
	
	public String getLoginPageTitle() {

		String actTitle = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page Title is : " + actTitle);
		return actTitle;

	}

	@Step("getting the login page URL ... \n")
	public String getLoginPageURL() {
		String actURL = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL, AppConstants.MEDIUM_TIME_OUT);
		System.out.println("Login Page URL : " + actURL);
		return actURL;
	}

	@Step("Forgot Password Link exists on the login page... \n")
	public boolean isForgotPwdLinkExists() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();

	}

	@Step("user is logged in with username: {0} and Password : {1} \n")
	public HomePage doLogin(String userName, String pwd) {

		System.out.println("App Credentials : " + userName + " : " + pwd);
		eleUtil.doSendKeys(emailId, userName, AppConstants.MEDIUM_TIME_OUT);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);

		return new HomePage(driver);

	}
	
	@Step("Navigate to Registration Page...\n")
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementReadyAndClick(regiterLink, AppConstants.SHORT_TIME_OUT);
		return new RegisterPage(driver);
	}
	

}
