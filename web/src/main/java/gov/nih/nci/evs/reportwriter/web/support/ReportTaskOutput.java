package gov.nih.nci.evs.reportwriter.web.support;

import java.util.ArrayList;

public class ReportTaskOutput {
	
	ArrayList<TableHeader> header;
	
	ArrayList<ReportData> data;

	public ArrayList<TableHeader> getHeader() {
		return header;
	}

	public void setHeader(ArrayList<TableHeader> header) {
		this.header = header;
	}

	public ArrayList<ReportData> getData() {
		return data;
	}

	public void setData(ArrayList<ReportData> data) {
		this.data = data;
	}
	

}
