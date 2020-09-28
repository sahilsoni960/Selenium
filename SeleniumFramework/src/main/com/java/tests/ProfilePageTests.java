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
			logger = extentReports.startTest("Start Click Button Resume Test");
			myprofile.clickOnResumeButton(driver, logger);
			extentReports.endTest(logger);

		} catch (Throwable e) {
			gm.logFail(logger, "Exception occured:" + e.toString(), driver);
		}
	}

}
