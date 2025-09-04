package gov.nih.nci.evs.reportwriter.core.model.evs;

/** The Class EvsSupportedRole. */
public class EvsSupportedRole {

  /** The name. */
  // Variable declaration
  private String name;

  /** The code. */
  private String code;

  /** Instantiates a new evs supported role. */
  // Default constructor
  public EvsSupportedRole() {}

  /**
   * Instantiates a new evs supported role.
   *
   * @param name the name
   * @param code the code
   */
  // Constructor
  public EvsSupportedRole(String name, String code) {

    this.name = name;
    this.code = code;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  // Set methods
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the code.
   *
   * @param code the new code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  // Get methods
  public String getName() {
    return this.name;
  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return this.code;
  }
}
