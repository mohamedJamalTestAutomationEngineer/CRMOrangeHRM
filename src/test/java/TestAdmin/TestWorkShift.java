package TestAdmin;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.WorkShift;
import testingComponents.BaseTest;

public class TestWorkShift extends BaseTest {

	@Test(priority = 1)
	public void testWorkShiftCreationPositive() throws InterruptedException, AWTException {
		WorkShift workShiftObje = new WorkShift(driver);
		workShiftObje.createWorkShift("new shift1" , "10:00 AM", "04:00 PM" , "as");
		String mesg = workShiftObje.getSuccessMessage();
		Assert.assertTrue(mesg.contains("Success"));
	}
	
	@Test(priority = 2)
	public void testWorkShiftCreationNameExisted() throws InterruptedException, AWTException {
		WorkShift workShiftObje = new WorkShift(driver);
		workShiftObje.createWorkShift("new shift1" , "10:00 AM", "04:00 PM" , "as");
		String mesg = workShiftObje.getErrorMessageIfNameExisted();
		Assert.assertTrue(mesg.equalsIgnoreCase("Already exists") || mesg.equalsIgnoreCase("To time should be after from time"));
	}
	
	// To time should be after from time
	// 
	@Test(priority = 3)
	public void testWorkShiftCreationInvalidShift() throws InterruptedException, AWTException {
		WorkShift workShiftObje = new WorkShift(driver);
		workShiftObje.createWorkShift("new shift4" , "09:00 PM", "05:00 PM" , "as");
		String mesg = workShiftObje.getErrorMessageIfTimeInvalid();
		Assert.assertTrue(mesg.equalsIgnoreCase("Already exists") || mesg.equalsIgnoreCase("To time should be after from time"));
	}
}
