package gov.nih.nci.evs.reportwriter.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;


public interface ReportTemplateColumnRepository extends CrudRepository<ReportTemplateColumn, Integer>{
	
	
	List<ReportTemplateColumn> getReportColumnsByReportTemplateID(@Param("reportTemplateId") Integer reportTemplateId);

}
