package com.opencart.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class HomePage {

	// 1. initiate driver

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 2. Page class constructor

	public HomePage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. private By locators: PO

	private final By logoutLink = By.linkText("Logout");
	private final By header = By.cssSelector("div#content h2");
	private final By searchTextField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");

	// public page actions and methods

	public boolean isLogoutLinkExists() {
		return eleUtil.isElementDisplayed(logoutLink);
	}

	public String getHomePageTitle() {

		String actTitle = eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Home Page Title is : " + actTitle);
		return actTitle;

	}

	public String getHomePageURL() {
		String actURL = eleUtil.waitForURLContains(AppConstants.HOME_PAGE_URL, AppConstants.MEDIUM_TIME_OUT);
		System.out.println("Home Page URL : " + actURL);
		return actURL;
	}

	public List<String> getHomePageHeaders() {
		List<WebElement> headerList = eleUtil.waitForAllElementsPresence(header, AppConstants.SHORT_TIME_OUT);
		List<String> headersValueList = new ArrayList<String>();

		for (WebElement e : headerList) {
			String text = e.getText();
			headersValueList.add(text);
		}

		return headersValueList;

	}

	public ResultsPage doSearch(String searchKey) {

		System.out.println("Entered Product name on Search :" + searchKey);
		eleUtil.doSendKeys(searchTextField, searchKey, AppConstants.SHORT_TIME_OUT);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);

	}

}
