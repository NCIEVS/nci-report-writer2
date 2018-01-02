package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkSubsource;
import gov.nih.nci.evs.reportwriter.web.repository.LkSubsourceRepository;

@Service
public class LkSubsourceServiceImpl implements LkSubsourceService {
	@Autowired
	LkSubsourceRepository lkSubsourceRepository;
	
	public List <LkSubsource> findAll() {
		
		return (List) lkSubsourceRepository.findAll();
	}

}
