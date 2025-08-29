package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkReportStatus;
import gov.nih.nci.evs.reportwriter.web.repository.LkReportStatusRepository;

@Service
public class LkReportStatusServiceImpl implements LkReportStatusService {
	@Autowired
	LkReportStatusRepository lkReportStatusRepository;
	
	public List <LkReportStatus> findAll() {
		
		return (List) lkReportStatusRepository.findAll();
	}

}
