import gov.nih.nci.evs.reportwriter.core.util.*;

import java.nio.charset.Charset;
import java.time.Duration;
import java.io.*;
import java.util.*;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//import gov.nih.nci.evs.restapi.util.*;

/**
 *
 * Helper class using the RestTemplate to call the SPARQL endpoint using a SPARQL query.
 *
 */
public class SPARQLQueryRunner {

	private static final Logger log = LoggerFactory.getLogger(SPARQLQueryRunner.class);

	private String username;
	private String password;
	private int readTimeout;
	private int connectTimeout;
	gov.nih.nci.evs.reportwriter.core.util.JSONUtils jsonUtils = null;

	public SPARQLQueryRunner () {}

	public SPARQLQueryRunner(String username, String password,int readTimeout, int connectTimeout) {
		this.username = username;
		this.password = password;
		this.readTimeout= readTimeout;
		this.connectTimeout = connectTimeout;
		this.jsonUtils = new gov.nih.nci.evs.reportwriter.core.util.JSONUtils();


		System.out.println("****************** SPARQLQueryRunner instantiated...");
	}

    public String getPrefixes() {
		StringBuffer buf = new StringBuffer();
		buf.append("PREFIX :<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>").append("\n");
		buf.append("PREFIX base:<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl>").append("\n");
		buf.append("PREFIX Thesaurus:<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>").append("\n");
		buf.append("PREFIX xml:<http://www.w3.org/XML/1998/namespace>").append("\n");
		buf.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>").append("\n");
		buf.append("PREFIX owl:<http://www.w3.org/2002/07/owl#>").append("\n");
		buf.append("PREFIX owl2xml:<http://www.w3.org/2006/12/owl2-xml#>").append("\n");
		buf.append("PREFIX protege:<http://protege.stanford.edu/plugins/owl/protege#>").append("\n");
		buf.append("PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>").append("\n");
		buf.append("PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>").append("\n");
		buf.append("PREFIX ncicp:<http://ncicb.nci.nih.gov/xml/owl/EVS/ComplexProperties.xsd#>").append("\n");
		buf.append("PREFIX dc:<http://purl.org/dc/elements/1.1/>").append("\n");
		return buf.toString();
	}


	/**
	 *
	 * Post a SPARQL query to the SPARQL endpoint and return the results.
	 *
	 * @param query SPARQL query.
	 * @return SPARQL results.
	 */
	public String runSPARQL(String query, String restURL) {
		RestTemplate restTemplate = new RestTemplateBuilder().
				rootUri(restURL).
				basicAuthentication(username,password).
				setReadTimeout(Duration.ofSeconds(readTimeout)).
				setConnectTimeout(Duration.ofSeconds(connectTimeout)).
				build();
		restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
		MultiValueMap <String,String> body = new LinkedMultiValueMap<String,String>();
		body.add("query", query);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(new MediaType("application","sparql-results+json")));
		HttpEntity<?> entity = new HttpEntity<Object>(body ,headers);
		String results = restTemplate.postForObject(restURL,entity,String.class);
		return results;
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String replaceAll(String s) {
		if (s == null) return null;
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if (c == '+') {
			    buf.append("%20");
			} else{
				buf.append(c);
			}
		}

		s = buf.toString();
		s = s.replaceAll("%28", "(");
		s = s.replaceAll("%29", ")");
		return s;
	}

    public static String encode(String query) {
		try {
			String retstr = String.format("%s", URLEncoder.encode(query, "UTF-8"));
			retstr = replaceAll(retstr);
			return retstr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String loadQuery(String filename, boolean encode) {
	    StringBuffer buf = new StringBuffer();
	    Vector v = readFile(filename);
	    for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
			if (!t.startsWith("#")) {
			    buf.append(t);
			    if (!encode) {
					buf.append("\n");
				}
		    }
		}
		String query = buf.toString();
		if (encode) {
			query = encode(query);
		}
		return query;
	}

	public static String loadQuery(String filename) {
		return loadQuery(filename, true);
	}

	public static Vector readFile(String filename)
	{
		Vector v = new Vector();
		try {
            FileReader a = new FileReader(filename);
            BufferedReader br = new BufferedReader(a);
            String line;
            line = br.readLine();
            while(line != null){
                v.add(line);
                line = br.readLine();
            }
            br.close();
		} catch (Exception ex) {
            ex.printStackTrace();
		}
		return v;
	}

	public Vector executeQuery(String query, String restURL) {
		//System.out.println("\n\nquery: " + query);
		//System.out.println("restURL: " + restURL);

		String json = runSPARQL(query, restURL);
		//System.out.println("json: " + json);

		if (json == null) return null;

		if (jsonUtils == null) {
			System.out.println("jsonUtils == null??? " );
		}
		Vector w = jsonUtils.parseJSON(json);

		if (w == null) return null;
        w = jsonUtils.getResponseValues(w);
        return w;
	}

	public Vector run_query(String restURL, String query) {
		String queryPrefix = getPrefixes();
		String res = runSPARQL(queryPrefix + query, restURL);
        Vector v = new gov.nih.nci.evs.reportwriter.core.util.JSONUtils().parseJSON(res);
        return new gov.nih.nci.evs.reportwriter.core.util.ParserUtils().getResponseValues(v);
	}

}