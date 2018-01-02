package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkReportTemplateStatus;
import gov.nih.nci.evs.reportwriter.web.repository.LkReportTemplateStatusRepository;

@Service
public class LkReportTemplateStatusServiceImpl implements LkReportTemplateStatusService {
	@Autowired
	LkReportTemplateStatusRepository lkReportTemplateStatusRepository;
	
	public List <LkReportTemplateStatus> findAll() {
		
		return (List) lkReportTemplateStatusRepository.findAll();
	}

}
