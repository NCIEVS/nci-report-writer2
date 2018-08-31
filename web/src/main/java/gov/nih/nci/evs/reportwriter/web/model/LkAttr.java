package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the lk_subsource database table.
 * 
 */
@Entity
@Table(name = "lk_attr")
@NamedQuery(name = "LkAttr.findAll", query = "SELECT l FROM LkAttr l")
public class LkAttr implements LkGeneric, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;

	public LkAttr() {
	}

	@Id
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
