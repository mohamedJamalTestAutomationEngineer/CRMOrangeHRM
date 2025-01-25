package Admin;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class JobTitle extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public JobTitle(WebDriver driver) {
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

	@FindBy(xpath = "//a[@class='oxd-topbar-body-nav-tab-link'][contains(text(),'Job Titles')]")
	private WebElement jobTitles;

	@FindBy(className = "oxd-button--secondary")
	private WebElement addButton;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required']/following::input[1]")
	private WebElement titleInputBox;

	@FindBy(css = "[class*='oxd-textarea--resize-vertical'][placeholder*='description']")
	private WebElement descriptionBox;

	@FindBy(className = "oxd-file-input")
	private WebElement browse;

	@FindBy(css = "[class*='oxd-textarea--resize-vertical']:nth-child(1)[placeholder='Add note']")
	private WebElement textBox;

	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell'][2]")
	private List<WebElement> Jobs;

	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell'][1]//div[@class='oxd-table-card-cell-checkbox']")
	private List<WebElement> checkboxs;

	@FindBy(className = "orangehrm-left-space")
	private WebElement saveButton;

	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;

	@FindBy(className = "oxd-input-field-error-message")
	private WebElement errorMessage;

	@FindBy(className = "oxd-button--label-danger")
	private WebElement deleteButton;

	@FindBy(className = "oxd-button--ghost")
	private WebElement NoCancelButton;

	@FindBy(css = "[class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")
	private WebElement YesCancelButton;

	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	public void navigateToJobs() {
		waitForElementToAppear(JobMenu);
		JobMenu.click();
		jobTitles.click();
	}

	public String createNewJobTitlePositive(String titleName, String descriptionValue, String textBoxValue,
			String filePath) throws AWTException {
		navigateToAdminPage();
		navigateToJobs();
		waitForElementToAppear(addButton);
		addButton.click();
		waitForElementToAppear(titleInputBox);
		titleInputBox.sendKeys(titleName);
		descriptionBox.sendKeys(descriptionValue);

		// You cannot sendKeys to a non-file input element (like a <div> or <button>).
		uploadFile(filePath);

		textBox.sendKeys(textBoxValue);

		saveButton.click();

		return getSuccessMessage();
	}

	public String createJobWithExistedTitle(String titleName) {
		navigateToAdminPage();
		navigateToJobs();
		waitForElementToAppear(addButton);
		addButton.click();
		waitForElementToAppear(titleInputBox);
		titleInputBox.sendKeys(titleName);

		return getErrorMessage();
	}

	/**
	 * Method to create a job with an exceeded file size and validate the error
	 * message.
	 *
	 * @param filePath The file path of the file to be uploaded.
	 * @return The error message displayed after attempting to upload the file.
	 * @throws AWTException         Thrown if there's an issue with the robot
	 *                              interaction (not used here but declared).
	 * @throws InterruptedException Thrown if any thread-related operation is
	 *                              interrupted.
	 */
	public String createJobWithExceededSize(String filePath) throws AWTException, InterruptedException {
		navigateToAdminPage();
		navigateToJobs();
		waitForElementToAppear(addButton);
		addButton.click();

		// Wait for the file upload element (browse) to be present in the DOM
		waitForElementTobeInDOM(By.className("oxd-file-input"));

		// Explanation: The element exists in the DOM (otherwise, you’d get a
		// NoSuchElementException), but it is not visible on the screen.
		System.out.println("Is element displayed? " + browse.isDisplayed()); // false
		// Explanation: The element is enabled and ready for interaction (e.g.,
		// clickable or editable), assuming it’s visible.
		// Even if an element is enabled, you cannot interact with it if it is not
		// displayed.
		// Selenium enforces visibility checks before actions like click() or sendKeys()
		System.out.println("Is element enabled? " + browse.isEnabled()); // true
		// The file upload element (browse) exists in the DOM (enabled = true), but it
		// is not visible (displayed = false).
		// This means Selenium’s standard click() or sendKeys() methods will fail
		// because they require the element to be visible.

		// Use JavaScript to modify the style of the file upload element to make it
		// visible
		// JavascriptExecutor is an interface in Selenium that allows you to run custom
		// JavaScript code directly in the browser.

		// style.display='block';
		// This is modifying the CSS property display of the element.
		// The value 'block' makes the element visible on the screen.
		// Elements with display: none; are hidden from view, and Selenium cannot
		// interact with them. By changing the display property to 'block',
		// the element becomes visible.

		// browse: This is the WebElement being passed as arguments[0] to the JavaScript
		// code. Essentially, the script applies the style.display='block';
		// to the browse element.
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", browse);
		// Interact with the now-visible file upload element to upload the specified
		// file
		browse.sendKeys(filePath);

		// uploadFile(filePath);

		return getErrorMessage();
	}

	/**
	 * Handles the file upload process by simulating user actions to interact with
	 * the system file dialog.
	 *
	 * This method uses JavaScript to trigger a click on the file upload button (if
	 * it is not directly clickable) and the Robot class to handle the system file
	 * chooser dialog. The file path is copied to the clipboard and pasted into the
	 * file dialog, followed by pressing Enter to complete the upload.
	 *
	 * @param filePath The full path to the file that needs to be uploaded.
	 * @throws AWTException If there is an issue creating or using the Robot class
	 *                      for simulating keyboard events.
	 *
	 *                      Steps: 1. The file upload button is clicked using
	 *                      JavaScript to handle cases where the element might not
	 *                      be directly clickable. 2. The file path is copied to the
	 *                      system clipboard using the StringSelection and Toolkit
	 *                      classes. 3. The Robot class is used to: - Paste the file
	 *                      path into the system file dialog using `CTRL + V`. -
	 *                      Confirm the file selection by pressing the `Enter` key.
	 *
	 *                      Prerequisites: - The file upload button (referenced by
	 *                      the `browse` WebElement) should be present and
	 *                      accessible in the DOM. - The Robot class requires the
	 *                      application to run in a graphical environment.
	 *
	 *                      Example Usage:
	 * 
	 *                      <pre>
	 *                      JobTitle jobTitle = new JobTitle(driver);
	 *                      jobTitle.uploadFile("C:\\path\\to\\file.txt");
	 *                      </pre>
	 */
	public void uploadFile(String filePath) throws AWTException {
		// Simulate clicking the file upload button using JavaScript
		// (Used when standard Selenium click() doesn't work)
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", browse);

		// Initialize Robot class for OS-level interactions that WebDriver can't access
		// directly
		Robot robot = new Robot();

		// Copy file path to system clipboard
		StringSelection file = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);

		// Handle OS file dialog
		robot.delay(1000); // Wait for the file dialog to appear
		// Paste path using Ctrl+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		// Submit with Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public boolean verifyJobTitleAddedSuccessfully(String jobTitle) {
		navigateToAdminPage();
		navigateToJobs();

		// Wait for job title cells to load
		waitForListOfElementsToAppear(Jobs);

		// Trim the job title once to avoid repeated calls
		String trimmedJobTitle = jobTitle.trim();

		// Iterate through all job title cells
		for (WebElement job : Jobs) {
			String jobText = job.getText().trim(); // Trim to handle extra spaces

			// Directly check if the job title matches
			if (jobText.equalsIgnoreCase(trimmedJobTitle)) {
				System.out.println("Job title found: " + jobText);
				return true;
			}
		}

		System.out.println("Job title '" + jobTitle + "' not found.");
		return false;
	}

	public String deleteJobTitle(String titleName) {
		navigateToAdminPage();
		navigateToJobs();

		waitForListOfElementsToAppear(Jobs);

		/*
		 * // Iterate through the job titles using a foreach loop for (WebElement job :
		 * Jobs) { String jobName = job.getText(); // Get the job title text if
		 * (jobName.contains(titleName)) { // Click the checkbox corresponding to the
		 * job title int index = Jobs.indexOf(job); // Find the index of the job element
		 * checkboxs.get(index).click(); // Use the same index to click the checkbox
		 * break; // Exit the loop once the correct job title is found } }
		 */

		// Iterate through the job titles
		for (int i = 0; i < Jobs.size(); i++) {
			String jobName = Jobs.get(i).getText();
			if (jobName.contains(titleName)) {
				// Click the checkbox corresponding to the job title
				checkboxs.get(i).click();

				// deleteButton.click();
				// waitForElementToAppear(NoCancelButton);
				// NoCancelButton.click();
				// break; // Exit the loop once the correct job title is found

				// click again on delete to combine delete offer (Yes/No) in one method
				deleteButton.click();

				waitForElementToBeClickable(YesCancelButton);
				YesCancelButton.click();
				getSuccessMessage();
			}
		}

		return "Job title is not found";
	}

	private String getSuccessMessage() {
		waitForElementToAppear(sucessMessage);
		System.out.println("Success Message: " + sucessMessage.getText());
		return sucessMessage.getText();
	}

	private String getErrorMessage() {
		waitForElementToAppear(errorMessage);
		System.out.println("Error Message: " + errorMessage.getText());
		return errorMessage.getText();
	}

}