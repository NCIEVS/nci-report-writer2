package gov.nih.nci.evs.reportwriter.core.model.evs;

public class EvsVersionInfo {
	String version;
	String date;
	String comment;
	String graphName;
	String source;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graph) {
		this.graphName = graph;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}