package gov.nih.nci.evs.reportwriter.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author EVS Team
 * @version 1.0
 *
 * Modification history:
 *     Initial implementation kim.ong@ngc.com
 *
 */

abstract public class ConfigurationController {
	/** The sys prop. */
	private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);
	private static Properties sysProp = System.getProperties();

	/** The dom. */
	private static Document dom;

	/** The properties. */
	private static Properties properties = null;

	/** The Constants. */

	public static String restURL = null;
	public static String username = null;
	public static String password = null;
	public static int readTimeout = 0;
	public static int connectTimeout = 0;
	public static boolean testMode = false;

	static {
		try {
			properties = loadProperties();
			if (properties != null) {
				restURL = properties.getProperty("restURL");
				username = properties.getProperty("username");
				password = properties.getProperty("password");
				readTimeout = Integer.parseInt(properties.getProperty("readTimeout"));
				connectTimeout = Integer.parseInt(properties.getProperty("connectTimeout"));
			}

		} catch (Exception ex) {
			log.info("Tetsing mode: false");
			System.out.println("Tetsing mode: false");
		}
	}


	/**
	 * To be implemented by each descendant testcase.
	 *
	 * @return String
	 */
	protected String getTestID(){
		return "Test Case";
	}


	/**
	 * Load properties.
	 *
	 * @return the properties
	 */
	private static Properties loadProperties() {
		/*
		try{
			File f = new File("resources/Test.properties");
	        if (f.exists()) {
			    System.out.println("Test mode: true");
			    testMode = true;
				String propertyFile = "resources/Test.properties";
				Properties lproperties = new Properties();
				FileInputStream fis = new FileInputStream(new File(propertyFile));
				lproperties.load(fis);
				return lproperties;

			} else {
                System.out.println("Test.properties file Does not Exists");
                testMode = false;
			}

		} catch (Exception e){

		}
		*/
		return null;
	}

	/**
	 * Parses the xml file.
	 *
	 * @param filename the filename
	 */
	private static void parseXMLFile(String filename)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom=db.parse(filename);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}


