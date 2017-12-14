package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.repository.ReportTemplateRepository;

@Service
public class ReportTemplateServiceImpl implements ReportTemplateService {
	@Autowired
	ReportTemplateRepository reportTemplateRepository;
	
	public List <ReportTemplate> findAll() {
		
		return (List) reportTemplateRepository.findAll();
	}

}
