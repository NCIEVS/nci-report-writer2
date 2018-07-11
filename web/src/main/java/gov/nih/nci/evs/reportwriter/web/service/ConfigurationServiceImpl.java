package gov.nih.nci.evs.reportwriter.web.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LookUp;
import gov.nih.nci.evs.reportwriter.web.support.ConfigurationProperties;


@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
	
	
		
	
	
	public ConfigurationProperties getConfigurationProperties() {
		ConfigurationProperties configurationProperties = new ConfigurationProperties();
		
		List <LookUp> lookUps = new ArrayList <LookUp> ();
		
		//code needs to be replaced
		List<String> graphNamesStr = new ArrayList <String> ();
		graphNamesStr.add("http://NCI_T");
		graphNamesStr.add("http://NCI_T2");
		
		for (String graphName:graphNamesStr) {
			lookUps.add(new LookUp(graphName,graphName));
		}
		
		configurationProperties.setGraphNames(lookUps);
		return configurationProperties;
	}
}
