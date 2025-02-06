package TestAdmin;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.CorporateBranding;
import testingComponents.BaseTest;

public class TestCorporateBranding extends BaseTest {

	@Test
	public void testEditCorporateBranding() throws InterruptedException, AWTException, IOException {
		CorporateBranding corportateObj = new CorporateBranding(driver);
		corportateObj.editCorporateBranding("C:\\Users\\Mohamed\\Downloads\\TestUpload1.exe", "C:\\Users\\Mohamed\\Downloads\\TestUpload2.exe");
		//corportateObj.previewSettings();
		//String mesg = corportateObj.publishSettings();
		//Assert.assertTrue(mesg.contains("Success"));
	}
	

	// script for TestUpload1.exe
	/*
	 * ; use ; to put comments WinWaitActive("Open") Sleep(500)
	 * ControlSetText("Open", "", "Edit1",
	 * "C:\Users\Mohamed\Downloads\image1\PersonalPhoto.png") Sleep(500)
	 * ControlClick("Open", "", "Button1")
	 */

	// script for TestUpload2.exe
	/*
	 * ; use ; to put comments WinWaitActive("Open") Sleep(500)
	 * ControlSetText("Open", "", "Edit1",
	 * "C:\Users\Mohamed\Downloads\image2\generateImage.png") Sleep(500)
	 * ControlClick("Open", "", "Button1")
	 */

}
