package Admin;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class OrganizationInfo extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public OrganizationInfo(WebDriver driver) {
		// The super(driver) call is used to invoke the constructor of the parent
		// AbstractComponent class (this might handle
		// some base functionality for all page objects).
		// It passes the driver argument from the categories constructor to the
		// constructor of AbstractComponent.
		super(driver); // Call the parent class constructor
		this.driver = driver; // Assign the passed WebDriver instance to the local driver
		PageFactory.initElements(driver, this); // Initialize WebElements using PageFactory
		// initializes the page element so that you can work directly on the element
		// without getting the NullPointerException
	}

	@FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][text()='Admin']")
	private WebElement adminMenu;

	@FindBy(xpath = "//span[@class='oxd-topbar-body-nav-tab-item'][contains(text(),'Organization')]")
	private WebElement organizationMenu;

	@FindBy(xpath = "//a[@class='oxd-topbar-body-nav-tab-link'][contains(text(),'General Information')]")
	private WebElement generalInfo;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required']/following::input[1]")
	private WebElement shiftName;

	@FindBy(xpath = "//div[@class='oxd-grid-item oxd-grid-item--gutters organization-name-container']/ancestor::div[1]//input")
	private WebElement orgName;

	@FindBy(className = "oxd-switch-input")
	private WebElement editButton;

	@FindBy(css = "[class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")
	private WebElement countryList;

	@FindBy(className = "oxd-select-option")
	private List<WebElement> countriesValue;

	@FindBy(className = "orangehrm-left-space")
	private WebElement saveButton;

	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;

	@FindBy(className = "oxd-input-field-error-message")
	private WebElement errorMessage;

	@FindBy(className = "oxd-input-field-error-message")
	private WebElement timeShiftValidation;

	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	public void navigateToOrganization() {
		waitForElementToAppear(organizationMenu);
		organizationMenu.click();
		generalInfo.click();
	}

	public void editOrganization(String countryName) throws InterruptedException, AWTException, IOException {
		navigateToAdminPage();
		navigateToOrganization();

		waitForElementToAppear(orgName);
		enableEditMode();

		selectCountryFromDropdown(countryName);

		waitForElementToAppear(saveButton);
		saveButton.click();
		
		// handleSavePopup();
		
		//Runtime.getRuntime().exec("C:\\Users\\Mohamed\\Downloads\\TestAutoIT.exe");
		//Thread.sleep(2000);

		//return getSuccessMessage();
	}

	// scroll down to the countries web element
	private void enableEditMode() {
		editButton.click();
		scrollDown(driver, 500); // Ensure the dropdown is visible
	}

	private void selectCountryFromDropdown(String countryName) {
		waitForElementToAppear(countryList);
		countryList.click();
		waitForListOfElementsToAppear(countriesValue);

		boolean countryFound = false;

		for (WebElement country : countriesValue) {
			// 1- Scrolls to the element.
			// 2- Retrieves its text.
			// 3- Compares it with the target country name.
			// 4- Clicks the matching element if found.
			if (scrollAndCheckCountry(country, countryName)) {
				countryFound = true;
				break;
			}
		}

		if (!countryFound) {
			System.out.println("Country not found in the dropdown: " + countryName);
		}
	}

	// method is designed to scroll to a specific country in a dropdown list
	// country parameter: A WebElement representing a country option in the
	// dropdown.
	// countryName parameter: A String representing the name of the country to be
	// selected.
	private boolean scrollAndCheckCountry(WebElement country, String countryName) {
		try {
			// Scrolls the dropdown to ensure the country element is visible in the
			// viewport.
			// This is necessary because dropdowns often have a scrollable list of options,
			// and the desired option might not be immediately visible.
			scrollDropdown(driver, country); // Scroll to the specific country
			// Retrieves the text of the country element (the name of the country) and trims
			// any leading or trailing whitespace.
			String countryText = country.getText().trim();
			// Compares the text of the country element with the desired countryName.
			if (countryText.equalsIgnoreCase(countryName.trim())) {
				System.out.println("Checking Country: " + countryText);
				country.click();
				System.out.println("Selected Country: " + countryText);
				return true; // indicates that the desired country was successfully found and selected.
			}
		}
		// If an exception occurs (e.g., the element is stale or not interactable), it
		// logs an error message instead of crashing the program.
		catch (Exception e) {
			System.out.println("Error locating country: " + countryName);
		}
		return false;
	}

	public String getSuccessMessage() {
		waitForElementToAppear(sucessMessage);
		System.out.println("Success Message: " + sucessMessage.getText());
		return sucessMessage.getText();
	}

}