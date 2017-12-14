package gov.nih.nci.evs.reportwriter.repository;

import org.springframework.data.repository.CrudRepository;
import gov.nih.nci.evs.reportwriter.model.LkProperty;


public interface LkPropertyRepository extends CrudRepository<LkProperty, String>{

}