package gov.nih.nci.evs.reportwriter.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Class GraphdbProperties. */
public class GraphdbProperties {

  /** The logger. */
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(GraphdbProperties.class);

  /** The username. */
  private String username;

  /** The password. */
  private String password;

  /** The monthly query url. */
  private String monthlyQueryUrl;

  /** The weekly query url. */
  private String weeklyQueryUrl;

  /** The read timeout. */
  private int readTimeout;

  /** The connect timeout. */
  private int connectTimeout;

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the read timeout.
   *
   * @return the read timeout
   */
  public int getReadTimeout() {
    return readTimeout;
  }

  /**
   * Sets the read timeout.
   *
   * @param readTimeout the new read timeout
   */
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  /**
   * Gets the connect timeout.
   *
   * @return the connect timeout
   */
  public int getConnectTimeout() {
    return connectTimeout;
  }

  /**
   * Sets the connect timeout.
   *
   * @param connectTimeout the new connect timeout
   */
  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  /**
   * Gets the monthly query url.
   *
   * @return the monthly query url
   */
  public String getMonthlyQueryUrl() {
    return monthlyQueryUrl;
  }

  /**
   * Sets the monthly query url.
   *
   * @param monthlyQueryUrl the new monthly query url
   */
  public void setMonthlyQueryUrl(String monthlyQueryUrl) {
    this.monthlyQueryUrl = monthlyQueryUrl;
  }

  /**
   * Gets the weekly query url.
   *
   * @return the weekly query url
   */
  public String getWeeklyQueryUrl() {
    return weeklyQueryUrl;
  }

  /**
   * Sets the weekly query url.
   *
   * @param weeklyQueryUrl the new weekly query url
   */
  public void setWeeklyQueryUrl(String weeklyQueryUrl) {
    this.weeklyQueryUrl = weeklyQueryUrl;
  }
}
