package Admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class EditSystemsUsers extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public EditSystemsUsers(WebDriver driver) {
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

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Password']/following::input[@type='password'][1]")
	private WebElement password;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Password']/following::input[@type='password'][2]")
	private WebElement confirmPassword;

	@FindBy(css = "button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")
	private WebElement saveButton;

	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;

	@FindBy(css = "[ class*='input-field-error-message']")
	private List<WebElement> errorMessages; // Locator for error messages displayed on the page

	// div[@class='oxd-table-cell oxd-padding-cell']/div[text()='manda user']
	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell']")
	private WebElement EmpNameInTable;

	@FindBy(xpath = "//button[@class='oxd-icon-button oxd-table-cell-action-space'][2]")
	private WebElement editButton;

	@FindBy(css = "div.oxd-autocomplete-option span")
	private List<WebElement> autoCompleteOptions;

	@FindBy(xpath = "//input[@type='checkbox']")
	private WebElement changePassword;

	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	// understand the code firt (Task)
	public void editSpecificUser(String employeeName, String newEmployeeName, String newUsername, String newPassword)
			throws InterruptedException {
		// Navigate to Admin Page
		navigateToAdminPage();

		// Wait for the table to load
		waitForElementToAppear(EmpNameInTable);

		try {
			// Locate the specific employee name element in the table
			WebElement empNameWebElement = EmpNameInTable.findElement(By.xpath("//div[text()='" + employeeName + "']"));

			// empNameWebElement != null will happen if the XPath used doesn't match any
			// element in the DOM
			if (empNameWebElement != null && empNameWebElement.getText().contains(employeeName)) {
				// Locate and click the Edit button for the specific user
				WebElement specificEdit = empNameWebElement
						.findElement(By.xpath("//button[@class='oxd-icon-button oxd-table-cell-action-space'][2]"));
				specificEdit.click();
			}
		} catch (Exception e) {
			// Handle exception if the element is not found
			System.out.println("Unable to locate the employee row: ");
			return; // Exit the method as the required element was not found
		}

		waitForElementToAppear(RoleButton);
		RoleButton.click();
		ESSOption.click();

		Status.click();
		Disabled.click();

		waitForElementToAppear(empName);
		empName.sendKeys(Keys.CONTROL, "a");
		empName.sendKeys(Keys.BACK_SPACE);
		empName.sendKeys(newEmployeeName);
		waitForListOfElementsToAppear(autoCompleteOptions);
		autoCompleteOptions.get(0).click();

		waitForElementToAppear(userName);
		userName.sendKeys(Keys.CONTROL, "a");
		userName.sendKeys(Keys.BACK_SPACE);
		userName.sendKeys(newUsername);

		clickUsingActions(changePassword);

		waitForElementToAppear(password);
		password.sendKeys(newPassword);
		confirmPassword.sendKeys(newPassword);

		saveButton.click(); // Click on the Save button

		waitForElementToAppear(sucessMessage);
		System.out.println("Message after Update : " + sucessMessage.getText());
	}

	public void clickUsingActions(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

}
