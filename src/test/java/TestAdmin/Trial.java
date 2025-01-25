package TestAdmin;

import org.testng.annotations.Test;

import testingComponents.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.PageFactory;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	public class Trial {

	    private WebDriver driver;
	    private WebDriverWait wait;

	    // Page Elements (AdminPage)
	    private WebElement adminMenu;
	    private WebElement systemUsersLink;
	    private WebElement userRoleDropdown;
	    private WebElement essOption;
	    private WebElement searchButton;

	    @BeforeClass
	    public void setUp() {
	        // Set up the Chrome WebDriver
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--headless"); // Optional: Run headless if you don't want the browser window
	        driver = new ChromeDriver(options);
	        
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	        // Initialize the wait object
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Navigate to the application URL
	        driver.get("https://opensource-demo.orangehrmlive.com");

	        // Locate elements
	        adminMenu = driver.findElement(By.xpath("//a[@class='oxd-main-menu-item']//span[text()='Admin']"));
	        systemUsersLink = driver.findElement(By.xpath("//a[contains(@href,'viewSystemUsers')]"));
	        userRoleDropdown = driver.findElement(By.xpath("//div[@class='oxd-select-wrapper']"));
	        essOption = driver.findElement(By.xpath("//div[@class='oxd-select-text']//div[text()='ESS']"));
	        searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
	    }

	    @Test
	    public void verifySelectESSRole() {
	        // Step 1: Click on "Admin" menu
	        adminMenu.click();

	        // Step 2: Click on "System Users" link
	        systemUsersLink.click();

	        // Step 3: Wait for and click the "User Role" dropdown
	        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown));
	        userRoleDropdown.click();

	        // Step 4: Wait for and select "ESS" option
	        wait.until(ExpectedConditions.elementToBeClickable(essOption));
	        essOption.click();

	        // Step 5: Click the search button to apply the selected role
	        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
	        searchButton.click();

	        // Add any verification logic here to ensure the role is selected correctly (e.g., verifying the table contents)
	    }

	    @AfterClass
	    public void tearDown() {
	        // Quit the WebDriver after test completion
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}

	
