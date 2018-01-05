package gov.nih.nci.evs.reportwriter.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateConceptList;


public interface ReportTemplateConceptListRepository extends CrudRepository<ReportTemplateConceptList, String>{
	
	List<ReportTemplateConceptList> getReportTemplateConceptListsByReportTemplateId(@Param("reportTemplateId") Integer reportTemplateId);

}
