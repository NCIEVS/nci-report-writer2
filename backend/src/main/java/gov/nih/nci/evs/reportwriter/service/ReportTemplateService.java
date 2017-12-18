package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.model.ReportTemplate;

public interface ReportTemplateService {
	
	public List <ReportTemplate> findAll();
	
	public ReportTemplate findOne(Integer id);
	
	public ReportTemplate save(ReportTemplate reportTemplate);

}
