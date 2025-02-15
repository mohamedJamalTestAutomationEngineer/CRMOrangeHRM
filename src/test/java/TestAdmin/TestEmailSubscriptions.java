package TestAdmin;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.EmailSubscription;
import testingComponents.BaseTest;

public class TestEmailSubscriptions extends BaseTest {

	String subscriberType = "Leave Applications";
	String subscribeName = "general-Leave 5";
	String subscribeEmail = "testEmail5@Email.com";
	EmailSubscription subscriptions;

	
	@Test(priority = 1)
	public void testEditEmailConfiguration() throws InterruptedException {
		subscriptions = new EmailSubscription(driver);
		subscriptions.navigateToEmailSubscription();
		String title = subscriptions.NavigateToNewEmailSubscription(subscriberType);
		System.out.println("Title: " + title); // Debug log
	    System.out.println("Subscriber Type: " + subscriberType); // Debug log
		Assert.assertTrue(title.contains(subscriberType));
	}
	
	
	@Test(priority = 2)
	public void testAddNewEmailSubscription() throws InterruptedException {
		subscriptions = new EmailSubscription(driver);
		subscriptions.addNewEmailSubscription(subscribeName , subscribeEmail);
		Assert.assertTrue(subscriptions.verifyAddingSubscription(subscribeName));
	}

	
}
