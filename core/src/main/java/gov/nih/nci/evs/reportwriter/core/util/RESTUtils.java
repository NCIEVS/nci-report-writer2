package gov.nih.nci.evs.reportwriter.core.util;

import java.nio.charset.Charset;
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
public class RESTUtils {

	private static final Logger log = LoggerFactory.getLogger(RESTUtils.class);

	private String username;
	private String password;
	private int readTimeout;
	private int connectTimeout;
	gov.nih.nci.evs.reportwriter.core.util.JSONUtils jsonUtils = null;

	public RESTUtils () {}

	public RESTUtils(String username, String password,int readTimeout, int connectTimeout) {
		this.username = username;
		this.password = password;
		this.readTimeout= readTimeout;
		this.connectTimeout = connectTimeout;
		this.jsonUtils = new gov.nih.nci.evs.reportwriter.core.util.JSONUtils();
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
				basicAuthorization(username,password).
				setReadTimeout(readTimeout).
				setConnectTimeout(connectTimeout).
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

/*
	public static void main(String[] args) {
		String username = ConfigurationController.username;
		String password = ConfigurationController.password;
		String serviceUrl = ConfigurationController.serviceUrl;
		String namedGraph = ConfigurationController.namedGraph;
		String restURL = ConfigurationController.restURL;
		//String serviceUrl_ctrp = ConfigurationController.serviceUrl_ctrp;

		//String filename = args[4];
		//System.out.println(serviceUrl);
		System.out.println(restURL);
		System.out.println(namedGraph);
		System.out.println(username);
		System.out.println(password);

		String filename = args[0];
		System.out.println(filename);

		int readTimeout = 100000;
		int connectTimeout = 100000;

		RESTUtils restUtils = new RESTUtils(username, password, readTimeout, connectTimeout);
		String query = loadQuery(filename, false);
		System.out.println(query);

		String json = restUtils.runSPARQL(query, restURL);
		//System.out.println(response);

        gov.nih.nci.evs.reportwriter.core.util.JSONUtils jsonUtils = new gov.nih.nci.evs.reportwriter.core.util.JSONUtils();

		Vector w = jsonUtils.parseJSON(json);
        w = jsonUtils.getResponseValues(w);
		//Vector w = new Vector();
		//w.add(response);
		Utils.saveToFile("response.txt", w);


	}
	*/
}