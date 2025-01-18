package TestFeatures;

import org.testng.annotations.Test;

import WebsitePages.AddSystemsUsers;
import testingComponents.BaseTest;

public class TestAddSystemUsers extends BaseTest {

	@Test
	public void testAddUsersPositiveScenario() throws InterruptedException {
		AddSystemsUsers addSystemsUsersPage = new AddSystemsUsers(driver);

		// Provide the test data here
		String employeeName = "jo";
		String username = "testUserName9";
		String password = "12345678ab";

		// Call the positive scenario method
		String result = addSystemsUsersPage.AddUsersPositiveScenario(employeeName, username, password);
		System.out.println("Positive scenario result: " + result);
		// Add assertions here if needed (for example, to check success message)
	}

	@Test
	public void testAddUsersNegativeScenario() throws InterruptedException {
		AddSystemsUsers addSystemsUsersPage = new AddSystemsUsers(driver);

		// Provide the test data here
		String employeeName = "jo";
		String username = "testUserName9";
		String password = "12345678ab";

		// Call the negative scenario method
		String result = addSystemsUsersPage.AddUsersNegativeScenario(employeeName, username, password);
		System.out.println("Negative scenario result: " + result);

		// Add assertions here if needed (for example, to check error message)
	}

}
