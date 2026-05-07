package gov.nih.nci.evs.reportwriter.core.model.evs;

/** The Class EvsAssociation. */
public class EvsAssociation {

  /** The source name. */
  // Variable declaration
  private String sourceName;

  /** The source code. */
  private String sourceCode;

  /** The association name. */
  private String associationName;

  /** The target name. */
  private String targetName;

  /** The target code. */
  private String targetCode;

  /** Instantiates a new evs association. */
  // Default constructor
  public EvsAssociation() {}

  /**
   * Instantiates a new evs association.
   *
   * @param sourceName the source name
   * @param sourceCode the source code
   * @param associationName the association name
   * @param targetName the target name
   * @param targetCode the target code
   */
  // Constructor
  public EvsAssociation(
      String sourceName,
      String sourceCode,
      String associationName,
      String targetName,
      String targetCode) {

    this.sourceName = sourceName;
    this.sourceCode = sourceCode;
    this.associationName = associationName;
    this.targetName = targetName;
    this.targetCode = targetCode;
  }

  /**
   * Sets the source name.
   *
   * @param sourceName the new source name
   */
  // Set methods
  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  /**
   * Sets the source code.
   *
   * @param sourceCode the new source code
   */
  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  /**
   * Sets the association name.
   *
   * @param associationName the new association name
   */
  public void setAssociationName(String associationName) {
    this.associationName = associationName;
  }

  /**
   * Sets the target name.
   *
   * @param targetName the new target name
   */
  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  /**
   * Sets the target code.
   *
   * @param targetCode the new target code
   */
  public void setTargetCode(String targetCode) {
    this.targetCode = targetCode;
  }

  /**
   * Gets the source name.
   *
   * @return the source name
   */
  // Get methods
  public String getSourceName() {
    return this.sourceName;
  }

  /**
   * Gets the source code.
   *
   * @return the source code
   */
  public String getSourceCode() {
    return this.sourceCode;
  }

  /**
   * Gets the association name.
   *
   * @return the association name
   */
  public String getAssociationName() {
    return this.associationName;
  }

  /**
   * Gets the target name.
   *
   * @return the target name
   */
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * Gets the target code.
   *
   * @return the target code
   */
  public String getTargetCode() {
    return this.targetCode;
  }
}
