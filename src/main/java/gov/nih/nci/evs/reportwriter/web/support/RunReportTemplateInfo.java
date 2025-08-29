package gov.nih.nci.evs.reportwriter.web.support;

import java.util.ArrayList;

public class RunReportTemplateInfo {

	private String datbaseType;
	private ArrayList<ReportTemplateUI> reportTemplates;

	public ArrayList<ReportTemplateUI> getReportTemplates() {
		return reportTemplates;
	}

	public void setReportTemplates(ArrayList<ReportTemplateUI> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public String getDatbaseType() {
		return datbaseType;
	}

	public void setDatbaseType(String datbaseType) {
		this.datbaseType = datbaseType;
	}

	

}
