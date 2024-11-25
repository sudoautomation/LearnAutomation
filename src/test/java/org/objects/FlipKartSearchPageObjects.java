package org.objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.base.Utils;

public class FlipKartSearchPageObjects {
	Utils utils;
	WebDriver driver;

	public FlipKartSearchPageObjects(WebDriver driver) {
		this.driver = driver;
		this.utils = new Utils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "q")
	private WebElement searchBox;

	@FindBy(xpath = "//*[@class='Nx9bqj _4b5DiR']")
	private List<WebElement> priceList;

	public List<WebElement> searchProduct(String productName) {
		searchBox.sendKeys(productName);
		searchBox.submit();
		return priceList;
	}

	public void clickProduct(WebElement product) {
		if (product != null) {
			product.click();
		}
	}

	public void selectDesiredProduct(String targetProd) throws InterruptedException {

		// Define the target values for the primary and secondary elements
		boolean elementFound = false;
		String prodDynamicXpath = "//*[contains(text(),'" + targetProd
				+ "')]/parent::div/parent::div/parent::a/parent::div/parent::div";

		String nextButtonXpath = "//*[contains(text(),'Next')]/parent::a";

		while (!elementFound) {
			try {
				// Locate the primary element (e.g., row containing the primary key text)
				WebElement primaryElement = driver.findElement(By.xpath(prodDynamicXpath));
				elementFound = true; // Exit the loop since the elements are found
				primaryElement.click();
				Thread.sleep(500);

			} catch (NoSuchElementException e) {
				// If elements are not found on the current page, try to go to the next page
				WebElement nextButton = utils.tryToFindXpath(nextButtonXpath);
				utils.isEnabled(nextButton, 10);
			}
		}
	}
}
