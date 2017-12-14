package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.ReportTask;
import gov.nih.nci.evs.reportwriter.repository.ReportTaskRepository;

@Service
public class ReportTaskServiceImpl implements ReportTaskService {
	@Autowired
	ReportTaskRepository reportTaskRepository;
	
	public List <ReportTask> findAll() {
		
		return (List) reportTaskRepository.findAll();
	}

}
