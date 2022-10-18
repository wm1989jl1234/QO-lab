package com.fdmgroup.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Utility class designed to get the web driver for browsers.
 * 
 * @since 2020-10-23
 *
 */
public class DriverUtilities {
	
	/**
	 * Private instance of the class.
	 */
	private static DriverUtilities driverUtilities;
	
	/**
	 * Private instance of the webdriver.
	 */
	private WebDriver driver;
	
	/**
	 * private constructor. 
	 */
	private DriverUtilities() {
		super();
	}
	
	/**
	 * Method to get the instance of this class. 
	 * 
	 * @return An instance of driverutilities. 
	 */
	public static DriverUtilities getInstance() {
		if(driverUtilities == null) {
			driverUtilities = new DriverUtilities();
		}
		return  driverUtilities;
	}
	
	
	public WebDriver getDriver() {
		if(driver == null) {
			createDriver();
		}
		return driver;
	}

	/**
	 * Depending on the browser name, create a new driver of such web browser. 
	 */
	private void createDriver() {
		String driverName = getDrivername(); 
		
		switch(driverName) {
		case "google chrome":
			System.setProperty("Webdriver.chrome.driver", "chromedriver.exe");
			this.driver = new ChromeDriver();
			break;
		
		case "firefox":
			break;
		}
	}

	/**
	 * Look at the configuration file and extract the value for key('browser').
	 * Return the browser value as string. 
	 * 
	 * @return driverName -> String
	 */
	private String getDrivername() {
		Properties config = new Properties();
		String driverName = "";
		try {
			config.load(new FileInputStream("properties.config"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String key:config.stringPropertyNames()) {
			if(key.equals("browser")) {
				driverName = config.getProperty(key);
			}
		}
		
		return driverName;
	}
}
