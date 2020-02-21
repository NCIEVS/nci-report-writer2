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
	private static Properties sysProp = System.getProperties();

	/** The dom. */
	private static Document dom;

	/** The properties. */
	private static Properties properties = loadProperties();

	/** The Constants. */


	public final static String restURL = properties.getProperty("restURL");
	public final static String username = properties.getProperty("username");
	public final static String password = properties.getProperty("password");
	public final static int readTimeout = Integer.parseInt(properties.getProperty("readTimeout"));
	public final static int connectTimeout = Integer.parseInt(properties.getProperty("connectTimeout"));


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
		try{
			String propertyFile = "resources/Test.properties";
			Properties lproperties = new Properties();
			FileInputStream fis = new FileInputStream(new File(propertyFile));
			lproperties.load(fis);
			return lproperties;
		} catch (Exception e){
			System.out.println("Error reading properties file");
			e.printStackTrace();
			return null;
		}
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


