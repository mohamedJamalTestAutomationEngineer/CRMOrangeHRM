package testingComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import WebsitePages.LoginLandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	// WebDriver instance to interact with the browser
	public  WebDriver driver;

	public WebDriver initalizeDriver() throws IOException {

		// Create a Properties object to load configuration properties
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\GlobalData.properties");
		prop.load(fis);


		// Get the browser name from system property or properties file
		// If the system property "browser" is null (i.e., not provided by the user at runtime), then the value of prop.getProperty("browser") is used instead.
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
	
		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		// I think no need here for the wait , it will be handled in the page classes 
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		return driver;
	}

    // Takes a screenshot of the current page and saves it in the reports folder.
	public String takeScreenshotMethod(String testCaseName, WebDriver driver) throws IOException {
		// Cast WebDriver to TakesScreenshot to capture a screenshot
		TakesScreenshot ts = (TakesScreenshot) driver;
		
		// Capture screenshot as a file
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		// Define the target location for saving the screenshot
		File target = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		// Copy the captured screenshot to the target file location
		FileUtils.copyFile(source, target);
		// Return the file path of the saved screenshot
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public LoginLandingPage lanuchApplication() throws IOException, InterruptedException {
		driver = initalizeDriver();
		LoginLandingPage LandingObject = new LoginLandingPage(driver);
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		//Thread.sleep(2000);  //add wait here 
		LandingObject.loginToWebite("Admin", "admin123");
		return LandingObject;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		//driver.close();
	}

}
