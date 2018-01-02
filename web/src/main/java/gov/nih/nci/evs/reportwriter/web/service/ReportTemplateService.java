package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;

public interface ReportTemplateService {
	
	public List <ReportTemplate> findAll();
	
	public ReportTemplate findOne(Integer id);
	
	public ReportTemplateUI create(ReportTemplateUI reportTemplate);
	
	public ReportTemplateUI save(ReportTemplateUI reportTemplate);

}
