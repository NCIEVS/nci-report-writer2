package gov.nih.nci.evs.reportwriter.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.nih.nci.evs.reportwriter.core.properties.CoreProperties;
import gov.nih.nci.evs.reportwriter.core.properties.StardogProperties;

@Configuration
@EnableConfigurationProperties
public class CorePropertiesConfiguration {
	private static final Logger log = LoggerFactory.getLogger(CorePropertiesConfiguration.class);
	
	public CorePropertiesConfiguration() {
		log.info("Creating instance of class ReportPropertiesConfiguration");
	}
	
	@Bean
	@ConfigurationProperties(prefix = "gov.nih.nci.evs.reportwriter.core", ignoreUnknownFields = false)
	CoreProperties coreProperties() {
		return new CoreProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "gov.nih.nci.evs.reportwriter.stardog", ignoreUnknownFields = false)
	StardogProperties stardogProperties() {
		return new StardogProperties();
	}

}
