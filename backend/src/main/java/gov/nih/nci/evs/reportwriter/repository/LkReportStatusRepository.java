package gov.nih.nci.evs.reportwriter.repository;

import org.springframework.data.repository.CrudRepository;
import gov.nih.nci.evs.reportwriter.model.LkReportStatus;


public interface LkReportStatusRepository extends CrudRepository<LkReportStatus, String>{

}
