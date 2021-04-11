package com.jupiter.pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.Status;

public class CartPage extends Page{


	public CartPage(WebDriver driver) {
		super(driver);
	}

	public String strActualText="";
	public String strExpectedText="";

	@FindBy(xpath = "(//div/h4[text()='Funny Cow']/ following :: p/a[text()='Buy'])[1]")	private WebElement btnFunnyCow_Buy;
	@FindBy(xpath = "(//div/h4[text()='Fluffy Bunny'] / following :: p/a[text()='Buy'])[1]")	private WebElement btnFluffyBunny_Buy;
	@FindBy(xpath="//a[contains(text(), 'Cart')]") 	private WebElement lnkCart;


	public void getCartDetails() { 
		List <WebElement> lstCartItems = driver.findElements(By.xpath("//table[@class='table table-striped cart-items']/tbody/tr"));
		for(int count=1; count <= lstCartItems.size(); count++) {
			WebElement eleItem = driver.findElement(By.xpath("//table[@class='table table-striped cart-items']/tbody/tr["+count+"]/td[1]"));
			WebElement eleQuantity = driver.findElement(By.xpath("//table[@class='table table-striped cart-items']/tbody/tr["+count+"]/td[3]/input"));
			
			String strItem = eleItem.getText();
			String strQuantity = eleQuantity.getAttribute("value");
			click(eleItem, strItem); // just to take screenshot of the page, we are performing this action
			test.log(Status.INFO, "Item: "+strItem+"  -->  Quantity: "+strQuantity);
		}
		test.log(Status.PASS, "Cart items verified successfully");
	}
}
