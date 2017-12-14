package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkReportStatus;
import gov.nih.nci.evs.reportwriter.repository.LkReportStatusRepository;

@Service
public class LkReportStatusServiceImpl implements LkReportStatusService {
	@Autowired
	LkReportStatusRepository lkReportStatusRepository;
	
	public List <LkReportStatus> findAll() {
		
		return (List) lkReportStatusRepository.findAll();
	}

}
