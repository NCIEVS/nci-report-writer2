package gov.nih.nci.evs.reportwriter.repository;

import org.springframework.data.repository.CrudRepository;
import gov.nih.nci.evs.reportwriter.model.ReportTemplate;


public interface ReportTemplateRepository extends CrudRepository<ReportTemplate, String>{

}
