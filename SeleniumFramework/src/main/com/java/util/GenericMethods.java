package com.java.util;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import com.cedarsoftware.util.io.JsonWriter;
import org.openqa.selenium.JavascriptExecutor;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.StaleElementReferenceException;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/***
 *
 * @author ssoni
 *
 */

public class GenericMethods {

	public static File folder;
	static String projectDir = System.getProperty("user.dir");
	public static final String DATA_POOL_PATH = projectDir + "/Resources/DataSheet.xlsx";
	SoftAssert softassert = new SoftAssert();

	/**
	 * This method is used to open the
	 * 
	 * @param browserName
	 * @return
	 */

	public WebDriver openBrowser(String browserName) {
		WebDriver driver = null;
		if (browserName.equalsIgnoreCase("Chrome")) {

			System.setProperty("webdriver.chrome.driver", projectDir + "/BrowserDrivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();

		}

		return driver;
	}

	/**
	 * Prints the custom message in the Extend reports output - Pass
	 * 
	 * @param logger
	 *            - ExtendTest Logger
	 * @param msg
	 *            - Message
	 */
	public void logPass(ExtentTest logger, String msg) {
		logger.log(LogStatus.PASS, msg);
	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Pass
	 * 
	 * @param logger-
	 *            Extend logger
	 * @param msg
	 *            - Message
	 * @param driver
	 *            - WebDriver
	 */
	public void logPass(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logPass(logger, msg);
		logger.log(LogStatus.PASS, msg, image);
	}

	/**
	 * Prints the custom message in the Extend reports output - Info
	 * 
	 * @param logger
	 *            - ExtendTest Logger
	 * @param msg
	 *            - Message
	 */
	public void logInfo(ExtentTest logger, String msg) {
		logger.log(LogStatus.INFO, msg);

	}

	/**
	 * Prints the custom message in the Extend reports output - Info
	 * 
	 * @param logger
	 *            - ExtendTest Logger
	 * @param msg
	 *            - Message
	 */
	public void logJsonInfo(ExtentTest logger, String msg) {
		logger.log(LogStatus.INFO, "<pre>" + JsonWriter.formatJson(msg) + "</pre>");

	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Info
	 * 
	 * @param logger-
	 *            ExtentTest logger
	 * @param msg
	 *            - Message
	 * @param driver
	 *            - WebDriver
	 */
	public void logInfo(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logInfo(logger, msg);
		logger.log(LogStatus.INFO, msg, image);
	}

	/**
	 * This method is used to log a step as fail
	 * 
	 * @param logger-ExtentTest
	 *            logger
	 * @param msg-Message
	 *            which need to be logged
	 */
	public void logFail(ExtentTest logger, String msg) {
		logger.log(LogStatus.FAIL, msg);
	}

	/**
	 * Prints the custom message in the Extend reports output with screenshot - Fail
	 * 
	 * @param logger-
	 *            Extend logger
	 * @param msg
	 *            - Message
	 */
	public void logFail(ExtentTest logger, String msg, WebDriver driver) {
		String screenshotPath = captureScreenshot(driver);
		String image = logger.addScreenCapture(screenshotPath);
		logFail(logger, msg);
		logger.log(LogStatus.FAIL, msg, image);
		softassert.fail(msg);
	}

	/**
	 * This method is used to capture screenshot
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @return-Location of the screenshot
	 */
	public String captureScreenshot(WebDriver driver) {
		String s1 = null;
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		if (true) {
			try {

				String failureImageFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
						.format(new GregorianCalendar().getTime()) + ".png";
				String failureScreenshotDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
				String extentReportSubDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
				String extentReportDir = "Reports/extentReport" + extentReportSubDir + "/";
				String dir = extentReportDir + "Screenshot" + failureScreenshotDir + "/";
				File srcDir = new File(dir);
				if (!srcDir.exists()) {
					srcDir.mkdir();
				}
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(dir + failureImageFileName));
				s1 = "Screenshot" + failureScreenshotDir + "/" + failureImageFileName;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return s1;
	}

	/**
	 * This method is used to click Element
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param logger-ExtentTest
	 *            logger
	 * @param locator-locator
	 *            of an element
	 */
	public void clickElement(WebDriver driver, ExtentTest logger, By locator, String elementName) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		WebElement ele = driver.findElement(locator);
		if (ele != null) {
			try {
				ele.click();
				logPass(logger, "Clicked on " + elementName, driver);
			} catch (ElementNotVisibleException e) {
				logFail(logger, "ElementNotVisibleException");
			} catch (StaleElementReferenceException e) {
				logFail(logger, "StaleElementReferenceException");
			} catch (InvalidElementStateException e) {
				clickElementUsingJavaScript(driver, locator);
			}

			catch (Exception e) {
				try {
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("scroll(0, 400);");
					ele.click();
					logInfo(logger, "Scrolled 400");
				} catch (Exception e2) {
					try {
						JavascriptExecutor jse = (JavascriptExecutor) driver;
						jse.executeScript("scroll(0, 500);");
						ele.click();
						logInfo(logger, "Scrolled 500");
					} catch (Exception e3) {
						try {
							JavascriptExecutor jse = (JavascriptExecutor) driver;
							jse.executeScript("scroll(0, 600);");
							ele.click();
							logInfo(logger, "Scrolled 600");
						} catch (Exception e4) {
							try {
								JavascriptExecutor jse = (JavascriptExecutor) driver;
								jse.executeScript("scroll(0, 700);");
								ele.click();
								logInfo(logger, "Scrolled 700");

							} catch (Exception e5) {
								try {
									JavascriptExecutor jse = (JavascriptExecutor) driver;
									jse.executeScript("scroll(0, 800);");
									ele.click();
									logInfo(logger, "Scrolled 800");
								} catch (Exception ex) {
									logInfo(logger, ex.toString() + "error occured");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method is used to click Element
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param logger-ExtentTest
	 *            logger
	 * @param ele-WebElement
	 */

	public void clickElement(WebDriver driver, ExtentTest logger, WebElement ele, String elementName) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(ele));
		if (ele != null) {
			try {
				ele.click();
				logPass(logger, "Clicked on " + elementName, driver);
			} catch (ElementNotVisibleException e) {
				logFail(logger, "ElementNotVisibleException");
			} catch (StaleElementReferenceException e) {
				logFail(logger, "StaleElementReferenceException");
			} catch (InvalidElementStateException e) {
				clickElementUsingJavaScript(driver, ele);
				logFail(logger, "Element is not Clickable");
			}

			catch (Exception e) {
				try {
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("scroll(0, 400);");
					ele.click();
					logInfo(logger, "Scrolled 400");
				} catch (Exception e2) {
					try {
						JavascriptExecutor jse = (JavascriptExecutor) driver;
						jse.executeScript("scroll(0, 500);");
						ele.click();
						logInfo(logger, "Scrolled 500");
					} catch (Exception e3) {
						try {
							JavascriptExecutor jse = (JavascriptExecutor) driver;
							jse.executeScript("scroll(0, 600);");
							ele.click();
							logInfo(logger, "Scrolled 600");
						} catch (Exception e4) {
							try {
								JavascriptExecutor jse = (JavascriptExecutor) driver;
								jse.executeScript("scroll(0, 700);");
								ele.click();
								logInfo(logger, "Scrolled 700");

							} catch (Exception e5) {
								try {
									JavascriptExecutor jse = (JavascriptExecutor) driver;
									jse.executeScript("scroll(0, 800);");
									ele.click();
									logInfo(logger, "Scrolled 800");
								} catch (Exception ex) {
									logInfo(logger, ex.toString() + "error occured");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method waits until element becomes visible
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param ele-WebElement
	 *            element
	 * @param elementName-Name
	 *            of the WebElement
	 * @param logger-ExtentTest
	 *            logger
	 * @return
	 */
	public boolean waitForElementExistence(WebDriver driver, WebElement ele, String elementName, ExtentTest logger) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.visibilityOf(ele));
			logPass(logger, "Object is present : " + elementName);
			return true;
		} catch (TimeoutException e) {
			logFail(logger, "Object is not present " + elementName + " Timeout Exception occured : " + e.getMessage(),
					driver);
			return false;
		} catch (ElementNotVisibleException e) {
			logFail(logger, "Object is not visible " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (ElementNotFoundException e) {
			logFail(logger, "Object is not found " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (Exception e) {
			logFail(logger, "Exception is :" + e.getMessage(), driver);
			return false;
		}
	}

	public void waitForMilliSec(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (Exception e) {

		}
	}

	/**
	 * This method waits until element becomes visible
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param locator-
	 *            element locator
	 * @param elementName-Name
	 *            of the WebElement
	 * @param logger-ExtentTest
	 *            logger
	 * @return
	 */
	public boolean waitForElementExistence(WebDriver driver, By locator, String elementName, ExtentTest logger) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			logFail(logger,
					"Element is not present " + elementName + " Timeout Exception is occured : " + e.getMessage(),
					driver);
			return false;
		} catch (ElementNotVisibleException e) {
			logFail(logger, "Element is not visible " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (ElementNotFoundException e) {
			logFail(logger, "Element is not found " + elementName + " Exception is " + e.getMessage(), driver);
			return false;
		} catch (Exception e) {
			logFail(logger, "Exception is :" + e.getMessage(), driver);
			return false;
		}
	}

	/**
	 * This method is used to click element using javaScript
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param elementLocator-Locator
	 *            of an element
	 */
	public void clickElementUsingJavaScript(WebDriver driver, By elementLocator) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(elementLocator));

	}

	/**
	 * This method is used to click element using javaScript
	 * 
	 * @param driver-WebDriver
	 *            instance
	 * @param element-WebElement
	 */
	public void clickElementUsingJavaScript(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}

	/**
	 * This method is used to generate the ExtendReport Path
	 */

	public String extentReportLocation(String className) {

		String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
		String extentReportSubDir = new SimpleDateFormat("MMM-dd").format(new GregorianCalendar().getTime());
		String extentReportDir = "Reports/extentReport" + extentReportSubDir + "/";
		File srcDir = new File(extentReportDir);
		if (!srcDir.exists()) {
			srcDir.mkdir();
		}

		String extendReportsPath = extentReportDir + className + timeStamp + ".html";
		return extendReportsPath;
	}
}
