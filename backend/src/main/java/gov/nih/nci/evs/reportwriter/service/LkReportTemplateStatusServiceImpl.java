package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkReportTemplateStatus;
import gov.nih.nci.evs.reportwriter.repository.LkReportTemplateStatusRepository;

@Service
public class LkReportTemplateStatusServiceImpl implements LkReportTemplateStatusService {
	@Autowired
	LkReportTemplateStatusRepository lkReportTemplateStatusRepository;
	
	public List <LkReportTemplateStatus> findAll() {
		
		return (List) lkReportTemplateStatusRepository.findAll();
	}

}
