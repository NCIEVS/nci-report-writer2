package gov.nih.nci.evs.reportwriter.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.nih.nci.evs.reportwriter.web.model.ReportTask;


public interface ReportTaskRepository extends CrudRepository<ReportTask, Integer>{
	
	List<ReportTask> findByStatusNot(@Param("status") String status);
	
	List<ReportTask> findByStatus(@Param("status") String status);

}
