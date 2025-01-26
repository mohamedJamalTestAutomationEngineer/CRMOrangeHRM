package Admin;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class WorkShift extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public WorkShift(WebDriver driver) {
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

	@FindBy(xpath = "//span[@class='oxd-topbar-body-nav-tab-item'][contains(text(),'Job')]")
	private WebElement JobMenu;

	@FindBy(xpath = "//a[@class='oxd-topbar-body-nav-tab-link'][contains(text(),'Work Shifts')]")
	private WebElement workShifts;

	@FindBy(className = "oxd-button--secondary")
	private WebElement addButton;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required']/following::input[1]")
	private WebElement shiftName;

	@FindBy(xpath = "//label[contains(text(),'From')]/ancestor::div[2]//input[1]")
	private WebElement fromBox;

	@FindBy(xpath = "//label[contains(text(),'To')]/ancestor::div[2]//input[1]")
	private WebElement toBox;

	@FindBy(css = "[class*='oxd-time-hour-input-text']")
	private WebElement hourInput;

	@FindBy(css = "[class*='oxd-time-minute-input-text'")
	private WebElement minuteInput;

	@FindBy(name = "pm")
	private WebElement PM;

	@FindBy(name = "am")
	private WebElement AM;

	@FindBy(css = "[placeholder='Type for hints...']")
	private WebElement assignedEmployee;

	@FindBy(xpath = "//div[@class='oxd-autocomplete-option']//span")
	private List<WebElement> employees;

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

	public void navigateToWorkShifts() {
		waitForElementToAppear(JobMenu);
		JobMenu.click();
		workShifts.click();
	}

	public void createWorkShift(String name, String fromTime, String toTime, String employee) throws InterruptedException {
	    navigateToAdminPage();
	    navigateToWorkShifts();

	    waitForElementToAppear(addButton);
	    addButton.click();

	    waitForElementToAppear(shiftName);
	    shiftName.sendKeys(name);

	    // Parse and input "fromTime"
	    enterTime(fromBox, fromTime);

	    // Parse and input "toTime"
	    enterTime(toBox, toTime);

	    assignedEmployee.sendKeys(employee);

	    waitForListOfElementsToAppear(employees);
	    employees.get(0).click();

	    saveButton.click();
	}

	private void enterTime(WebElement timeBox, String time) {
		/*
		 time.split("[: ]"):
		 Splits the input string time (e.g., "09:45 AM") into parts using the delimiters : (colon) and space.
		 The regular expression [: ] matches both a colon (:) and a space ( ).
		 Example input "09:45 AM" splits into ["09", "45", "AM"].
			*/
	    String[] timeParts = time.split("[: ]");
	    String hour = timeParts[0];    // 09
	    String minute = timeParts[1];  // 45
	    String period = timeParts[2];  // AM

	    timeBox.click();
	    hourInput.sendKeys(Keys.CONTROL + "a");
	    hourInput.sendKeys(Keys.BACK_SPACE);
	    hourInput.sendKeys(hour);

	    minuteInput.sendKeys(Keys.CONTROL + "a");
	    minuteInput.sendKeys(Keys.BACK_SPACE);
	    minuteInput.sendKeys(minute);

	    if (period.equalsIgnoreCase("AM")) {
	        AM.click();
	    } else if (period.equalsIgnoreCase("PM")) {
	        PM.click();
	    }
	}


	public String getSuccessMessage() {
		waitForElementToAppear(sucessMessage);
		System.out.println("Success Message: " + sucessMessage.getText());
		return sucessMessage.getText();
	}

	public String getErrorMessageIfNameExisted() {
		waitForElementToAppear(errorMessage);
		System.out.println("Error Message: " + errorMessage.getText());
		return errorMessage.getText();
	}

	public String getErrorMessageIfTimeInvalid() {
		waitForElementToAppear(timeShiftValidation);
		System.out.println("Error Message : " + timeShiftValidation.getText());
		return timeShiftValidation.getText();
	}

}