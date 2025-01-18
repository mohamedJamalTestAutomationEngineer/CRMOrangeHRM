package testingComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Resources.ExtentReportsNG;

//ListenersNG class extends BaseTest and implements ITestListener to define custom test listener behavior
public class ListenersNG extends BaseTest implements ITestListener {

	// Initialize ExtentReports object to create custom reports
	ExtentReports extentReportsObj = ExtentReportsNG.getReports();

	// Declare ExtentTest object for logging test status
	ExtentTest extentTests;

	// threads are used in case of parallel testing to avoid override testing results
	ThreadLocal<ExtentTest> extentestLocalThread = new ThreadLocal<ExtentTest>();

	// This method is invoked when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		// Create a new test entry in the ExtentReports with the test method name
		extentTests = extentReportsObj.createTest(result.getMethod().getMethodName());

		// Set the current test object in the thread-local variable for parallel
		// execution support
		extentestLocalThread.set(extentTests);
	}

	// This method is invoked when a test passes
	@Override
	public void onTestSuccess(ITestResult result) {
		// Log the success status of the test in the ExtentReport
		extentestLocalThread.get().log(Status.PASS, "Test is Passed");
	}

	// This method is invoked when a test fails
	@Override
	public void onTestFailure(ITestResult result) {
		// Log the failure and the exception in the ExtentReport
		extentestLocalThread.get().fail(result.getThrowable());
		String filePath = null;

		try {
			// This line of code dynamically accesses the driver field from the test class during the execution of the test and assigns it to the driver variable 
			// in the current listener class.
			
			// getTestClass() returns the ITestClass object that contains details about the test class ( let`s take TestCategories class as an example)
			
			// getRealClass() returns the actual TestCategories.class
			
			// getTestClass() gives us a reference to the ITestClass wrapper, but we need the Class<?> reference to interact with the fields and methods 
			// of the test class directly.
			
			// getField("driver") is used to access a specific field (in this case, "driver") of the TestCategories class.
			
			// get(result.getInstance()) gets the actual instance of TestCategories class running the test.
			
			// The result is that the driver field from the TestCategories instance (after casting) is assigned to the driver variable in the listener (ListenersNG), 
			// allowing the listener to access the WebDriver (e.g., for screenshot capture when the test fails).
			
			// This line of code is using reflection to access the driver object from the TestCategories class dynamically, allowing the listener to interact with
			// the WebDriver instance (e.g., take screenshots on test failure) without directly having a reference to it.
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			// Catch and print any exception while accessing the driver
			e1.printStackTrace();
		}

		try {
			// Capture screenshot for the failed test method and store the file path
			filePath = takeScreenshotMethod(result.getMethod().getMethodName(), driver);

		} catch (Exception e) {
			// Catch and print any exception while accessing the driver
			e.printStackTrace();
		}
		// Add the screenshot to the ExtentReport for the failed test method
		extentestLocalThread.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	// This method is invoked when the test execution finishes
	@Override
	public void onFinish(ITestContext context) {
		// Flush the ExtentReports to save the final results
		extentReportsObj.flush();
	}

}
