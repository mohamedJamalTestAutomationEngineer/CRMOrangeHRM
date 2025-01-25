package TestAdmin;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.JobTitle;
import testingComponents.BaseTest;

public class TestJobTitle extends BaseTest {

	@Test
	public void testJobTitleCreationPositive() throws InterruptedException, AWTException {
		JobTitle JobObj = new JobTitle(driver);
		String Mesg = JobObj.createNewJobTitlePositive("test title4", "test description" , "test Noted" , 
				"C:\\Users\\Mohamed\\OneDrive\\Desktop\\window handling in selenium.txt");
	    Assert.assertTrue(Mesg.contains("Success"));
	}
	
	@Test
	public void testJobExistance() {
		JobTitle JobObj = new JobTitle(driver);
		Boolean existed = JobObj.verifyJobTitleAddedSuccessfully("Account Assistant1");
		Assert.assertTrue(existed);
	}
	
	@Test
	public void testJobTitleCreationNegative() {
		JobTitle JobObj = new JobTitle(driver);
		String errorMesg = JobObj.createJobWithExistedTitle("Account Assistant");
		Assert.assertTrue(errorMesg.equalsIgnoreCase("Already exists"));
	}
	
	@Test
	public void testUploadFileExceedSize() throws AWTException, InterruptedException {
		JobTitle JobObj = new JobTitle(driver);
		String errorMesg = JobObj.createJobWithExceededSize("C:\\Users\\Mohamed\\OneDrive\\Desktop\\Test File.pdf");
		Assert.assertTrue(errorMesg.equalsIgnoreCase("Attachment Size Exceeded"));
	}
	
	@Test
	public void testJobDeletion() {
		JobTitle JobObj = new JobTitle(driver);
		String Message =  JobObj.deleteJobTitle("Chief Technical Officer");
		//Assert.assertTrue(Message.contains("Success"));
	}
	

	// delete title using checkbox option
	// combine and refactor the methods espically the negative ones
	
}
