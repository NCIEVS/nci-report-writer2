package gov.nih.nci.evs.reportwriter.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * The Class ConfigurationController.
 *
 * @author EVS Team
 * @version 1.0
 *     <p>Modification history: Initial implementation kim.ong@ngc.com
 */
public abstract class ConfigurationController {
  /** The sys prop. */
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);

  /** The sys prop. */
  @SuppressWarnings("unused")
  private static Properties sysProp = System.getProperties();

  /** The dom. */
  @SuppressWarnings("unused")
  private static Document dom;

  /** The properties. */
  private static Properties properties = null;

  /** The Constants. */
  public static String restURL = null;

  /** The username. */
  public static String username = null;

  /** The password. */
  public static String password = null;

  /** The read timeout. */
  public static int readTimeout = 0;

  /** The connect timeout. */
  public static int connectTimeout = 0;

  /** The monthly graph name. */
  public static String monthlyGraphName = null;

  /** The monthly query url. */
  public static String monthlyQueryUrl = null;

  /** The weekly graph name. */
  public static String weeklyGraphName = null;

  /** The weekly query url. */
  public static String weeklyQueryUrl = null;

  static {
    try {
      properties = loadProperties();
      if (properties != null) {
        restURL = properties.getProperty("restURL");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        readTimeout = Integer.parseInt(properties.getProperty("readTimeout"));
        connectTimeout = Integer.parseInt(properties.getProperty("connectTimeout"));

        monthlyGraphName = properties.getProperty("monthlyGraphName");
        monthlyQueryUrl = properties.getProperty("monthlyQueryUrl");
        weeklyGraphName = properties.getProperty("weeklyGraphName");
        weeklyQueryUrl = properties.getProperty("weeklyQueryUrl");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * To be implemented by each descendant testcase.
   *
   * @return String
   */
  protected String getTestID() {
    return "Test Case";
  }

  /**
   * Load properties.
   *
   * @return the properties
   */
  private static Properties loadProperties() {
    try {
      File f = new File("resources/Test.properties");
      if (f.exists()) {
        String propertyFile = "resources/Test.properties";
        Properties lproperties = new Properties();
        try (FileInputStream fis = new FileInputStream(new File(propertyFile)); ) {
          lproperties.load(fis);
          return lproperties;
        }

      } else {
        System.out.println("Test.properties file Does not Exists");
      }

    } catch (Exception e) {
      // n/a
    }
    return null;
  }

  /**
   * Parses the xml file.
   *
   * @param filename the filename
   */
  @SuppressWarnings("unused")
  private static void parseXMLFile(String filename) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      dom = db.parse(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
