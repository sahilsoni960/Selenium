package com.java.tests;

import com.java.util.GenericMethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.java.util.PropertyFileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import atu.testrecorder.ATUTestRecorder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass {

	public static ExtentReports extentReports;
	public static ExtentTest logger;
	public static String projectDir = System.getProperty("user.dir");
	public static ATUTestRecorder recorder;
	public static WebDriver driver;
	public static ChromeDriver cdriver;
	public static FirefoxDriver fdriver;
	public GenericMethods gm = new GenericMethods();
	public PropertyFileUtils propertyFileUtils = new PropertyFileUtils();

	@BeforeSuite(alwaysRun = true, description = "Logging the application")
	public void oneTimeSetUp() throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		recorder = new ATUTestRecorder(projectDir + "\\ScriptVideos\\", "TestVideo-" + dateFormat.format(date), false);
		extentReports = new ExtentReports(gm.extentReportLocation("Automation suite Report"));
		logger = extentReports.startTest("One time set up and Login");
		if (propertyFileUtils.getPropertyValue(logger, "Configuration", "Browser").contains("Chrome"))
			driver = (ChromeDriver) gm.openBrowser("Chrome");
		else if (propertyFileUtils.getPropertyValue(logger, "Configuration", "Browser").contains("Firefox"))
			driver = (FirefoxDriver) gm.openBrowser("Firefox");
		recorder.start();
		propertyFileUtils.generateApis(driver, logger);		
		extentReports.endTest(logger);
	}

	@AfterSuite(alwaysRun = true, description = "Logout and tearUp")
	public void tearUp() throws Exception {
		logger = extentReports.startTest("logout");
		extentReports.endTest(logger);
		extentReports.flush();
		recorder.stop();
		driver.quit();
	}
}
