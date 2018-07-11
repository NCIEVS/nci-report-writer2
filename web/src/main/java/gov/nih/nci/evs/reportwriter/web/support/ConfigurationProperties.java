package gov.nih.nci.evs.reportwriter.web.support;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LookUp;

public class ConfigurationProperties {

	
	private List<LookUp> graphNames;

	public List<LookUp> getGraphNames() {
		return graphNames;
	}

	public void setGraphNames(List<LookUp> graphNames) {
		this.graphNames = graphNames;
	}
}
