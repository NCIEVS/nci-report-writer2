package gov.nih.nci.evs.reportwriter.web.support;

import java.util.ArrayList;

/** The Class ReportTaskOutput. */
public class ReportTaskOutput {

  /** The header. */
  ArrayList<TableHeader> header;

  /** The data. */
  ArrayList<ReportData> data;

  /**
   * Gets the header.
   *
   * @return the header
   */
  public ArrayList<TableHeader> getHeader() {
    return header;
  }

  /**
   * Sets the header.
   *
   * @param header the new header
   */
  public void setHeader(ArrayList<TableHeader> header) {
    this.header = header;
  }

  /**
   * Gets the data.
   *
   * @return the data
   */
  public ArrayList<ReportData> getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param data the new data
   */
  public void setData(ArrayList<ReportData> data) {
    this.data = data;
  }
}
