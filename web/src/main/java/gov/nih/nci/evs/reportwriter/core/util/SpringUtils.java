package gov.nih.nci.evs.reportwriter.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nih.nci.evs.reportwriter.core.configuration.ConfigurationController;
import gov.nih.nci.evs.reportwriter.core.service.QueryBuilderServiceImpl;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerServiceImpl;

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

      System.out.println("restURL: " + restURL);
      System.out.println("username: " + username);
      System.out.println("password: " + password);
      System.out.println("readTimeout: " + readTimeout);
      System.out.println("connectTimeout: " + connectTimeout);

    } catch (Exception ex) {
      ex.printStackTrace();
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
