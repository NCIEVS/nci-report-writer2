package gov.nih.nci.evs.reportwriter.program;


import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import gov.nih.nci.evs.reportwriter.core.properties.CoreProperties;
import gov.nih.nci.evs.reportwriter.core.service.ReportWriter;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"gov.nih.nci.evs.reportwriter"})
public class ReportWriterProgramApplication {

	private static final Logger log = LoggerFactory.getLogger(ReportWriterProgramApplication.class);

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	ReportWriter reportWriter;

	public static void main(String[] args) {
		//SpringApplication.run(ReportWriterProgramApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ReportWriterProgramApplication.class, args);
		ReportWriterProgramApplication app = context.getBean(ReportWriterProgramApplication.class);
		Option templateOption = Option.builder("t")
				.longOpt("templateFile")
				.numberOfArgs(1)
				.required(true)
				.type(String.class)
				.desc("Template File Name")
				.build();
		Option outputOption = Option.builder("o")
				.longOpt("outputFile")
				.numberOfArgs(1)
				.required(true)
				.type(String.class)
				.desc("Output File Name")
				.build();
		Option conceptOption = Option.builder("l")
				.longOpt("conceptFile")
				.numberOfArgs(1)
				.required(false)
				.type(String.class)
				.desc("Concept List File Name")
				.build();
		Option namedGraphOption = Option.builder("g")
				.longOpt("namedGraph")
				.numberOfArgs(1)
				.required(false)
				.type(String.class)
				.desc("Named Graph")
				.build();
		Option restURLOption = Option.builder("r")
				.longOpt("restURL")
				.numberOfArgs(1)
				.required(false)
				.type(String.class)
				.desc("REST URL")
				.build();
		Options options = new Options();
		options.addOption(templateOption);
		options.addOption(outputOption);
		options.addOption(conceptOption);
		options.addOption(namedGraphOption);
		options.addOption(restURLOption);
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmdLine = parser.parse(options, args);
			String templateFileName = (String) cmdLine.getParsedOptionValue("templateFile");
			String outputFileName = (String) cmdLine.getParsedOptionValue("outputFile");
			String conceptFileName = (String) cmdLine.getParsedOptionValue("conceptFile");
			String namedGraph = (String) cmdLine.getParsedOptionValue("namedGraph");
			String restURL = (String) cmdLine.getParsedOptionValue("restURL");
			app.start(templateFileName,outputFileName,conceptFileName,restURL, namedGraph);
		} catch (ParseException ex) {
			System.err.println("Parsing the command line options failed");
			System.err.println("Reason: " + ex.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Starts the main processing after input options have been verified.
	 *
	 * @param templateFileName Name of the template file to process
	 * @param outputFileName Name of the output file
	 * @param conceptFileName Name of the file containing concept codes (optional)
	 */
	private void start(String templateFileName, String outputFileName, String conceptFileName, String restURL, String namedGraph) {
   		String templateFile = coreProperties.getTemplateDirectory() + "/" + templateFileName;
   		String outputFile = coreProperties.getOutputDirectory() + "/" + outputFileName;
        log.info("Starting ReportWriter");
        log.info("Template File: " + templateFile);
        log.info("Output File: " + outputFile);
        System.out.println("Starting ReportWriter");
        System.out.println("Template File: " + templateFile);
        System.out.println("Output File: " + outputFile);
        long startTime = System.currentTimeMillis();
        String result = reportWriter.runReport(templateFile,outputFile,conceptFileName, restURL, namedGraph);
        if (result.equals("success")) {
        		long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            log.info("");
            log.info("************************************************");
            log.info("Start Date: " + new Date(startTime));
            log.info("Time Running: " + totalTime + " milliseconds  " + (totalTime/1000) + " seconds");
            log.info("End Date: " + new Date(endTime));
        		log.info("ReportWriter completed successfully");
            System.out.println("");
            System.out.println("************************************************");
            System.out.println("Start Date: " + new Date(startTime));
            System.out.println("Time Running: " + totalTime + " milliseconds  " + (totalTime/1000) + " seconds");
            System.out.println("End Date: " + new Date(endTime));
        		System.out.println("ReportWriter completed successfully");
        		System.exit(0);
        } else {
        	    	System.out.println("ReportWriter failed to complete");
        	    	System.exit(1);
        }
	}
}
