package gov.nih.nci.evs.reportwriter.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;


public interface ReportTemplateRepository extends CrudRepository<ReportTemplate, Integer>{
	
	List<ReportTemplate> findAllOrderByIdDesc();

}
