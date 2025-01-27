package TestAdmin;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Test;

import Admin.OrganizationInfo;
import testingComponents.BaseTest;

public class TestOrganization extends BaseTest {

	@Test
	public void testOrgPositive() throws InterruptedException, AWTException, IOException {
		OrganizationInfo orgInfo = new OrganizationInfo(driver);
		orgInfo.editOrganization("Tonga");  // Tonga , American Samoa , Algeria , Egypt
	}
	
}
