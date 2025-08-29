package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;

public interface ReportTemplateColumnService {
	public List <ReportTemplateColumn> findAll();
	
	
	public List<ReportTemplateColumn> getReportColumnsByReportTemplateID(Integer reportTemplateId);

}
