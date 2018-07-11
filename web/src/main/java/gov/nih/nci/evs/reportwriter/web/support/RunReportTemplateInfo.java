package gov.nih.nci.evs.reportwriter.web.support;

import java.util.ArrayList;

public class RunReportTemplateInfo {

	private String graphName;
	private ArrayList<ReportTemplateUI> reportTemplates;

	public ArrayList<ReportTemplateUI> getReportTemplates() {
		return reportTemplates;
	}

	public void setReportTemplates(ArrayList<ReportTemplateUI> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

}
