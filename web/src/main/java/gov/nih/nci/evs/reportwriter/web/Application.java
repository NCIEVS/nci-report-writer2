package gov.nih.nci.evs.reportwriter.web;


import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import gov.nih.nci.evs.reportwriter.web.controller.ReportWriterController;

@SpringBootApplication
@ComponentScan(basePackages = {"gov.nih.nci.evs.reportwriter"})
@EnableAsync
public class Application   {

	
	private static final Logger log = LoggerFactory.getLogger(ReportWriterController.class);
	
	
    public static void main(String[] args) {
    	log.info("In main methid of SpringApplication*****");
        SpringApplication.run(Application.class, args);
    }

   
  
    
    @Bean
    public Executor asyncExecutor() {
    	log.info("In asyncExecutor methid of SpringApplication****");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("ReportWriter-");
        executor.initialize();
        return executor;
    }
}