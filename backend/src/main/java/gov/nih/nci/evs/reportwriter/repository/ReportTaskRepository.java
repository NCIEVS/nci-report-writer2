package gov.nih.nci.evs.reportwriter.repository;

import org.springframework.data.repository.CrudRepository;
import gov.nih.nci.evs.reportwriter.model.ReportTask;


public interface ReportTaskRepository extends CrudRepository<ReportTask, String>{

}
