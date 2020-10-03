package com.java.tests;

import org.testng.annotations.Test;
import com.java.actionMethods.MyProfilePageActions;

/***
 *
 * @author ssoni
 *
 */

public class ProfilePageTests extends BaseClass {

	MyProfilePageActions myprofile = new MyProfilePageActions();

	@Test(description = "Click on Resume Button Test")
	public void testResume() {
		try {
			driver.get(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI2"));
			gm.logInfo(logger, "Browser is launched", driver);

			logger = extentReports.startTest("Start Click Button Resume Test");
			myprofile.clickOnResumeButton(driver, logger);
			gm.waitForMilliSec(2000);
			extentReports.endTest(logger);

		} catch (Throwable e) {
			gm.logFail(logger, "Exception occured:" + e.toString(), driver);
		}
	}

}
