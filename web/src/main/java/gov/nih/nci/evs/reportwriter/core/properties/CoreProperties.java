/*
 *
 */
package gov.nih.nci.evs.reportwriter.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Class CoreProperties. */
public class CoreProperties {

  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(CoreProperties.class);

  /** The template directory. */
  private String templateDirectory;

  /** The output directory. */
  private String outputDirectory;

  /**
   * Gets the template directory.
   *
   * @return the template directory
   */
  public String getTemplateDirectory() {
    return templateDirectory;
  }

  /**
   * Sets the template directory.
   *
   * @param templateDirectory the new template directory
   */
  public void setTemplateDirectory(String templateDirectory) {
    this.templateDirectory = templateDirectory;
  }

  /**
   * Gets the output directory.
   *
   * @return the output directory
   */
  public String getOutputDirectory() {
    return outputDirectory;
  }

  /**
   * Sets the output directory.
   *
   * @param outputDirectory the new output directory
   */
  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
}
