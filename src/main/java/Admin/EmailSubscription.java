package Admin;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AbstractComponents.AbstractComponent;

public class EmailSubscription extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes
	private Actions actions;

	public EmailSubscription(WebDriver driver) {
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

	@FindBy(xpath = "//ul[@class='oxd-dropdown-menu']/li[2]")
	private WebElement emailSubscriptions;
	
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required'][text()='Test Email Address']/ancestor::div[2]//input[1]")
	private WebElement testEmailAddress;
	
	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell'][1]")
	private List<WebElement> notificationTypes;
	
	@FindBy(className = "orangehrm-main-title")
	private WebElement subscriberTitle;
	
	@FindBy(className = "oxd-button-icon")
	private WebElement addButton;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required' and text()='Name']/following::div[1]/input")
	private WebElement subscriberName;
	
	@FindBy(xpath = "//label[@class='oxd-label oxd-input-field-required']/following::div[2]//input[1]")
	private WebElement subscriberEmail;
	
	@FindBy(className = "orangehrm-left-space")
	private WebElement saveButton;
	
	@FindBy(css = "div.oxd-toast-content")
	private WebElement sucessMessage;
	
	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell'][2]")
	private List<WebElement> subscriberNames;


	// Method to navigate to the Admin page
	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}
	
	public void navigateToEmailSubscription() {
		
		navigateToAdminPage();
		
		waitForElementToAppear(configurationMenu);
		configurationMenu.click();
		
		waitForElementToAppear(emailSubscriptions);
		emailSubscriptions.click();
	}
	
	public String NavigateToNewEmailSubscription(String Type) throws InterruptedException {

		waitForAllElementTobeInDOM(By.xpath("//div[@class='oxd-table-cell oxd-padding-cell'][1]"));
		
		// Print the size of the notificationTypes list
	    System.out.println("Number of notifications: " + notificationTypes.size());
	    
		    for (WebElement notification : notificationTypes) {
		    	System.out.println("Notification : " + notification.getText());
		        if (notification.getText().equalsIgnoreCase(Type)) {
		            WebElement action = notification.findElement(By.xpath("./following-sibling::div[2]//button"));
		            waitForElementToBeClickable(action);
		            //action.click();  // not working , I used JS instead in the below line 
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", action);
		            break; // Exit the loop once the action is performed
		        }
		    }
		    
		    //waitForElementToAppear(subscriberTitle);
		    waitForElementTobeInDOM(By.className("orangehrm-main-title"));
		    return subscriberTitle.getText();
	}
	
	public String addNewEmailSubscription(String name , String email) {
		waitForElementToAppear(addButton);
		addButton.click();
		
		waitForElementToAppear(subscriberName);
		subscriberName.sendKeys(name);
		
		waitForElementToAppear(subscriberEmail);
		subscriberEmail.sendKeys(email);
		
		saveButton.click();
		
		return getSuccessMessage();
	}
	
	public boolean verifyAddingSubscription(String name) throws InterruptedException {
		//waitForListOfElementsToAppear(subscriberNames);
		Thread.sleep(2000);
		for (WebElement subscriber : subscriberNames) {
			String subscriberName = subscriber.getText();
			if(subscriberName.equalsIgnoreCase(name))
				return true;
		}
		return false;
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