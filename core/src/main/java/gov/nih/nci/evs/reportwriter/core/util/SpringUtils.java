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

public class SpringUtils {
	static String restURL = ConfigurationController.restURL;
	static String username = ConfigurationController.username;
	static String password = ConfigurationController.password;
	static int readTimeout = ConfigurationController.readTimeout;
	static int connectTimeout = ConfigurationController.connectTimeout;

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