package gov.nih.nci.evs.reportwriter.core.model.report;

import java.util.ArrayList;

/** The Class Report. */
public class Report {

  /** The rows. */
  private ArrayList<ReportRow> rows = new ArrayList<ReportRow>();

  /**
   * Gets the rows.
   *
   * @return the rows
   */
  public ArrayList<ReportRow> getRows() {
    return rows;
  }

  /**
   * Sets the rows.
   *
   * @param rows the new rows
   */
  public void setRows(ArrayList<ReportRow> rows) {
    this.rows = rows;
  }
}
