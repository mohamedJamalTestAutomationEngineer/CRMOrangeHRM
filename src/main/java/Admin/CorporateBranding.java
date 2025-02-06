package Admin;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class CorporateBranding extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes
	private Actions actions;

	public CorporateBranding(WebDriver driver) {
		// The super(driver) call is used to invoke the constructor of the parent
		// AbstractComponent class (this might handle
		// some base functionality for all page objects).
		// It passes the driver argument from the categories constructor to the
		// constructor of AbstractComponent.
		super(driver); // Call the parent class constructor
		this.driver = driver; // Assign the passed WebDriver instance to the local driver

		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this); // Initialize WebElements using PageFactory
		// initializes the page element so that you can work directly on the element
		// without getting the NullPointerException
	}

	@FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][text()='Admin']")
	private WebElement adminMenu;

	@FindBy(xpath = "//li[contains(@class,'oxd-topbar-body-nav-tab')][6]")
	private WebElement CorporateBrandingMenu;

	@FindBy(xpath = "//label[text()='Primary Color']/ancestor::div[1]/div")
	private WebElement PrimaryColorButton;

	@FindBy(className = "oxd-color-picker-palette")
	private WebElement colorPalette;

	@FindBy(className = "oxd-color-picker-indicator")
	private WebElement ColorIndicator;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Primary Font Color']/following-sibling::div[1]")
	private WebElement primaryFontColor;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Primary Gradient Color 1']/following-sibling::div[1]")
	private WebElement primaryGradientColor1;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Secondary Color']/following-sibling::div[1]")
	private WebElement secondaryColorButton;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Secondary Font Color']/following-sibling::div[1]")
	private WebElement secondaryFontColorButton;

	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Primary Gradient Color 2']/following-sibling::div[1]")
	private WebElement primaryGradientColor2Button;

	@FindBy(css = "input.oxd-color-picker-range")
	private WebElement colorRange;

	// @FindBy(css = "div.oxd-color-picker input[class*='oxd-input oxd-input']")
	@FindBy(xpath = "//label[@class='oxd-label oxd-color-picker-label']/following-sibling::input[1]")
	private WebElement colorBoxValue;

	@FindBy(xpath = "(//div[@class='oxd-file-button'])[1]")
	private WebElement ClientLogo;

	@FindBy(xpath = "(//div[@class='oxd-file-button'])[2]")
	private WebElement ClientBanner;

	@FindBy(xpath = "(//div[@class='oxd-file-button'])[3]")
	private WebElement LoginBanner;
	
	@FindBy(xpath = "//button[@class='oxd-button oxd-button--medium oxd-button--ghost'][2]")
	private WebElement previewButton;

	@FindBy(className = "oxd-button--secondary")
	private WebElement publishButton;
	
	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;


	// Method to navigate to the Admin page
	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	// Method to navigate to Corporate Branding section
	public void navigateToCorporateBranding() throws InterruptedException {
		waitForElementToAppear(CorporateBrandingMenu);
		CorporateBrandingMenu.click();
	}

	// Method to edit Corporate Branding settings
	public void editCorporateBranding(String filePath1 , String filePath2 ) throws InterruptedException, AWTException, IOException {
		navigateToAdminPage();
		navigateToCorporateBranding();

		// Set colors for various elements
		
		  setColorByIndicator(PrimaryColorButton, ColorIndicator);
		  setColorByIndicator(primaryFontColor, ColorIndicator);
		  setColorByIndicator(primaryGradientColor1, ColorIndicator);
		  setColorByIndicator(secondaryColorButton, ColorIndicator);
		  changeColorValue1(secondaryFontColorButton, "#251690"); // Manually set a color 
		  setColorByOffset(primaryGradientColor2Button, 50); // Adjust color range slider
		 

		 Thread.sleep(2000);
		 uploadFileUsingRobot(ClientLogo, "D:\\New folder\\Path for Testing.txt");

		System.out.println("Clicking ClientBanner...");
		uploadFileUsingAutoIT(ClientBanner, filePath1);
		Thread.sleep(2000); // Wait for the first upload to complete
		//uploadFileUsingRobot(ClientBanner,"C:\\Users\\Mohamed\\Downloads\\image1\\PersonalPhoto.png");
		
		System.out.println("Clicking LoginBanner...");
		uploadFileUsingAutoIT(LoginBanner, filePath2);
		Thread.sleep(2000); // Wait for the second upload to complete
		//uploadFileUsingRobot(LoginBanner, "C:\\Users\\Mohamed\\Downloads\\image2\\generateImage.png");
	}
	
	public void uploadFileUsingAutoIT(WebElement element, String filePath) throws IOException, InterruptedException {
        waitForElementToBeClickable(element);
        element.click();
        Runtime.getRuntime().exec(filePath);
    }
	
	public void previewSettings() throws InterruptedException {
		scrollDown(driver, 300);
		waitForElementToBeClickable(previewButton); // element is enable so we can use .click()  but here we use JS as a different way
		
		boolean isDisabled = !previewButton.isEnabled();
		System.out.println("Is the button disabled? " + isDisabled);

		// we are casting the WebDriver instance to JavascriptExecutor
		// JavascriptExecutor allows executing JavaScript code in the browser via Selenium.
		// Selenium's click() method cannot interact with disabled elements, so we use JavaScript.
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// arguments[0] refers to the previewButton, which is passed into the script.
		// This JavaScript code removes the disabled attribute from the previewButton.
		js.executeScript("arguments[0].removeAttribute('disabled');", previewButton);
		previewButton.click();
	}
	
	public String publishSettings() {
		waitForElementToBeClickable(publishButton);
		publishButton.click();
		return getSuccessMessage();
	}

	// Method to adjust the color range slider by moving the cursor horizontally
	public void adjustColorRange(int xOffset) {
		waitForElementToAppear(colorRange);
		actions.clickAndHold(colorRange).moveByOffset(xOffset, 0).release().perform();
	}

	// Method to change color value by entering hex code
	public void changeColorValue(String colorValue) {
		waitForElementToAppear(colorBoxValue);
		colorBoxValue.sendKeys(Keys.CONTROL + "a"); // Select all text
		colorBoxValue.sendKeys(Keys.DELETE); // Delete selected text
		colorBoxValue.sendKeys(colorValue); // Enter new color value
	}

	// Overloaded method to change color value using a specific WebElement
	public void changeColorValue1(WebElement colorBox, String colorValue) throws InterruptedException {
		waitForElementToAppear(colorBox);
		colorBox.click(); // Open the color picker
		waitForElementToAppear(colorBoxValue);
		colorBoxValue.sendKeys(Keys.CONTROL + "a");
		colorBoxValue.sendKeys(Keys.DELETE);
		colorBoxValue.sendKeys(colorValue);
		ensurePickerClosed();
	}

	// Method to randomly select a color from the color picker
	public void setRandomColor(WebElement cursor) {
		waitForElementToBeClickable(cursor);
		Dimension size = cursor.getSize();
		int width = size.getWidth();
		int height = size.getHeight();

		Random random = new Random();
		int offsetX = random.nextInt(width - 10) + 5; // Generate random X offset
		int offsetY = random.nextInt(height - 10) + 5; // Generate random Y offset

		actions.moveToElement(cursor, offsetX, offsetY).click().perform();
		ensurePickerClosed();
	}

	// Ensures the color picker is closed by sending an ESCAPE key
	public void ensurePickerClosed() {
		actions.sendKeys(Keys.ESCAPE).perform();
		waitForElementToDisappear(By.className("oxd-color-picker-palette"));
	}

	// Sets color by clicking on the color indicator inside the picker
	public void setColorByIndicator(WebElement colorButton, WebElement indicator) {
		waitForElementToAppear(colorButton);
		colorButton.click(); // Open color picker
		waitForElementToBeClickable(colorPalette);
		//colorPalette.click();
		//Actions actions = new Actions(driver);
		actions.moveToElement(colorPalette).click().build().perform();
		setRandomColor(indicator);
	}

	// Sets color by adjusting slider offset
	public void setColorByOffset(WebElement colorButton, int xOffset) {
		waitForElementToAppear(colorButton);
		colorButton.click();
		waitForElementToAppear(colorPalette);
		//colorPalette.click();
		actions.moveToElement(colorPalette).click().build().perform();
		adjustColorRange(xOffset);
		ensurePickerClosed();
	}
	
	public void uploadFileUsingRobot(WebElement element, String filePath) throws AWTException, InterruptedException {
	    waitForElementToBeClickable(element);
	    element.click();

	    // Wait for the file upload dialog to appear
	    Thread.sleep(2000); // Adjust the sleep time as needed

	    Robot robot = new Robot();
	    // Copy the file path to clipboard
	    StringSelection selection = new StringSelection(filePath);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

	    // Press CTRL + V
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);

	    // Press Enter
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);

	    // Wait for the file to be uploaded
	    Thread.sleep(1500);
	}

	public String getSuccessMessage() {
		waitForElementToAppear(sucessMessage);
		System.out.println("Success Message: " + sucessMessage.getText());
		return sucessMessage.getText();
	}

	// script to upload two files using AutoIT
	/*
	 * ; Read command-line arguments Local $filePath1 = $CmdLine[1] ; File path for
	 * the first button Local $filePath2 = $CmdLine[2] ; File path for the second
	 * button
	 * 
	 * ; Wait for the file upload dialog to appear WinWaitActive("Open", "", 10) ;
	 * Wait up to 10 seconds for the dialog
	 * 
	 * ; Check which button was clicked (e.g., by checking the dialog title or
	 * content) If WinExists("Open") Then ; Handle the first file upload dialog
	 * ControlSetText("Open", "", "Edit1", $filePath1) ControlClick("Open", "",
	 * "Button1") ; Click the "Open" button ; Wait for the first dialog to close
	 * WinWaitClose("Open", "", 10) ; Wait up to 10 seconds for the dialog to close
	 * EndIf
	 * 
	 * ; Wait for the file upload dialog to appear WinWaitActive("Open", "", 10) ;
	 * Wait up to 10 seconds for the dialog
	 * 
	 * If WinExists("Open") Then ; Handle the second file upload dialog
	 * ControlSetText("Open", "", "Edit1", $filePath2) ControlClick("Open", "",
	 * "Button1") ; Click the "Open" button ; Wait for the second dialog to close
	 * WinWaitClose("Open", "", 10) ; Wait up to 10 seconds for the dialog to close
	 * EndIf
	 */

}