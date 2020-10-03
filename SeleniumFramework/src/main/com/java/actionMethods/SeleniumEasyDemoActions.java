package com.java.actionMethods;

import org.openqa.selenium.WebDriver;
import com.java.util.GenericMethods;
import com.java.pageObjects.SeleniumEasyTestObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class SeleniumEasyDemoActions {

	GenericMethods gm = new GenericMethods();

	public void closePopup(WebDriver driver, ExtentTest logger) throws Exception {

		gm.waitForMilliSec(2000);
		if (!driver.findElements(SeleniumEasyTestObjects.closePopup).isEmpty())
			gm.clickElement(driver, logger, SeleniumEasyTestObjects.closePopup, "Click on Close Popup Button");
	}

	public void openFileDownload(WebDriver driver, ExtentTest logger) throws Exception {

		gm.clickElement(driver, logger, SeleniumEasyTestObjects.alertsModals, "Click on Generate File Button");
		gm.waitForMilliSec(2000);
		gm.clickElement(driver, logger, SeleniumEasyTestObjects.fileDownload, "Click on Download Button");

	}

	public void downloadFile(WebDriver driver, ExtentTest logger, String text) throws Exception {
		gm.enterText(driver, logger, SeleniumEasyTestObjects.textbox, text, "Enter Text");
		gm.clickElement(driver, logger, SeleniumEasyTestObjects.generateFileButton, "Click on Generate File Button");
		gm.clickElement(driver, logger, SeleniumEasyTestObjects.downloadButton, "Click on Download Button");
		gm.verifyDownloadedFiles(logger);

	}
}
