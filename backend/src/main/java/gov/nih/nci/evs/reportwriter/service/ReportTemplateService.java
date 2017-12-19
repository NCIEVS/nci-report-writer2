package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.support.ReportTemplateUI;

public interface ReportTemplateService {
	
	public List <ReportTemplate> findAll();
	
	public ReportTemplate findOne(Integer id);
	
	public ReportTemplateUI save(ReportTemplate reportTemplate);

}
