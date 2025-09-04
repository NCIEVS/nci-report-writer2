package gov.nih.nci.evs.reportwriter.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.model.sparql.Bindings;
import gov.nih.nci.evs.reportwriter.core.model.sparql.Sparql;
import gov.nih.nci.evs.reportwriter.core.service.QueryBuilderServiceImpl;
import gov.nih.nci.evs.reportwriter.core.service.ReportWriter;

@Service
/** Generates a report based on a report template file. */
public class ReportWriterRunner {

  private static final Logger log = LoggerFactory.getLogger(ReportWriter.class);

  public ReportWriterRunner() {}

  // https://sparql-evs-dev.nci.nih.gov/sparql http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl
  QueryBuilderServiceImpl queryBuilderService = new QueryBuilderServiceImpl();
  RESTUtils restUtils = new RESTUtils();

  public EvsVersionInfo getEvsVersionInfo(String restURL) {
    String queryPrefix = queryBuilderService.contructPrefix();
    String query = queryBuilderService.constructVersionInfoQuery();
    String res = restUtils.runSPARQL(queryPrefix + query, restURL);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    EvsVersionInfo evsVersionInfo = new EvsVersionInfo();
    try {
      Sparql sparqlResult = mapper.readValue(res, Sparql.class);
      Bindings[] bindings = sparqlResult.getResults().getBindings();
      for (Bindings b : bindings) {
        evsVersionInfo.setVersion(b.getVersion().getValue());
        evsVersionInfo.setDate(b.getDate().getValue());
        evsVersionInfo.setComment(b.getComment().getValue());
      }
    } catch (Exception ex) {
      System.out.println("Bad News Exception");
      System.out.println(ex);
    }
    return evsVersionInfo;
  }

  public static List readFile(String filename) {
    List list = new ArrayList();
    try {
      BufferedReader in =
          new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
      String str;
      while ((str = in.readLine()) != null) {
        list.add(str);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return list;
  }

  /*
  	public static void main(String[] args) {
  		long ms = System.currentTimeMillis();
  		String templateFile = args[0];
  		System.out.println("templateFile: " + templateFile);
  		int n = templateFile.lastIndexOf(".");
  		String outputFile = templateFile.substring(0, n);
  		System.out.println("outputFile: " + outputFile);
  		String conceptFile = null;
  		String restURL = args[1];
  		System.out.println("restURL: " + restURL);
  		String namedGraph = args[2];
  		System.out.println("namedGraph: " + namedGraph);
  		List codes = null;
  		if (args.length == 4) {
  			String datafile = args[3];
              codes = readFile(datafile);
  		}
  		String str = null;
          SparqlQueryManagerService sparqlQueryManagerService = SpringUtils.createSparqlQueryManagerService();
  		if (codes != null) {
  		    str = new ReportWriter(sparqlQueryManagerService).run_report(codes, templateFile, outputFile, conceptFile, restURL, namedGraph);
  		} else {
  		    str = new ReportWriter(sparqlQueryManagerService).runReport(templateFile, outputFile, conceptFile, restURL, namedGraph);
  	    }
  		System.out.println(str);
  		System.out.println("Total run time (ms): " + (System.currentTimeMillis() - ms));
  	}
  */
}
