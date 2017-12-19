package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.model.ReportTemplateColumn;

public interface ReportTemplateColumnService {
	public List <ReportTemplateColumn> findAll();
	
	
	public List<ReportTemplateColumn> getReportColumnsByReportTemplateID(Integer reportTemplateId);

}
