package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkAssociation;
import gov.nih.nci.evs.reportwriter.web.repository.LkAssociationRepository;

@Service
public class LkAssociationServiceImpl implements LkAssociationService {
	@Autowired
	LkAssociationRepository lkAssocationRepository;
	
	public List <LkAssociation> findAll() {
		
		return (List) lkAssocationRepository.findAll();
	}

}
