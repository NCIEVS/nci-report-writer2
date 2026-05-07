package gov.nih.nci.evs.reportwriter.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.nih.nci.evs.reportwriter.web.properties.WebProperties;

/** The Class PropertiesConfiguration. */
@Configuration
@EnableConfigurationProperties
public class PropertiesConfiguration {

  /** The logger. */
  private static final Logger log = LoggerFactory.getLogger(PropertiesConfiguration.class);

  /** Instantiates a new properties configuration. */
  public PropertiesConfiguration() {
    log.debug("Creating instance of class PropertiesConfiguration");
  }

  /**
   * Web properties.
   *
   * @return the web properties
   */
  /*
   * WebProperties  Properties
   */
  @Bean
  @ConfigurationProperties(prefix = "gov.nih.nci.evs.reportwriter.web", ignoreUnknownFields = false)
  WebProperties webProperties() {
    return new WebProperties();
  }
}
