package com.opencart.qa.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opencart.qa.exceptions.BrwoserExceptions;
import com.opencart.qa.exceptions.FrameWorkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;

	public static String highlight;

	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to init the driver on the basis of given browserName.
	 * 
	 * @param browserName
	 * @return it returns the driver value
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");

		System.out.println("Browser Name  : " + browserName);

		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			// driver = new SafariDriver();
			break;

		default:
			System.out.println(" ============ Invalid Browser =========== " + browserName);
			throw new BrwoserExceptions("========= Invalid Browser ================");

		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	/**
	 * this will return one local copy of driver for s specific thread.
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	// mvn test -Denv="qa"
	// mvn clean install
	// mvn package
	// mvn deploy
	public Properties initProp() {
		FileInputStream ip = null;
		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("Environment name is : " + envName);

		try {
			if (envName == null) {
				System.out.println(" Environment name is null, hence running the test cases from QA Environment...");
				ip = new FileInputStream(".\\src\\test\\resources\\config\\config.qa.properties");
			} else {

				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.dev.properties");
					break;
				case "uat":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.uat.properties");
					break;
				case "stage":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.stage.properties");
					break;
				case "prod":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");
					break;

				default:
					System.out.println("-------- INVALID ENVIRONMENT NAME -----------" + envName);
					throw new FrameWorkException("Invalid Environment " + envName);

				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();

		}

		return prop;
	}

	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs((OutputType.FILE));// temp dir
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}
