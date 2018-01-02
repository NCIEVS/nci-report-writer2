package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkPropertyType;
import gov.nih.nci.evs.reportwriter.web.repository.LkPropertyTypeRepository;

@Service
public class LkPropertyTypeServiceImpl implements LkPropertyTypeService {
	@Autowired
	LkPropertyTypeRepository lkPropertyTypeRepository;
	
	public List <LkPropertyType> findAll() {
		
		return (List) lkPropertyTypeRepository.findAll();
	}

}
