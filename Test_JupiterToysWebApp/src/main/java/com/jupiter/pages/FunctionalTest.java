package com.jupiter.pages;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jupiter.utils.CustomException;
import com.jupiter.utils.ExcelUtils;
import com.jupiter.utils.PropertiesUtility;
import com.jupiter.utils.ReportsUtility;
import org.openqa.selenium.remote.RemoteWebDriver;



public class FunctionalTest {

	public static String propertiesFile = "config.properties";
	protected static WebDriver driver;
	private String strClassName;
	public static ExtentTest test;
	public static ExtentReports report;
	public static ReportsUtility reports = new ReportsUtility();

	@BeforeSuite
	public void setEnvironment() {
		PropertiesUtility.getProperties(propertiesFile);
		report = reports.setReport();
		
	}

	@BeforeMethod()
	public void startTest(final Method m) {
		test = report.createTest(m.getName());
	}
	
	@AfterMethod
	public void endTest() {
		reports.writeToReport();
	}




	@BeforeTest
	public static void setUp(){

		if (PropertiesUtility.BROWSER_NAME == null || PropertiesUtility.BROWSER_NAME .equals("")) {
			throw new CustomException("BROWSER_NAME property is not defined");
		} else {
			if(PropertiesUtility.BROWSER_NAME .equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",".//src//test//resources//Drivers//IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			} else if(PropertiesUtility.BROWSER_NAME .equalsIgnoreCase("CHROME")) {
				System.setProperty("webdriver.chrome.driver",".//src//test//resources//Drivers//chromedriver.exe");
				driver = new ChromeDriver();

			} else if(PropertiesUtility.BROWSER_NAME .equalsIgnoreCase("FIREFOX")) {
				throw new CustomException("FIREFOX driver is not available in Drivers folder");

			} else if(PropertiesUtility.BROWSER_NAME .equalsIgnoreCase("SAFARI")) {
				throw new CustomException("SAFARI driver is not available in Drivers folder");

			} else {
				throw new CustomException("BROWSER_NAME property value is invalid");
			}
		}

		if (PropertiesUtility.BASE_URL == null || PropertiesUtility.BASE_URL .equals("")) {
			throw new CustomException("BASE_URL property is not defined");
		} else {
			driver.get(PropertiesUtility.BASE_URL);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}

	}


	@AfterTest
	public static void tearDown(){
		driver.manage().deleteAllCookies();
		driver.close();
	}

	

	@DataProvider(name = "data")
	public Iterator<Object[]> data(final Method m) {
		ExcelUtils excelUtilities = new ExcelUtils();
		try {
			strClassName = this.toString();
			strClassName = ExcelUtils.getTestCaseName(this.toString());
			System.err.println("Methodname: "+m.getName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return excelUtilities.dataProvider(m.getName(), strClassName);
	}

}


