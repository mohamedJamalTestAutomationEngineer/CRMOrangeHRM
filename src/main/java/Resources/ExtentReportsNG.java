package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsNG {
	
	public static ExtentReports getReports() {
		
		// Defining the file path for the report, using the current user's directory and specifying the location
        // The report will be saved in the "reports" folder as "index.html"
		String filePath = System.getProperty("user.dir") + "\\reports\\index.html";
		// Creating an instance of ExtentSparkReporter to generate an HTML report
		ExtentSparkReporter sparkReport = new ExtentSparkReporter(filePath);
		
		// Configuring the report's document title and name
		sparkReport.config().setDocumentTitle("Document Title");
		sparkReport.config().setReportName("Report Name");
		
		// Creating an ExtentReports object that will manage the report generation
		ExtentReports extentReport = new ExtentReports();
		
		// Attaching the ExtentSparkReporter to the ExtentReports object
		extentReport.attachReporter(sparkReport);
		
		// Setting some system information that will be display
		extentReport.setSystemInfo("TesterName" , "Jamal");
		extentReport.setSystemInfo("OS", "Windows 10");
		extentReport.setSystemInfo("Environment", "ChromeDriver");
		
		// Returning the ExtentReports object that can be used for logging test results
		return extentReport;
	}

}
