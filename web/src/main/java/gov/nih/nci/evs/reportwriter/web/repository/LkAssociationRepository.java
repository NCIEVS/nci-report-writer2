package gov.nih.nci.evs.reportwriter.web.repository;

import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import gov.nih.nci.evs.reportwriter.web.model.LkAssociation;

/** The Interface LkAssociationRepository. */
// @RepositoryRestResource(collectionResourceRel = "lkassociation", path = "lkassociation")
public interface LkAssociationRepository extends CrudRepository<LkAssociation, String> {
  // n/a
}
