package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 100 :Design Login page Open Cart Application")
@Story("Login US:200; Add Login page features with Title, URL, Login Page, ForgotPassword link")
public class LoginPageTest extends BaseTest {

	@Description("Validating the Login Page Title")
	@Severity(SeverityLevel.NORMAL)
	
	@Test
	public void loginPageTitleTest() {
		Assert.assertEquals(loginPage.getLoginPageTitle(), AppConstants.LOGIN_PAGE_TITLE);
	}
	
	
	
	@Description("Validating the Login Page URL")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageUrlTest() {
		Assert.assertTrue(loginPage.getLoginPageURL().contains(AppConstants.LOGIN_PAGE_URL));
	}

	
	@Description("Validating the Forgot Password Link exist on the Login Page")
	@Severity(SeverityLevel.CRITICAL)
	@Issue("Bug Id : 123 and Title: Forgot Password link is missing from the Login Page")
	@Test
	public void forgotPwdLinkExistsTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExists(), "Forgot Password link exists");
	}
	
	@Description("User is able to login with valid Credentials and navigating to Home Page and retriving Home Page title")
	@Severity(SeverityLevel.BLOCKER)	

	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE);

	}

}
