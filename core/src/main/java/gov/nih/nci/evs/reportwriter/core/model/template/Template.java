package gov.nih.nci.evs.reportwriter.core.model.template;

import java.io.*;
import java.util.*;

public class Template {
	private String name;
	private String type;
	private String rootConceptCode;
	private String association;
	private Integer level;
	private Integer sortColumn;

	private List <TemplateColumn> columns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootConceptCode() {
		return rootConceptCode;
	}

	public void setRootConceptCode(String rootConceptCode) {
		this.rootConceptCode = rootConceptCode;
	}

	public String getAssociation() {
		return association;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}


	public List<TemplateColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TemplateColumn> columns) {
		this.columns = columns;
	}

	 public void saveToFile(String outputfile, Vector v) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputfile, "UTF-8");
			if (v != null && v.size() > 0) {
				for (int i=0; i<v.size(); i++) {
					String t = (String) v.elementAt(i);
					pw.println(t);
				}
		    }
		} catch (Exception ex) {

		} finally {
			try {
				pw.close();
				System.out.println("Output file " + outputfile + " generated.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	 }

	public void save(String outputfile) {
		Vector w = new Vector();
        w.add(this.to_string());
		saveToFile(outputfile, w);
	}

	public String to_string() {
		StringBuffer str = new StringBuffer();
		str.append("Name: " + name + "\n");
		str.append("Type: " + type + "\n");
		str.append("Root Concept Code: " + rootConceptCode + "\n");
		str.append("Association: " + association + "\n");
		str.append("Level: " + level + "\n");
		str.append("Sort Column: " + sortColumn + "\n");
		str.append("columns: \n");
        for (int i=0; i<columns.size(); i++) {
			TemplateColumn col = (TemplateColumn) columns.get(i);
			str.append(col.to_string() + "\n");
		}
		return str.toString();
	}
}
