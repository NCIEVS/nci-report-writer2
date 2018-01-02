package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkGroup;
import gov.nih.nci.evs.reportwriter.web.repository.LkGroupRepository;

@Service
public class LkGroupServiceImpl implements LkGroupService {
	@Autowired
	LkGroupRepository lkGroupRepository;
	
	public List <LkGroup> findAll() {
		
		return (List) lkGroupRepository.findAll();
	}

}
