package TestAdmin;

import org.testng.annotations.Test;

import Admin.EditSystemsUsers;
import testingComponents.BaseTest;

public class TestEditSystemUsers extends BaseTest {

	@Test
	public void testAddUsersPositiveScenario() throws InterruptedException {
		EditSystemsUsers EditObj = new EditSystemsUsers(driver);
        EditObj.editSpecificUser("manda user","j" , "newAdmin","12345678a");
	}
	
}
