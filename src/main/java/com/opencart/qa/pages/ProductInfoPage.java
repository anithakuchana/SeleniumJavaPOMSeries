package com.opencart.qa.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ProductInfoPage {

	// 1. initiate driver

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap;

	// 2. Page class constructor

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. private By locators

	private final By header = By.cssSelector("div#content h1");
	private final By images = By.cssSelector("ul.thumbnails img");
	private final By qty = By.name("quantity");
	private final By addToCart = By.id("button-cart");
	private final By addToCartSucessMsg = By.xpath("//div[@class = 'alert alert-success alert-dismissible']");
	private final By shoppingCartLink = By.linkText("shopping cart");
	private final By metaData = By.xpath("//div[@id= 'content']//ul[@class = 'list-unstyled'][1]/li");
	private final By priceData = By.xpath("//div[@id= 'content']//ul[@class = 'list-unstyled'][2]/li");

	// 4. public methods

	public String getProductHeader() {
		return eleUtil.doElementGetText(header);
	}

	public int getProductImagesCount() {
		int imageCount = eleUtil.waitForAllElementsVisible(images, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("Total Number of Images for " + getProductHeader() + " is : " + imageCount);
		return imageCount;
	}
	
	public String enterQtyAndAddToCart(String qtyNumber) {
		
		eleUtil.doSendKeys(qty, qtyNumber);
		eleUtil.doClick(addToCart);
		return eleUtil.waitForElementVisible(addToCartSucessMsg, AppConstants.MEDIUM_TIME_OUT).getText();

		
	}
	
	public String enterQtyAndAddToCart(int qtyNumber) {
        return enterQtyAndAddToCart(String.valueOf(qtyNumber));
    }
	
	public ShopingCartPage goToShoppingCart() {
		eleUtil.doClick(shoppingCartLink);
		return new ShopingCartPage(driver);
	}
	
	
	public Map<String, String> getProductInfoData() {
		
	//	productMap = new HashMap<String, String>();  //order does not maintain
		productMap = new LinkedHashMap<String, String>(); //order Maintains
	//	productMap = new TreeMap<String, String>();  //sort
		
		productMap.put("productname", getProductHeader());
		productMap.put("imagescount",String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getproductPricingData();
		System.out.println("Product Information \n" + productMap);
		
		return productMap;
	}
	
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	
	private void getProductMetaData() {
		List<WebElement> metaDataList = eleUtil.getElements(metaData);
	
		
		for(WebElement e: metaDataList) {
			String metaText = e.getText();
			String meta[] = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
			
		}
		
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	
	private void getproductPricingData() {
		
		List<WebElement> priceDataList = eleUtil.getElements(priceData);
		String productPrice = priceDataList.get(0).getText().trim();
		String productEXTaxPrice = priceDataList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", productPrice);
		productMap.put("exTaxPrice", productEXTaxPrice);
		
	}
	
	

}
