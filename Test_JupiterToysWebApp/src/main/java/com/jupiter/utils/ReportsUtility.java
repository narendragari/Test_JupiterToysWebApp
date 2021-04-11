package com.jupiter.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportsUtility {

	public static ExtentTest test;
	public static ExtentReports report;
	public static String strLog="";
	
	static {
		report = null;
		test = null;
	}
	
	public String currentDirectory = System.getProperty("user.dir");
	public String strExecutionFolder = currentDirectory+"//TestResults//Execution_"+getcurrentdateandtime();
	
	public ExtentReports setReport() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(strExecutionFolder+"//TestReport.html");
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setSystemInfo("OS", System.getProperty("os.name"));
		report.setSystemInfo("Browser", PropertiesUtility.BROWSER_NAME);
		report.setSystemInfo("Application", "Jupiter Toys");
		report.setSystemInfo("Environment", "SIT");
		report.setSystemInfo("User Name", "Narendra Puttanarasappagari");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Jupiter Toys - Test Report");
		htmlReporter.config().setReportName("Jupiter Toys - Test Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return report;
	}
	
	public void writeToReport() {
		report.flush();
	}
	
	//TODO: implement of test object
	public static ExtentTest logger() {
		
		return test;
	}
	
	
	//TODO: screen capture
	public String addScreenShot(WebDriver driver) throws IOException{
		
		test.addScreenCaptureFromPath(captureScreen(driver));
		return "";
	}
	public String captureScreen(WebDriver driver)  {
		
		//String destination =".//Reports//Screenshots//"+getcurrentdateandtime()+".png";
		String destination =strExecutionFolder+"//Screenshots//"+getcurrentdateandtime()+".png";
		try {
			TakesScreenshot screen = (TakesScreenshot) driver;
			File src = screen.getScreenshotAs(OutputType.FILE);

			File target = new File(destination);
			FileUtils.copyFile(src, target);

		} catch(IOException e) {e.printStackTrace();	} 
		return destination;
	}

	public static String getcurrentdateandtime(){
		String str = null;
		try{
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str= dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		}
		catch(Exception e){

		}
		return str;
	}
}
