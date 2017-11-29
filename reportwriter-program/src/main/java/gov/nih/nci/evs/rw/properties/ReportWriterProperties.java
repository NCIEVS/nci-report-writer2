package gov.nih.nci.evs.rw.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportWriterProperties {

	/** The logger. */
    private static final Logger log = LoggerFactory.getLogger(ReportWriterProperties.class);
    
    private String templateDirectory;
    private String outputDirectory;

	public String getTemplateDirectory() {
		return templateDirectory;
	}
	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}
	public String getOutputDirectory() {
		return outputDirectory;
	}
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

}
