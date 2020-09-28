package com.java.actionMethods;

import org.openqa.selenium.WebDriver;
import com.java.pageObjects.MyProfilePageObjects;
import com.java.util.GenericMethods;
import com.relevantcodes.extentreports.ExtentTest;

/***
*
* @author ssoni
*
*/

public class MyProfilePageActions {

	GenericMethods gm = new GenericMethods();

	public void clickOnResumeButton(WebDriver driver, ExtentTest logger) throws Exception {
		gm.waitForMilliSec(1000);
		gm.waitForElementExistence(driver, MyProfilePageObjects.resumeButton, "Resume Button", logger);
		gm.clickElement(driver, logger, MyProfilePageObjects.resumeButton, "Clickk on Resume Button");
	}

}
