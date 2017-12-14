package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkDisplay;
import gov.nih.nci.evs.reportwriter.repository.LkDisplayRepository;

@Service
public class LkDisplayServiceImpl implements LkDisplayService {
	@Autowired
	LkDisplayRepository lkDisplayRepository;
	
	public List <LkDisplay> findAll() {
		
		return (List) lkDisplayRepository.findAll();
	}

}
