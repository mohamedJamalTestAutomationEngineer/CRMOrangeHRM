package TestAdmin;

import org.testng.annotations.Test;

import Admin.DeleteSystemsUsers;
import testingComponents.BaseTest;

public class TestDeleteSystemUsers extends BaseTest {

	@Test
	public void testDeleteUsersNegativeScenario() throws InterruptedException {
		DeleteSystemsUsers DeleteObj = new DeleteSystemsUsers(driver);
		DeleteObj.deleteSpecificUser("Fernando Gianini");
	}
	
	
}
