package Admin;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AbstractComponents.AbstractComponent;

public class EmailConfiguration extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes
	private Actions actions;

	public EmailConfiguration(WebDriver driver) {
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

	@FindBy(xpath = "//nav[@aria-label='Topbar Menu'][1]/ul/li[7]")
	private WebElement configurationMenu;

	@FindBy(xpath = "//ul[@class='oxd-dropdown-menu']/li[1]/a")
	private WebElement emailConfiguration;
	
	@FindBy(xpath = "//input[@type='radio' and @value='smtps']")
	private WebElement secureSMTP;
	
	@FindBy(xpath = "//label[text()='Mail Sent As']/following::input[contains(@class, 'oxd-input oxd-input')][1]")
	private WebElement mailSentAs;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='SMTP Host']/ancestor::div[2]//input[1]")
	private WebElement SMTPHost;
	
	@FindBy(xpath = "//label[@class='oxd-label'][text()='SMTP Port']/ancestor::div[2]//input[1]")
	private WebElement SMTPPort;
	
	@FindBy(css = "input[value='login'][type='radio']")
	private WebElement SMTPAuthenticationYes;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='SMTP User']/ancestor::div[2]//input[1]")
	private WebElement SMTPUser;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='SMTP Password']/ancestor::div[2]//input[1]")
	private WebElement SMTPPassword;
	
	@FindBy(xpath = "(//input[@type='checkbox'])[2]")
	private WebElement sendTestMail;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Test Email Address']/ancestor::div[2]//input[1]")
	private WebElement testEmailAddress;
	
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
	public void navigateToEmailConfiguration(String email , String hostName , String PortNumber , String userName , String password)  {
		navigateToAdminPage();
		
		waitForElementToBeClickable(configurationMenu);
		configurationMenu.click();
		
		waitForElementToBeClickable(emailConfiguration);
		emailConfiguration.click();
		
		/*
		waitForElementTobeInDOM(By.xpath("//input[@type='radio' and @value='smtps']"));

		// question : why actions move to element way worked and other ways ( .click(), JS methods) not worked 
		// answer : In many modern UI frameworks (like OrangeHRM, which your page resembles), radio buttons are styled to be invisible 
		// and replaced by custom elements (<span>).Overlaying Element: The <span> element is visually on top of the radio button, preventing direct clicks on Input.
		// Explanation : How <span> Overlays <input>
		// 1- The <input> is hidden using opacity: 0, width: 0, height: 0.
		// 2- The <span> is styled to look like a radio button and placed in the same position.
		// 3- The <label> wraps them both, so clicking the <span> actually selects the <input>.
		// 4- Selenium .click() on <input> fails because it is invisible.
		// 5- Selenium .click() on <span> works because it is visible and triggers the radio button. 
		actions.moveToElement(secureSMTP).click().perform();
		
		waitForElementToAppear(mailSentAs);
		// Keys.CONTROL + "a" : This selects all text in the mailSentAs input field (similar to Ctrl + A on a keyboard)
		// Keys.BACK_SPACE    : Since all text is selected from the previous step, pressing BACKSPACE deletes the selected text
		// Selenium treats it as a concatenated sequence, meaning: It first presses CTRL, then "a", then BACKSPACE in a single command.
		clearAndSendKeys(mailSentAs, email);
		
		// instead of sendkeys based on the old findBy above , we had to relocate the SMTPHost again so we can deal with fresh version from element 
		waitForElementToAppear(By.xpath("//label[@class='oxd-label oxd-input-field-required'][text()='SMTP Host']/ancestor::div[2]//input[1]"));
		WebElement SMTPHost = driver.findElement(By.xpath("//label[@class='oxd-label oxd-input-field-required'][text()='SMTP Host']/ancestor::div[2]//input[1]"));	
		clearAndSendKeys(SMTPHost, hostName);
		
		// instead of sendkeys based on the old findBy above , we had to relocate the SMTPPort again so we can deal with fresh version from element 
		waitForElementToAppear(By.xpath("//label[@class='oxd-label'][text()='SMTP Port']/ancestor::div[2]//input[1]"));
		WebElement SMTPPort = driver.findElement(By.xpath("//label[@class='oxd-label'][text()='SMTP Port']/ancestor::div[2]//input[1]"));
		clearAndSendKeys(SMTPPort, PortNumber);
		
		
		WebElement SMTPAuthenticationYes = driver.findElement(By.cssSelector("input[value='login'][type='radio']"));
		clickUsingJavaScript(SMTPAuthenticationYes);
		
		waitForElementToAppear(SMTPUser);
		SMTPUser.sendKeys(userName);
		
		waitForElementToAppear(SMTPPassword);
		SMTPPassword.sendKeys(password);
		
		WebElement sendTestMail = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));
		clickUsingJavaScript(sendTestMail);
		
		waitForElementToAppear(testEmailAddress);
		testEmailAddress.sendKeys(email);
		*/
		
		// this method is a replacement for the above commented block 
		configureEmailSettings(email, hostName, PortNumber, userName, password);
		
		waitForElementToAppear(saveButton);
		saveButton.click();
	}
	
	// Helper method to click an element using JavaScript
    private void clickUsingJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
	
	// Helper method to clear and send keys to an input field
    private void clearAndSendKeys(WebElement element, String text) {
        waitForElementToAppear(element);
        element.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        element.sendKeys(text);
    }
    
    private void configureEmailSettings(String email, String hostName, String portNumber, String userName, String password) {
        waitForElementTobeInDOM(By.xpath("//input[@type='radio' and @value='smtps']"));
        actions.moveToElement(secureSMTP).click().perform();

        clearAndSendKeys(mailSentAs, email);
        clearAndSendKeys(SMTPHost, hostName);
        clearAndSendKeys(SMTPPort, portNumber);

        clickUsingJavaScript(SMTPAuthenticationYes);

        clearAndSendKeys(SMTPUser, userName);
        clearAndSendKeys(SMTPPassword, password);

        clickUsingJavaScript(sendTestMail);
        clearAndSendKeys(testEmailAddress, email);
    }
	
	public String getSuccessMessage() {
	    try {
	        // Wait for the success message to appear
	        waitForElementToAppear(sucessMessage);
	        // Log the success message
	        System.out.println("Success Message: " + sucessMessage.getText());
	        return sucessMessage.getText();
	    } catch (TimeoutException e) {
	        // Handle the case where the success message does not appear
	        System.out.println("Success message did not appear within the timeout period.");
	        return "suces Message is not displayed";
	    }
	}
	

}