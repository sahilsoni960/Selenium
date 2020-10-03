package com.java.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.HashMap;
import com.java.tests.BaseClass;

/***
 *
 * @author ssoni
 *
 */

public class GPSLocationTest extends BaseClass {

	@Test(description = "Geo Location Test")
	public void geoLocationTest() {

		driver.get(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI3"));
		gm.logInfo(logger, "Browser is launched", driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		gm.waitForMilliSec(5000);
		
		if (propertyFileUtils.getPropertyValue(logger, "Configuration", "Browser").contains("Chrome")) {
			logger = extentReports.startTest("Geo Location Test started");

			try {

				HashMap<String, Object> coordinate = new HashMap<String, Object>();
				coordinate.put("latitude", 30.3079823);
				coordinate.put("longitude", -97.893803);
				coordinate.put("accuracy", 10);
				((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinate);
				driver.navigate().to(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI3"));
				js.executeScript("window.scrollBy(0,500)");
				driver.findElement(By.xpath("//*[@id=\"mapcanvas\"]/div[2]/table/tr/td[2]/button")).click();
				gm.waitForMilliSec(5000);
				coordinate.put("latitude", 40.712776);
				coordinate.put("longitude", -74.005974);
				coordinate.put("accuracy", 10);
				((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinate);
				driver.navigate().to(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI3"));
				js.executeScript("window.scrollBy(0,500)");
				driver.findElement(By.xpath("//*[@id=\"mapcanvas\"]/div[2]/table/tr/td[2]/button")).click();
				gm.waitForMilliSec(5000);
				gm.logInfo(logger, "Test Completed", driver);

			} catch (Throwable e) {
				gm.logFail(logger, "Exception occured:" + e.toString(), driver);
			}

		} else if (propertyFileUtils.getPropertyValue(logger, "Configuration", "Browser").contains("Firefox")) {

			driver.navigate().to(propertyFileUtils.getPropertyValue(logger, "Configuration", "URI3"));
			driver.findElement(By.xpath("//*[@id=\"mapcanvas\"]/div[2]/table/tr/td[2]/button")).click();
			js.executeScript("window.scrollBy(0,500)");
			gm.waitForMilliSec(5000);
		}

	}
}
