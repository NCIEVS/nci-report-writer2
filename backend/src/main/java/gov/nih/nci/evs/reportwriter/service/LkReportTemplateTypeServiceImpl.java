package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkReportTemplateType;
import gov.nih.nci.evs.reportwriter.repository.LkReportTemplateTypeRepository;

@Service
public class LkReportTemplateTypeServiceImpl implements LkReportTemplateTypeService {
	@Autowired
	LkReportTemplateTypeRepository lkReportTemplateTypeRepository;
	
	public List <LkReportTemplateType> findAll() {
		
		return (List) lkReportTemplateTypeRepository.findAll();
	}

}
