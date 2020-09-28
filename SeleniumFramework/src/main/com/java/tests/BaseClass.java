package com.java.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.java.util.GenericMethods;
import com.java.util.PropertyFileUtils;

public class BaseClass {

	public static ExtentReports extentReports;
	public static ExtentTest logger;
	public static WebDriver driver;
	public GenericMethods gm = new GenericMethods();
	public PropertyFileUtils propertyFileUtils = new PropertyFileUtils();

	@BeforeSuite(alwaysRun = true, description = "Logging the application")
	public void oneTimeSetUp() {
		extentReports = new ExtentReports(gm.extentReportLocation("Automation suite Report"));
		logger = extentReports.startTest("One time set up and Login");
		driver = gm.openBrowser(propertyFileUtils.getPropertyValue(logger, "Configuration", "Browser"));
		propertyFileUtils.generateApis(driver, logger);
		driver.get(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI"));
		gm.logInfo(logger, "Browser is launched", driver);
		extentReports.endTest(logger);
	}

	@AfterSuite(alwaysRun = true, description = "Logout and tearUp")
	public void tearUp() {
		logger = extentReports.startTest("logout");
		extentReports.endTest(logger);
		extentReports.flush();
		driver.quit();
	}
}
