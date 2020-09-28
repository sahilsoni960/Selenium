package com.java.util;

import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.WebDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class PropertyFileUtils {

	private static final String userDir = System.getProperty("user.dir");
	private static final String propertyFolderLocation = userDir + "/src/main/resources/Property/";
	private static GenericMethods gm = new GenericMethods();

	/**
	 * This method fetches the value from Property file
	 *
	 * @param logger
	 * @param fileName-Name         of the propertyFile
	 * @param propertyName-property name
	 * @return-Property value in String Format
	 */
	public String getPropertyValue(ExtentTest logger, String fileName, String propertyName) {
		String value = "";
		if (!fileName.contains(".properties")) {
			fileName = fileName + ".properties";
		}
		try (InputStream input = new FileInputStream(propertyFolderLocation + fileName)) {
			Properties prop = new Properties();
			prop.load(input);
			value = prop.getProperty(propertyName);
		} catch (IOException ex) {
			gm.logFail(logger, "The" + fileName + "is not present in the " + propertyFolderLocation);
		}
		return value;
	}

	/**
	 * This method fetches all the property starting with a given name.
	 *
	 * @param logger-ExtentTest logger
	 * @param fileName-Name     of the property file
	 * @return Set of property name
	 */
	public static Set<String> getPropertyKeySet(ExtentTest logger, String fileName) {
		Properties prop = null;
		Set<String> propertiesKeySet = new HashSet<>();
		if (!fileName.contains(".properties")) {
			fileName = fileName + ".properties";
		}
		try (InputStream input = new FileInputStream(propertyFolderLocation + fileName)) {
			prop = new Properties();
			prop.load(input);
			for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements(); ) {
				String name = (String) e.nextElement();
				propertiesKeySet.add(name);
			}
		} catch (FileNotFoundException e) {
			gm.logFail(logger, "There is no File  with the name " + fileName + "present on location" + propertyFolderLocation);
		} catch (Exception e) {
			gm.logFail(logger, e.getMessage());
		}
		return propertiesKeySet;
	}

	/**
	 * This method is used to update a certain property value
	 *
	 * @param logger-ExtendTest logger
	 * @param fileName-Name     of the property
	 * @param key-The           property key which value need to be changed
	 * @param value-Updated     value
	 */
	public static void updatePropertyValue(ExtentTest logger, String fileName, String key, String value) {
		Set<String> propertiesKey = getPropertyKeySet(logger, fileName);
		if (!fileName.contains(".properties")) {
			fileName = fileName + ".properties";
		}
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(propertyFolderLocation + fileName);
			for (String tempPropertyName : propertiesKey) {
				if (tempPropertyName.contains(key)) {
					config.setProperty(tempPropertyName, value);
				}
			}
			config.save();
		} catch (Exception e) {
			gm.logFail(logger, "user is getting following error" + e.getMessage() + " while updating property");
		}
	}

	/**
	 * This method is used to fetch the property key and value pair for a property starting with certain name.
	 *
	 * @param fileName-Name  of the file
	 * @param connectionType
	 * @return
	 */
	public static Map<String, String> getNormalPropertiesAndValueForAParticularConnection(String fileName, String connectionType) {
		Properties prop = null;
		Map<String, String> connectionProperty = new HashMap<>();
		try (InputStream input = new FileInputStream(propertyFolderLocation + fileName + ".properties")) {
			prop = new Properties();
			prop.load(input);
			for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements(); ) {
				String name = (String) e.nextElement();
				if (name.toUpperCase().startsWith(connectionType.toUpperCase())) {
					if (!name.contains(".connparams")) {
						connectionProperty.put(name.split("\\.")[1], prop.getProperty(name));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connectionProperty;
	}

	/**
	 * This method is used to fetch the property key and value pair for a property starting with certain name.
	 *
	 * @param fileName-Name  of the file
	 * @param connectionType
	 * @return
	 */
	public Map<String, String> getConnparamsPropertiesAndValueForAParticularConnection(String fileName, String connectionType) {
		Properties prop = null;
		Map<String, String> connectionProperty = new HashMap<>();
		try (InputStream input = new FileInputStream(propertyFolderLocation + fileName + ".properties")) {
			prop = new Properties();
			prop.load(input);
			for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements(); ) {
				String name = (String) e.nextElement();
				if (name.startsWith(connectionType)) {
					if (name.contains(".connparams")) {
						connectionProperty.put(name.split("\\.")[2], prop.getProperty(name));
					}
				}
			}
		} catch (Exception e) {

		}
		return connectionProperty;
	}

	public void generateApis(WebDriver driver, ExtentTest logger) {
		String fileName = "Configuration.properties";
		Set<String> propertiesKey = getPropertyKeySet(logger, fileName);
		try (InputStream input = new FileInputStream(propertyFolderLocation + fileName)) {
			//PropertiesConfiguration config = new PropertiesConfiguration(propertyFolderLocation + fileName);
			Properties prop = new Properties();
			prop.load(input);
			for (String tempPropertyName : propertiesKey) {
				String value = prop.getProperty(tempPropertyName);
				if (value.startsWith("/saas")) {
					updatePropertyValue(logger, fileName, tempPropertyName, getPropertyValue(logger, fileName, "SAASAPI") + value);
				} else if (value.startsWith("/sisvc")) {
					updatePropertyValue(logger, fileName, tempPropertyName, getPropertyValue(logger, fileName, "SISVCAPI") + value);
				} else if (value.startsWith("/api/v1/Login")) {
					updatePropertyValue(logger, fileName, tempPropertyName, getPropertyValue(logger, fileName, "IDSAPI") + value);
				}
			}
		} catch (Exception ex) {
			gm.logFail(logger, "The" + fileName + "is not present in the " + propertyFolderLocation);
		}
	}
}