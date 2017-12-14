package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkAssociation;
import gov.nih.nci.evs.reportwriter.repository.LkAssociationRepository;

@Service
public class LkAssociationServiceImpl implements LkAssociationService {
	@Autowired
	LkAssociationRepository lkAssocationRepository;
	
	public List <LkAssociation> findAll() {
		
		return (List) lkAssocationRepository.findAll();
	}

}
