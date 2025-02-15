package TestAdmin;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.EmailSubscription;
import testingComponents.BaseTest;

public class TestEmailSubscriptions extends BaseTest {

	String subscriberType = "Leave Applications";
	String subscrpers = "Subscribers";
	String subscribeName = "general-Leave 10";
	String subscribeEmail = "testEmail10@Email.com";
	EmailSubscription subscriptions;
	
	@Test(priority = 1)
	public void testEditEmailConfiguration() throws InterruptedException {
	    subscriptions = new EmailSubscription(driver);
	    subscriptions.navigateToEmailSubscription();
	    String title = subscriptions.NavigateToNewEmailSubscription(subscriberType);
	    System.out.println("Title: " + title); // Debug log
	    Assert.assertTrue(title.contains(subscriberType), "Title does not contain the expected subscriber type.");
	}
	
	@Test(priority = 2)
	public void testAddNewEmailSubscription() throws InterruptedException {
	    subscriptions = new EmailSubscription(driver);
	    String successMessage = subscriptions.addNewEmailSubscription(subscribeName, subscribeEmail);
	    System.out.println("Success Message: " + successMessage); // Debug log
	    boolean isSubscriptionAdded = subscriptions.verifyAddingSubscription(subscribeName);
	    System.out.println("Is Subscription Added: " + isSubscriptionAdded); // Debug log
	    Assert.assertTrue(isSubscriptionAdded, "Subscription was not added successfully.");
	}

	
}
