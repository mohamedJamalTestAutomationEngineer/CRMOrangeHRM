package TestAdmin;

import org.testng.annotations.Test;

import Admin.CorporateBranding;
import testingComponents.BaseTest;

public class TestCorporateBranding extends BaseTest {

	@Test
	public void testEditCorporateBranding() throws InterruptedException {
		CorporateBranding corportateObj = new CorporateBranding(driver);
		corportateObj.editCorporateBranding();   
	}

}
