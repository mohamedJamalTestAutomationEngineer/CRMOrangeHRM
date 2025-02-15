package TestAdmin;

import org.testng.Assert;
import org.testng.annotations.Test;

import Admin.EmailConfiguration;
import testingComponents.BaseTest;

public class TestEmailConfiguration extends BaseTest {

	@Test
	public void testEditEmailConfiguration() {
		EmailConfiguration config = new EmailConfiguration(driver);
		config.navigateToEmailConfiguration("privaliage1@Admin.com" , "128.127.1.6" , "5521" , "userName" , "password");
		String mesg = config.getSuccessMessage();
		Assert.assertTrue(mesg.contains("Success"));
	}

}
