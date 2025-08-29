package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkSource;
import gov.nih.nci.evs.reportwriter.web.repository.LkSourceRepository;

@Service
public class LkSourceServiceImpl implements LkSourceService {
	@Autowired
	LkSourceRepository lkSourceRepository;
	
	public List <LkSource> findAll() {
		
		return (List) lkSourceRepository.findAll();
	}

}
