package Admin;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
    
    //@FindBy(css = "div.oxd-color-picker input[class*='oxd-input oxd-input']")
    @FindBy(xpath = "//label[@class='oxd-label oxd-color-picker-label']/following-sibling::input[1]")
    private WebElement colorBoxValue;
	
	@FindBy(className = "orangehrm-left-space")
	private WebElement saveButton;

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
    public void editCorporateBranding() throws InterruptedException {
        navigateToAdminPage();
        navigateToCorporateBranding();
        
        // Set colors for various elements
        setColorByIndicator(PrimaryColorButton, ColorIndicator);
        setColorByIndicator(primaryFontColor, ColorIndicator);
        setColorByIndicator(primaryGradientColor1, ColorIndicator);
        setColorByIndicator(secondaryColorButton, ColorIndicator);
        changeColorValue1(secondaryFontColorButton, "#251617"); // Manually set a color
        setColorByOffset(primaryGradientColor2Button, 10); // Adjust color range slider
    }

    // Method to adjust the color range slider by moving the cursor horizontally
    public void adjustColorRange(int xOffset) {
        waitForElementToAppear(colorRange);
        actions.clickAndHold(colorRange)
               .moveByOffset(xOffset, 0)
               .release()
               .perform();
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
        
        actions.moveToElement(cursor, offsetX, offsetY)
               .click()
               .perform();
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
        waitForElementToAppear(colorPalette);
        colorPalette.click();
        setRandomColor(indicator);
    }
    
    // Sets color by adjusting slider offset
    public void setColorByOffset(WebElement colorButton, int xOffset) {
        waitForElementToAppear(colorButton);
        colorButton.click();
        waitForElementToAppear(colorPalette);
        colorPalette.click();
        adjustColorRange(xOffset);
        ensurePickerClosed();
    }
    
}