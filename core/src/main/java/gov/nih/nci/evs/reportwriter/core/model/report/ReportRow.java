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
}
