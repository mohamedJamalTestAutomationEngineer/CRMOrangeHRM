package Admin;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import AbstractComponents.AbstractComponent;

public class AddSystemsUsers extends AbstractComponent {

	private static final String USERNAME_ALREADY_EXISTS_ERROR = "Already exists";
	private static final String USERNAME_MIN_LENGTH_ERROR = "Username should be at least 5 characters";

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public AddSystemsUsers(WebDriver driver) {
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

	@FindBy(className = "oxd-topbar-body-nav-tab")
	private WebElement userManagementMenu;

	@FindBy(className = "oxd-dropdown-menu")
	private WebElement usersMenu;

	@FindBy(css = "[class='oxd-button oxd-button--medium oxd-button--secondary']")
	private WebElement addButton;

	@FindBy(css = "div[class*='oxd-select-text oxd-select-text'] div:nth-child(1)")
	private WebElement RoleButton;

	@FindBy(xpath = "//div[@role='option']/span[text()='Admin']")
	private WebElement adminOption;

	@FindBy(xpath = "//div[@role='option']/span[text()='ESS']")
	private WebElement ESSOption;

	@FindBy(xpath = "(//div[@class='oxd-select-wrapper'])[2]/div")
	private WebElement Status;

	@FindBy(xpath = "//div[@role='option']/span[text()='Enabled']")
	private WebElement Enabled;

	@FindBy(xpath = "//div[@role='option']/span[text()='Disabled']")
	private WebElement Disabled;

	@FindBy(css = "div.oxd-autocomplete-wrapper input")
	private WebElement empName;

	@FindBy(xpath = "//label[text()='Username']/following::input[1]")
	private WebElement userName;

	@FindBy(xpath = "//div[@role='listbox']//div[@class='oxd-autocomplete-option']/span")
	private List<WebElement> autoCompletedNames; // Locator for the autocomplete dropdown options for Employee Name

	@FindBy(xpath = "//label[text()='Password']/following::input[1]")
	private WebElement password;

	@FindBy(xpath = "//label[text()='Confirm Password']/following::input[1]")
	private WebElement confirmPassword;

	@FindBy(css = "button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")
	private WebElement saveButton;

	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;

	@FindBy(css = "[ class*='input-field-error-message']")
	private List<WebElement> errorMessages; // Locator for error messages displayed on the page

	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	public String fillUserDetails(String Employeename, String name, String userPassword) {
		navigateToAdminPage();
		waitForElementToAppear(addButton);
		addButton.click();
		waitForElementToAppear(RoleButton);
		RoleButton.click(); // Open the Role dropdown
		waitForElementToAppear(adminOption);
		ESSOption.click(); // Select the "ESS" option for Role
		waitForElementToBeClickable(Status);
		Status.click();
		Enabled.click();
		waitForElementToBeClickable(empName);
		empName.sendKeys(Employeename);
		waitForListOfElementsToAppear(autoCompletedNames);
		autoCompletedNames.get(0).click(); // Select the first autocomplete option

		waitForElementToAppear(userName);

		// Ensure username has at least 5 characters
		if (name.length() < 5) {
			System.out.println(USERNAME_MIN_LENGTH_ERROR);
			return USERNAME_MIN_LENGTH_ERROR;
		} else {
			userName.sendKeys(name); // Enter the username
		}

		waitForListOfElementsToAppear(errorMessages); // Wait for potential error messages

		password.sendKeys(userPassword);
		confirmPassword.sendKeys(userPassword);

		saveButton.click();

		return "Addition Steps completed";
	}

	public String AddUsersPositiveScenario(String employeeName, String username, String userPassword)
			throws InterruptedException {
		fillUserDetails(employeeName, username, userPassword);
		waitForElementToAppear(sucessMessage);
		return sucessMessage.getText();
	}

	public String AddUsersNegativeScenario(String employeeName, String username, String userPassword)
			throws InterruptedException {
		fillUserDetails(employeeName, username, userPassword);

		// Wait for and retrieve the specific error message "Already exists"
		String errorMessage = waitForSpecificErrorMessage(USERNAME_ALREADY_EXISTS_ERROR);
		if (errorMessage != null) {
			return errorMessage; // Return the error message if found
		}
		return errorMessage; // Return null if no error message found

	}

	// This method is designed to wait for a specific error message to appear on a
	// web page within a given time (10 seconds in this case).
	// If it finds the message, it returns the message text. If not, it either keeps
	// waiting until the time runs out or
	// returns a default "Validation error not found" message if something goes
	// wrong.
	public String waitForSpecificErrorMessage(String expectedMessage) {
		// Setup a Wait Timer
		// Why it’s needed: Web pages may take time to load or show error messages.
		// The timer ensures the program waits until the error message appears instead
		// of moving on too quickly.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			// What it does: The wait.until method tells the program to repeatedly check for
			// the error message until:
			// 1- It finds the message.
			// 2- The 10 seconds run out.
			return wait.until(driver -> {
				// What it does: This finds all error messages on the page by looking for
				// elements with a CSS class containing "input-field-error-message".
				// Why it’s needed: There could be multiple error messages, so we collect them
				// all to check each one.
				List<WebElement> errors = driver.findElements(By.cssSelector("[class*='input-field-error-message']"));
				for (WebElement error : errors) {
					if (error.getText().contains(expectedMessage)) {
						return error.getText(); // Return the error message if it matches
					}
				}
				/*
				 * What return null; Does ? When the code in the lambda function encounters
				 * return null;, it tells Selenium:
				 * 
				 * "I didn't find what I was looking for yet. Keep waiting and try again."
				 * Selenium will then:
				 * 
				 * Wait for a very short period (a few milliseconds). Re-run the lambda function
				 * to check again. Repeat this process until a match is found or the timer runs
				 * out.
				 */
				return null; // Keep waiting if the message isn't found
			});
		} catch (Exception e) {
			return "Validation error not found."; // Return this if the message doesn't appear in time after 10 seconds
													// mentioned in the wait statement
		}
	}

}
