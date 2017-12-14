package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkSubsource;
import gov.nih.nci.evs.reportwriter.repository.LkSubsourceRepository;

@Service
public class LkSubsourceServiceImpl implements LkSubsourceService {
	@Autowired
	LkSubsourceRepository lkSubsourceRepository;
	
	public List <LkSubsource> findAll() {
		
		return (List) lkSubsourceRepository.findAll();
	}

}
