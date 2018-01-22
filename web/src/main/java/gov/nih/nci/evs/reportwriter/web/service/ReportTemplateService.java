package gov.nih.nci.evs.reportwriter.web.service;

import java.io.FileNotFoundException;
import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.support.FileUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;

public interface ReportTemplateService {
	
	public List <ReportTemplateUI> findAll();
	
	public ReportTemplate findOne(Integer id);
	
	public ReportTemplateUI create(ReportTemplateUI reportTemplate);
	
	public ReportTemplateUI save(ReportTemplateUI reportTemplate);

	
	
}
