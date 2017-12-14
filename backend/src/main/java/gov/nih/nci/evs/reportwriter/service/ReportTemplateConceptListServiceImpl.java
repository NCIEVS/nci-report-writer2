package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.ReportTemplateConceptList;
import gov.nih.nci.evs.reportwriter.repository.ReportTemplateConceptListRepository;

@Service
public class ReportTemplateConceptListServiceImpl implements ReportTemplateConceptListService {
	@Autowired
	ReportTemplateConceptListRepository reportTemplateConceptListRepository;
	
	public List <ReportTemplateConceptList> findAll() {
		
		return (List) reportTemplateConceptListRepository.findAll();
	}

}
