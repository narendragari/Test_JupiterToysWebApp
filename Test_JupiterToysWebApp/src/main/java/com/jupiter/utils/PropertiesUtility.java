package com.jupiter.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtility {

	public static String BROWSER_NAME;
	public static String BASE_URL;
	public static String TEST_DATA_FILE;
	public static String SCREENSHOTS_FOR_STEPS;

	static {
		PropertiesUtility.BROWSER_NAME = null;
		PropertiesUtility.BASE_URL = null;
		PropertiesUtility.TEST_DATA_FILE = null;
		PropertiesUtility.SCREENSHOTS_FOR_STEPS = null;
	}

	public static void getProperties(final String propertiesFile) {
		final File file = new File(propertiesFile);
		if (!file.exists()) {
			throw new CustomException(String.valueOf(propertiesFile) + ": file does not exist");
		}

		final Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		}
		catch (FileNotFoundException e) {
			throw new CustomException("Error loading file : " + propertiesFile);
		}
		catch (IOException e2) {
			throw new CustomException("Error loading file : " + propertiesFile);
		}

		PropertiesUtility.BROWSER_NAME = prop.getProperty("BROWSER_NAME");
		if (PropertiesUtility.BROWSER_NAME == null || PropertiesUtility.BROWSER_NAME.equals("")) {
			throw new CustomException("BROWSER_NAME property is not defined");
		}

		PropertiesUtility.BASE_URL = prop.getProperty("BASE_URL");
		if (PropertiesUtility.BASE_URL == null || PropertiesUtility.BASE_URL.equals("")) {
			throw new CustomException("BASE_URL property is not defined");
		}

		PropertiesUtility.TEST_DATA_FILE = prop.getProperty("TEST_DATA_FILE");
		if (PropertiesUtility.TEST_DATA_FILE == null || PropertiesUtility.TEST_DATA_FILE.equals("")) {
			throw new CustomException("TEST_DATA_FILE property is not defined");
		}
		
		PropertiesUtility.SCREENSHOTS_FOR_STEPS = prop.getProperty("SCREENSHOTS_FOR_STEPS");
		if (PropertiesUtility.SCREENSHOTS_FOR_STEPS == null || PropertiesUtility.SCREENSHOTS_FOR_STEPS.equals("")) {
			throw new CustomException("SCREENSHOTS_FOR_STEPS property is not defined");
		}

	}
}
