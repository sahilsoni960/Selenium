package com.java.tests;

import org.testng.annotations.Test;
import com.java.actionMethods.SeleniumEasyDemoActions;

/***
 *
 * @author ssoni
 *
 */

public class SeleniumEasyDemoTests extends BaseClass {

	SeleniumEasyDemoActions demo = new SeleniumEasyDemoActions();

	@Test(description = "File Downlaod Test")
	public void testFileDownload() {
		try {
			driver.get(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI1"));
			gm.logInfo(logger, "Browser is launched", driver);
			
			logger = extentReports.startTest("Start File Download Test");
			demo.closePopup(driver, logger);
			demo.openFileDownload(driver, logger);
			demo.downloadFile(driver, logger, "File Download Test");
			gm.waitForMilliSec(2000);
			extentReports.endTest(logger);

		} catch (Throwable e) {
			gm.logFail(logger, "Exception occured:" + e.toString(), driver);
		}
	}
}
