package Admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class LoginLandingPage extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public LoginLandingPage(WebDriver driver) {
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

	@FindBy(name = "username")
    private WebElement userName;
	
	@FindBy(name = "password")
	private WebElement password;
	
	@FindBy(className = "oxd-button")
	private WebElement login;
	
	public void loginToWebite(String username , String pass) {
		waitForElementToAppear(userName);
		userName.sendKeys(username);
		password.sendKeys(pass);
		login.click();
	}



}
