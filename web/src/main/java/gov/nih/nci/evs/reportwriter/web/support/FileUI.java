package gov.nih.nci.evs.reportwriter.web.support;

import java.io.InputStream;

/** The Class FileUI. */
public class FileUI implements java.io.Serializable {

  /** The file path. */
  private String filePath;

  /** The raw file stream. */
  private InputStream rawFileStream;

  /** The file name. */
  private String fileName;

  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Sets the file name.
   *
   * @param fileName the new file name
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Gets the raw file stream.
   *
   * @return the raw file stream
   */
  public InputStream getRawFileStream() {
    return rawFileStream;
  }

  /**
   * Sets the raw file stream.
   *
   * @param rawFileStream the new raw file stream
   */
  public void setRawFileStream(InputStream rawFileStream) {
    this.rawFileStream = rawFileStream;
  }

  /**
   * Gets the file path.
   *
   * @return the file path
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Sets the file path.
   *
   * @param filePath the new file path
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
