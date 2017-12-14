package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.LkPropertyType;
import gov.nih.nci.evs.reportwriter.repository.LkPropertyTypeRepository;

@Service
public class LkPropertyTypeServiceImpl implements LkPropertyTypeService {
	@Autowired
	LkPropertyTypeRepository lkPropertyTypeRepository;
	
	public List <LkPropertyType> findAll() {
		
		return (List) lkPropertyTypeRepository.findAll();
	}

}
