package gov.nih.nci.evs.reportwriter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.nih.nci.evs.reportwriter.model.LkGeneric;
import gov.nih.nci.evs.reportwriter.model.LkProperty;
import gov.nih.nci.evs.reportwriter.model.LookUp;
import gov.nih.nci.evs.reportwriter.service.LkAssociationService;
import gov.nih.nci.evs.reportwriter.service.LkDisplayService;
import gov.nih.nci.evs.reportwriter.service.LkGroupService;
import gov.nih.nci.evs.reportwriter.service.LkPropertyService;
import gov.nih.nci.evs.reportwriter.service.LkPropertyTypeService;
import gov.nih.nci.evs.reportwriter.service.LkReportStatusService;
import gov.nih.nci.evs.reportwriter.service.LkReportTemplateStatusService;
import gov.nih.nci.evs.reportwriter.service.LkReportTemplateTypeService;
import gov.nih.nci.evs.reportwriter.service.LkSourceService;
import gov.nih.nci.evs.reportwriter.service.LkSubsourceService;


@RestController
public class HomeController {

	@Autowired  LkAssociationService lkAssociationService;
	@Autowired  LkDisplayService lkDisplayService;
	@Autowired  LkGroupService lkGroupService;
	@Autowired  LkPropertyService lkPropertyService;
	@Autowired  LkPropertyTypeService lkPropertyTypeService;
	@Autowired  LkReportStatusService lkReportStatusService;
	@Autowired  LkReportTemplateStatusService lkReportTemplateStatusService;
	@Autowired  LkReportTemplateTypeService lkReportTemplateTypeService;
	@Autowired  LkSourceService lkSourceService;
	@Autowired  LkSubsourceService lkSubsourceService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody String home() {
		return "Welcome to ReportWriter";
	}

	@RequestMapping(value="/lkassociation",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkAssociations() {
		List lks =  lkAssociationService.findAll();	
		return convertToLookUp(lks);
	}

	
	@RequestMapping(value="/lkdisplay",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkDisplays() {
		List lks =  lkDisplayService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkgroup",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkGroups() {
		List lks =  lkGroupService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkpropertytype",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkPropertyTypes() {
		List lks =  lkPropertyTypeService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkreportstatus",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkReportStatuses() {
		List lks =  lkReportStatusService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkreporttemplatestatus",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkReportTemplateStatuses() {
		List lks =  lkReportTemplateStatusService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkreporttemplatetype",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkReportTemplateTypes() {
		List lks =  lkReportTemplateTypeService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lksource",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkSources() {
		List lks =  lkSourceService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lksubsource",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkSubsources() {
		List lks =  lkSubsourceService.findAll();	
		return convertToLookUp(lks);
	}

	@RequestMapping(value="/lkproperty",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List <LookUp> findAllLkProperties() {
		List <LkProperty> lks =  lkPropertyService.findAll();	
		List <LookUp> lookUps = new ArrayList <LookUp> ();
		for (LkProperty lookUp: lks) {
			String label = "(" + lookUp.getCode() + ") " + lookUp.getLabel();
			lookUps.add(new LookUp(label,lookUp.getCode()));
		}
		return lookUps;
	}


	private List <LookUp> convertToLookUp(List <LkGeneric> lks) {
		List <LookUp> lookUps = new ArrayList <LookUp> ();
		for (LkGeneric lookUp: lks) {
			lookUps.add(new LookUp(lookUp.getName(),lookUp.getName()));
		}
		return lookUps;
		
	}

}
