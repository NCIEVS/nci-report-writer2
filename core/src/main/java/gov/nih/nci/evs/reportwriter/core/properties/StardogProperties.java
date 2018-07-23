package gov.nih.nci.evs.reportwriter.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StardogProperties {
	
	/** The logger. */
    private static final Logger log = LoggerFactory.getLogger(StardogProperties.class);

    private String url;
    private String username;
    private String password;
    private String monthlyQueryUrl;
    private String weeklyQueryUrl;
    private int readTimeout;
    private int connectTimeout;
    private String monthlyGraphName;
    private String weeklyGraphName;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getMonthlyQueryUrl() {
		return monthlyQueryUrl;
	}

	public void setMonthlyQueryUrl(String monthlyQueryUrl) {
		this.monthlyQueryUrl = monthlyQueryUrl;
	}

	public String getMonthlyGraphName() {
		return monthlyGraphName;
	}

	public void setMonthlyGraphName(String monthlyGraphName) {
		this.monthlyGraphName = monthlyGraphName;
	}

	public String getWeeklyQueryUrl() {
		return weeklyQueryUrl;
	}

	public void setWeeklyQueryUrl(String weeklyQueryUrl) {
		this.weeklyQueryUrl = weeklyQueryUrl;
	}

	public String getWeeklyGraphName() {
		return weeklyGraphName;
	}

	public void setWeeklyGraphName(String weeklyGraphName) {
		this.weeklyGraphName = weeklyGraphName;
	}

	
}