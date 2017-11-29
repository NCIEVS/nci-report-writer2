package gov.nih.nci.evs.rw.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.nih.nci.evs.rw.properties.ReportWriterProperties;
import gov.nih.nci.evs.rw.properties.StardogProperties;



@Configuration
@EnableConfigurationProperties
public class PropertiesConfiguration {

    /** The logger. */
    private static final Logger log = LoggerFactory.getLogger(PropertiesConfiguration.class);

    public PropertiesConfiguration() {
        log.debug("Creating instance of class PropertiesConfiguration");
    }


    /*
     * Server Properties
     */
    @Bean
    ServerProperties serverProperties() {
        return new ServerProperties();
    }
    
    /*
     * Stardog  Properties
     */
    @Bean
    @ConfigurationProperties(prefix = "nci.evs.stardog", ignoreUnknownFields = false)
    StardogProperties stardogProperties() {
        return new StardogProperties();
    }

    /*
     * ReportWriter  Properties
     */
    @Bean
    @ConfigurationProperties(prefix = "nci.evs.rw", ignoreUnknownFields = false)
    ReportWriterProperties reportWriterProperties() {
        return new ReportWriterProperties();
    }
   

}
