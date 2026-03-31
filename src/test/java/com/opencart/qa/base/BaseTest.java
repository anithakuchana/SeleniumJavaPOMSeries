package com.opencart.qa.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.factory.DriverFactory;
import com.opencart.qa.pages.HomePage;
import com.opencart.qa.pages.LoginPage;
import com.opencart.qa.pages.ProductInfoPage;
import com.opencart.qa.pages.RegisterPage;
import com.opencart.qa.pages.ResultsPage;
import com.opencart.qa.pages.ShopingCartPage;

//@Listeners({ChainTestListener.class, TestAllureListener.class})
public class BaseTest {

	protected WebDriver driver;

	DriverFactory df;

	protected Properties prop;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected ShopingCartPage shoppingCartPage;
	protected RegisterPage registerPage;

	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(@Optional("chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProp();

		if (browserName != null) { // BrowserName is coming from the TestNG XML file
			prop.setProperty("browser", browserName);
		}

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);

	}

	
	@AfterMethod //will be running after each @Test Method
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) {
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
		 
		//take screenshot for failed and success
	//	ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		
	}
	
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
