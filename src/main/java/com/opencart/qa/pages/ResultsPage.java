package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ResultsPage {

	// 1. initiate driver
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 2. Page class constructor

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. private Final By Locators : PO

	private final By searchResults = By.cssSelector("div.product-thumb");

	// 4. public page actions and methods

	public int getSearchResultsCount() {
		int actSearchResultsCount = eleUtil.waitForAllElementsVisible(searchResults, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Total number of results products after search :" + actSearchResultsCount);
		return actSearchResultsCount;
	}

	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Selected Product name :" + productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}

}
