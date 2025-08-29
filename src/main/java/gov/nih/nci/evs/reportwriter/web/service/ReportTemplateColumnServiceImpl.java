package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateColumnRepository;

@Service
public class ReportTemplateColumnServiceImpl implements ReportTemplateColumnService {
	@Autowired
	ReportTemplateColumnRepository reportTemplateColumnRepository;
	
	public List <ReportTemplateColumn> findAll() {
		
		return (List) reportTemplateColumnRepository.findAll();
	}

	
	
	public List<ReportTemplateColumn> getReportColumnsByReportTemplateID(Integer reportTemplateId){
		
		
		return reportTemplateColumnRepository.getReportColumnsByReportTemplateId(reportTemplateId);
	}
}
