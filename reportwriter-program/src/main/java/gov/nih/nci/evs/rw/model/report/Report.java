package gov.nih.nci.evs.rw.model.report;

import java.util.ArrayList;

public class Report {

	private ArrayList <ReportRow> rows = new ArrayList <ReportRow> ();

	public ArrayList<ReportRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<ReportRow> rows) {
		this.rows = rows;
	}
}
