package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.ElementUtil;

public class ShopingCartPage {

	// Initiate driver
	private WebDriver driver;
	private ElementUtil eleUtil;
	

	public ShopingCartPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//private By locators
	
	//public methods
	
	public boolean isProductDisplayed(String productName) {
		By productLink = By.linkText(productName);
		return eleUtil.isElementDisplayed(productLink);
	}
	
	
	public int getProductQTY(String productName) {
		By qtyLocator = By.xpath("//a[text()='" + productName + "']/ancestor::tr//input[contains(@name,'quantity')]");
		String qtyValue = eleUtil.getElementAttribute(qtyLocator, "value");
		return Integer.parseInt(qtyValue);
	}
	

}
