package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkAttr;
import gov.nih.nci.evs.reportwriter.web.repository.LkAttrRepository;


@Service
public class LkAttrServiceImpl implements LkAttrService {
	@Autowired
	LkAttrRepository lkAttrRepository;
	
	public List <LkAttr> findAll() {
		
		return (List) lkAttrRepository.findAll();
	}

}
