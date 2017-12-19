package gov.nih.nci.evs.reportwriter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.nih.nci.evs.reportwriter.model.ReportTemplateColumn;


public interface ReportTemplateColumnRepository extends CrudRepository<ReportTemplateColumn, Integer>{
	
	
	List<ReportTemplateColumn> getReportColumnsByReportTemplateID(@Param("reportTemplateId") Integer reportTemplateId);

}
