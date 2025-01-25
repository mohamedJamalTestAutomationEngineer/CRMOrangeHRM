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

public class DeleteSystemsUsers extends AbstractComponent {

	private WebDriver driver; // before creating constructor , the below driver has no life , just null value
	// so we must create constructor and this constructor will take the life from
	// testing classes in the POM design
	// as we are here in the pages classes

	public DeleteSystemsUsers(WebDriver driver) {
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

	@FindBy(css = "div.oxd-autocomplete-wrapper input")
	private WebElement empName;

	// div[@class='oxd-table-cell oxd-padding-cell']/div[text()='manda user']
	@FindBy(xpath = "//div[@class='oxd-table-cell oxd-padding-cell']")
	private WebElement EmpNameInTable;

	@FindBy(xpath = "//button[@class='oxd-icon-button oxd-table-cell-action-space'][1]")
	private WebElement deleteButton;
	
	@FindBy(css = "div.oxd-toast-content")
	private WebElement errorMessage;

	public void navigateToAdminPage() {
		waitForElementToAppear(adminMenu);
		adminMenu.click();
	}

	// understand the code firt (Task)
	public void deleteSpecificUser(String employeeName) throws InterruptedException {
		// Navigate to Admin Page
		navigateToAdminPage();

		// Wait for the table to load
		waitForElementToAppear(EmpNameInTable);

		// Locate the specific employee name element in the table
		WebElement empNameWebElement = EmpNameInTable.findElement(By.xpath("//div[text()='" + employeeName + "']"));
		WebElement specificDelete = empNameWebElement
				.findElement(By.xpath("//button[@class='oxd-icon-button oxd-table-cell-action-space'][1]"));
		specificDelete.click();      
                
     // Wait for the success or error message to appear
        waitForElementToAppear(errorMessage);
        System.out.println("Delete Message : " + errorMessage.getText());
	}

}
