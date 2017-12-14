package gov.nih.nci.evs.reportwriter.repository;

import org.springframework.data.repository.CrudRepository;
import gov.nih.nci.evs.reportwriter.model.ReportTemplateColumn;


public interface ReportTemplateColumnRepository extends CrudRepository<ReportTemplateColumn, String>{

}
