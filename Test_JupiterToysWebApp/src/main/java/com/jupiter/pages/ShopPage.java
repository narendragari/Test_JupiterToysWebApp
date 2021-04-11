package com.jupiter.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ShopPage extends Page{


	public ShopPage(WebDriver driver) {
		super(driver);
	}

	public String strActualText="";
	public String strExpectedText="";

	@FindBy(xpath = "(//div/h4[text()='Funny Cow']/ following :: p/a[text()='Buy'])[1]")	private WebElement btnFunnyCow_Buy;
	@FindBy(xpath = "(//div/h4[text()='Fluffy Bunny'] / following :: p/a[text()='Buy'])[1]")	private WebElement btnFluffyBunny_Buy;
	@FindBy(xpath="//a[contains(text(), 'Cart')]") 	private WebElement lnkCart;

	public void buyFunnyCow() {
		click(btnFunnyCow_Buy, "Funny Cow - Buy");
		waitForBrowserStability();
	}

	public void buyFluffyBunny() {
		click(btnFluffyBunny_Buy, "Fluffy Bunny - Buy");
		waitForBrowserStability();
	}
	
	public void gotoCart() {
		click(lnkCart, "Cart");
		waitForBrowserStability();
	}


}
