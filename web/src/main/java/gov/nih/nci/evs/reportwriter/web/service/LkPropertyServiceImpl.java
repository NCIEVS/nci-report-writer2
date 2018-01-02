package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkProperty;
import gov.nih.nci.evs.reportwriter.web.repository.LkPropertyRepository;

@Service
public class LkPropertyServiceImpl implements LkPropertyService {
	@Autowired
	LkPropertyRepository lkPropertyRepository;
	
	public List <LkProperty> findAll() {
		
		return (List) lkPropertyRepository.findAll();
	}

}
