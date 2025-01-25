package TestAdmin;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.AdminSearchPage;
import testingComponents.BaseTest;


public class TestAdminSearchPage extends BaseTest {
	AdminSearchPage adminObj;

	@Test
	public void verifySearchSystemsUsersviaName() throws InterruptedException {
		adminObj = new AdminSearchPage(driver);
		adminObj.searchSystemUsersUsingUserName("Admin");
		adminObj.searchSystemUsersUsingRole();
		adminObj.searchSystemUsersUsingEmployeeName("John");
		adminObj.searchSystemUsersUsingStatus();
	}
	
	@Test
	public void verfiyResetSearchButton() {
		adminObj = new AdminSearchPage(driver);
		adminObj.searchSystemUsersUsingRole();
		adminObj.restSearchSystemUsers();
		Assert.assertTrue(adminObj.isUsernameInputCleared());
		Assert.assertTrue(adminObj.isUserRoleisCleared());
		Assert.assertTrue(adminObj.isEmployeeNameInputCleared());
		Assert.assertTrue(adminObj.isStatusisCleared());
	}
	
	
}
