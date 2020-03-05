package gov.nih.nci.evs.reportwriter.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerServiceImpl;
import gov.nih.nci.evs.reportwriter.core.service.QueryBuilderServiceImpl;

import gov.nih.nci.evs.reportwriter.core.configuration.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringUtils {
	private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);

	static String restURL = null;
	static String username = null;
	static String password = null;
	static int readTimeout = 0;
	static int connectTimeout = 0;

	static {
		try {
			restURL = ConfigurationController.restURL;
			username = ConfigurationController.username;
			password = ConfigurationController.password;
			readTimeout = ConfigurationController.readTimeout;
			connectTimeout = ConfigurationController.connectTimeout;
		} catch (Exception ex) {
			log.info("Tetsing mode: false");
			System.out.println("Tetsing mode: false");
		}
	}

	public static SparqlQueryManagerServiceImpl createSparqlQueryManagerService() {
		SparqlQueryManagerServiceImpl sparqlQueryManagerService = new SparqlQueryManagerServiceImpl();
	    sparqlQueryManagerService.setQueryBuilderService(createQueryBuilderService());
	    sparqlQueryManagerService.setRESTUtils(createRESTUtils());
		return sparqlQueryManagerService;
	}

	public static QueryBuilderServiceImpl createQueryBuilderService() {
		QueryBuilderServiceImpl queryBuilderService = new QueryBuilderServiceImpl();
		return queryBuilderService;
	}

	public static RESTUtils createRESTUtils() {
		RESTUtils restUtils = new RESTUtils(username, password, readTimeout, connectTimeout);
		return restUtils;
	}
}