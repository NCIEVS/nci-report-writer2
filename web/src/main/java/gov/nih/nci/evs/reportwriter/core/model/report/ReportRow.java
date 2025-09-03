package gov.nih.nci.evs.reportwriter.core.model.report;

import java.util.ArrayList;
import java.util.List;

public class ReportRow {

	List <ReportColumn> columns = new ArrayList<ReportColumn>();

	public List<ReportColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ReportColumn> columns) {
		this.columns = columns;
	}

    //KLO, 10272020
	public String getValue() {
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<columns.size(); i++) {
			ReportColumn col = (ReportColumn) columns.get(i);
			buf.append(col.getName()).append("$").append(col.getValue()).append("|");
		}
		return buf.toString();
	}
}
