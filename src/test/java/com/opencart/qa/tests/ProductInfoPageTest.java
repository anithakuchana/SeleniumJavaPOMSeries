package com.opencart.qa.tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {
		homePage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{ "macbook", "MacBook Pro" }, 
			{ "macbook", "MacBook Air" },
			{ "imac", "iMac" },
			{ "samsung", "Samsung SyncMaster 941BW" }, 
			{ "samsung", "Samsung Galaxy Tab 10.1" },
			{ "canon", "Canon EOS 5D" }

		};
	}

	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		resultsPage = homePage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);

	}

	@DataProvider
	public Object[][] getProductImagesData() {
		return new Object[][] { 
			{ "macbook", "MacBook Pro", 4 },
			{ "macbook", "MacBook Air", 4 },
			{ "imac", "iMac", 3 },
			{ "samsung", "Samsung SyncMaster 941BW", 1 },
			{ "samsung", "Samsung Galaxy Tab 10.1", 7 },
			{ "canon", "Canon EOS 5D", 3 }

		};
	}

	@Test(dataProvider = "getProductImagesData")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		resultsPage = homePage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), imageCount);
	}
	
	
	
	@DataProvider(name = "produtInfoData")
	public Object[][] getproductInfoData() {

	    Map<String, String> product1 = new LinkedHashMap<>();
	    product1.put("searchKey", "macbook");
	    product1.put("productname", "MacBook Pro");
	    product1.put("imagescount", "4");
	    product1.put("Brand", "Apple");
	    product1.put("Product Code", "Product 18");
	    product1.put("Reward Points", "800");
	    product1.put("Availability", "Out Of Stock");
	    product1.put("productprice", "$2,000.00");
	    product1.put("exTaxPrice", "$2,000.00");

	    Map<String, String> product2 = new LinkedHashMap<>();
	    product2.put("searchKey", "imac");
	    product2.put("productname", "iMac");
	    product2.put("imagescount", "3");
	    product2.put("Brand", "Apple");
	    product2.put("Product Code", "Product 14");
	    product2.put("Reward Points", null);
	    product2.put("Availability", "Out Of Stock");
	    product2.put("productprice", "$122.00");
	    product2.put("exTaxPrice", "$100.00");

	    Map<String, String> product3 = new LinkedHashMap<>();
	    product3.put("searchKey", "samsung");
	    product3.put("productname", "Samsung SyncMaster 941BW");
	    product3.put("imagescount", "1");
	    product3.put("Brand", null);
	    product3.put("Product Code", "Product 6");
	    product3.put("Reward Points", null);
	    product3.put("Availability", "2-3 Days");
	    product3.put("productprice", "$242.00");
	    product3.put("exTaxPrice", "$200.00");

	    return new Object[][] {
	        { product1 },
	        { product2 },
	        { product3 }
	    };
	}
	
	
	
	
	@Test(dataProvider = "produtInfoData")
	public void productInfoTest(Map<String, String> expectedData) {
	
		resultsPage = homePage.doSearch(expectedData.get("searchKey"));
		productInfoPage = resultsPage.selectProduct(expectedData.get("productname"));
	
		Map<String, String>productInfoDataMap = productInfoPage.getProductInfoData();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productInfoDataMap.get("productname"), expectedData.get("productname"));
		softAssert.assertEquals(productInfoDataMap.get("imagescount"), expectedData.get("imagescount"));
		
		softAssert.assertEquals(productInfoDataMap.get("Brand"), expectedData.get("Brand"));
		softAssert.assertEquals(productInfoDataMap.get("Product Code"), expectedData.get("Product Code"));
		softAssert.assertEquals(productInfoDataMap.get("Reward Points"), expectedData.get("Reward Points"));
		softAssert.assertEquals(productInfoDataMap.get("Availability"), expectedData.get("Availability"));
	
		softAssert.assertEquals(productInfoDataMap.get("productprice"), expectedData.get("productprice"));
		softAssert.assertEquals(productInfoDataMap.get("exTaxPrice"), expectedData.get("exTaxPrice"));
		
		softAssert.assertAll();
		
	}
	
	
	
	
	
	
	
	@DataProvider(name = "productAddToShoppingCartData")
	public Object[][] getProductAddToShoppingCartData() {
		return new Object[][] { 
			{ "macbook", "MacBook Pro", 1 },
			{ "macbook", "MacBook Air",2 },
			{ "imac", "iMac", 1 },
			{ "samsung", "Samsung SyncMaster 941BW", 3 },
			{ "samsung", "Samsung Galaxy Tab 10.1", 1 },
		};
	}
	
	
	@Test(dataProvider = "productAddToShoppingCartData", priority = Integer.MAX_VALUE)
	public void addToCartAndClickOnShoppingCartTest(String searchKey, String productName, int qty) {
		
		resultsPage = homePage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		
		String actSucessMsg = productInfoPage.enterQtyAndAddToCart(qty);
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertTrue(actSucessMsg.contains(AppConstants.ADD_TO_CART_SUCESS_MSG));
	
		shoppingCartPage = productInfoPage.goToShoppingCart();	
		
		softAssert.assertTrue(shoppingCartPage.isProductDisplayed(productName), 
				"Product is not displayed in the shopping cart :" + productName);
		
		softAssert.assertEquals(shoppingCartPage.getProductQTY(productName), qty,
				"Product Quantity mismatche in shopping cart for: "+ productName);
		
	}
	
	
	

}
