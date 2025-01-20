package WebsitePages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class AdminSearchPage extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public AdminSearchPage(WebDriver driver) {
		// The super(driver) call is used to invoke the constructor of the parent
		// AbstractComponent class (this might handle
		// some base functionality for all page objects).
		// It passes the driver argument from the categories constructor to the
		// constructor of AbstractComponent.
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// initializes the page element so that you can work directly on the element
		// without getting the NullPointerException
	}

	@FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][text()='Admin']")
    private WebElement adminMenu;
	
	@FindBy(className = "oxd-topbar-body-nav-tab")
	private WebElement userManagementMenu;
	
	@FindBy(className = "oxd-dropdown-menu")
	private WebElement usersMenu;
	
	@FindBy(xpath =  "//label[text()='Username']/following::input[1]")
	private WebElement usernameInput;
	
	@FindBy(xpath = "//label[@class='oxd-label'][text()='User Role']/following::div[1]")
	private WebElement userRoleDropdown;
	
	@FindBy(xpath = "//div[@role='option']/span[text()='ESS']")
	private WebElement essRoleOption;
	
	@FindBy(xpath = "//div[@role='option']/span[text()='Admin']")
	private WebElement adminRoleOption;
	
	@FindBy(xpath = "//label[@class='oxd-label'][text()='Status']/following::div[2]")
	private WebElement Status;
	
	@FindBy(xpath = "//div[@role='option']/span[text()='Enabled']")
	private WebElement Enabled;
	
	@FindBy(xpath = "//div[@role='option']/span[text()='Disabled']")
	private WebElement Disabled;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement searchButton;
	   
	@FindBy(css = "div.oxd-autocomplete-text-input.oxd-autocomplete-text-input input")
	private WebElement employeeName;
	
	@FindBy(css = "div.oxd-autocomplete-option span")
	private List<WebElement> autoCompleteOptions;
	
	@FindBy(className = "oxd-button--ghost")
	private WebElement resetButton;

	
	public void navigateToAdminPage() {
        waitForElementToAppear(adminMenu);
        adminMenu.click();
    }
	public void navigateToUserManagement() {
        waitForElementToAppear(userManagementMenu);
        userManagementMenu.click();
		usersMenu.click();
    }
	public void searchSystemUsersUsingUserName(String name){
		navigateToAdminPage();
        navigateToUserManagement();
        
		waitForElementToAppear(usernameInput);
		usernameInput.sendKeys(name);
		searchButton.click();
	}
	
	public void searchSystemUsersUsingRole() {
		navigateToAdminPage();
        navigateToUserManagement();
        
        waitForElementToAppear(userRoleDropdown);
        userRoleDropdown.click();
        essRoleOption.click();
        searchButton.click();
	}
	
	public void searchSystemUsersUsingEmployeeName(String empName) throws InterruptedException{
		navigateToAdminPage();
        navigateToUserManagement();
        
        waitForElementToBeClickable(employeeName);
       
		employeeName.sendKeys(empName);
		waitForListOfElementsToAppear(autoCompleteOptions);
		autoCompleteOptions.get(0).click();
		searchButton.click();
	}
	
	public void searchSystemUsersUsingStatus() throws InterruptedException{
		navigateToAdminPage();
        navigateToUserManagement();
        
        waitForElementToBeClickable(Status);
        Status.click();
        Disabled.click();
		searchButton.click();
	}
	
	public void restSearchSystemUsers() {
		resetButton.click();
	}
	
	public boolean isUsernameInputCleared() {
		return usernameInput.getText().isEmpty();
	}
	
	public boolean isUserRoleisCleared() {
	    return userRoleDropdown.getText().equals("-- Select --"); 
	}
	
	public boolean isEmployeeNameInputCleared() {
		return employeeName.getText().isEmpty();
	}
	
	public boolean isStatusisCleared() {
		return Status.getText().equals("-- Select --"); 
	}
	
	



}
