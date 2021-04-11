package com.jupiter.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.jupiter.pages.CartPage;
import com.jupiter.pages.ContactPage;
import com.jupiter.pages.FunctionalTest;
import com.jupiter.pages.HomePage;
import com.jupiter.pages.ShopPage;

import java.io.IOException;
public class Jupiter_WebTests extends FunctionalTest{

	public void getScreenshot() throws IOException {
		test.addScreenCaptureFromPath(reports.captureScreen(driver));
	}



	@Test(dataProvider="data", testName="TM_01_ValidateFieldsOnContactPage")
	public void TM_01_ValidateFieldsOnContactPage(String strForename, String strEmail, 
			String strMessage) throws IOException{

		test.assignCategory("Regression");

		try {
			test.log(Status.INFO, "Validating mandatory fields on Contact page");
			HomePage homePage = new HomePage(driver);
			homePage.gotoContactPage();
			ContactPage contactPage = new ContactPage(driver);
			contactPage.clickSubmit();
			contactPage.validateErrorsOnPage();
			contactPage.setMandatoryDetails(strForename, strEmail, strMessage);
			contactPage.validateErrorsOnPage();

		} catch (Exception e) {
			test.log(Status.FAIL, "unable to validate fields on Contact page"+e.getMessage());
			getScreenshot();
		}
	}
	
	@Test(dataProvider="data", testName="TM_02_VerifyFeedbackSubmission")
	public void TM_02_VerifyFeedbackSubmission(String strForename, String strEmail, 
			String strMessage) throws IOException, AssertionError{

		test.assignCategory("Regression");

		try {
			test.log(Status.INFO, "Verify feedback form submission");
			HomePage homePage = new HomePage(driver);
			homePage.gotoContactPage();
			ContactPage contactPage = new ContactPage(driver);
			contactPage.setMandatoryDetails(strForename, strEmail, strMessage);
			contactPage.clickSubmit();
			contactPage.validateFormSubmission(strForename);

		}  catch (AssertionError ae) {
			test.log(Status.FAIL, "Assertion Error: "+ae.getMessage());
			getScreenshot();
			Assert.fail();
			
		} catch (Exception e) {
			test.log(Status.FAIL, "Unsuccessful feedback submission"+e.getMessage());
			getScreenshot();
		}
	}
	
	@Test(dataProvider="data", testName="TM_03_VerifyContactPageWithInvalidData")
	public void TM_03_VerifyContactPageWithInvalidData(String strForename, String strEmail, 
			String strMessage) throws IOException{

		test.assignCategory("Regression");

		try {
			test.log(Status.INFO, "Verify Contact page with invalid data");
			HomePage homePage = new HomePage(driver);
			homePage.gotoContactPage();
			ContactPage contactPage = new ContactPage(driver);
			contactPage.setMandatoryDetails(strForename, strEmail, strMessage);
			contactPage.validateErrorsOnPage();

		} catch (Exception e) {
			test.log(Status.FAIL, "unable to  verify with invalid data"+e.getMessage());
			getScreenshot();
		}
	}
	
	@Test(testName="TM_04_VerifyTheShoppingCartData")
	public void TM_04_VerifyTheShoppingCartData() throws IOException{

		test.assignCategory("Regression");

		try {
			test.log(Status.INFO, "Verify the shopping cart data");
			HomePage homePage = new HomePage(driver);
			homePage.gotoShopPage();
			ShopPage shopPage = new ShopPage(driver);
			shopPage.buyFunnyCow();
			shopPage.buyFunnyCow();
			shopPage.buyFluffyBunny();
			shopPage.gotoCart();
			CartPage cartPage = new CartPage(driver);
			cartPage.getCartDetails();
			
		} catch (Exception e) {
			test.log(Status.FAIL, "unable to  verify Shopping cart data"+e.getMessage());
			getScreenshot();
		}
	}

}

