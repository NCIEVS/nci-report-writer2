package gov.nih.nci.evs.reportwriter.core.model.report;

import java.util.ArrayList;
import java.util.List;

/** The Class ReportRow. */
public class ReportRow {

  /** The columns. */
  List<ReportColumn> columns = new ArrayList<ReportColumn>();

  /**
   * Gets the columns.
   *
   * @return the columns
   */
  public List<ReportColumn> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the new columns
   */
  public void setColumns(List<ReportColumn> columns) {
    this.columns = columns;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  // KLO, 10272020
  public String getValue() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < columns.size(); i++) {
      ReportColumn col = columns.get(i);
      buf.append(col.getName()).append("$").append(col.getValue()).append("|");
    }
    return buf.toString();
  }
}
