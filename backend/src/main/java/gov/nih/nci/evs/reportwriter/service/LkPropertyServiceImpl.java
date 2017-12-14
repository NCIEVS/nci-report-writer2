package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkProperty;
import gov.nih.nci.evs.reportwriter.repository.LkPropertyRepository;

@Service
public class LkPropertyServiceImpl implements LkPropertyService {
	@Autowired
	LkPropertyRepository lkPropertyRepository;
	
	public List <LkProperty> findAll() {
		
		return (List) lkPropertyRepository.findAll();
	}

}
