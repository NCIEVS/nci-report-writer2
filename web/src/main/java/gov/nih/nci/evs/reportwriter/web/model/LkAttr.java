package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the lk_subsource database table. */
@Entity
@Table(name = "lk_attr")
@NamedQuery(name = "LkAttr.findAll", query = "SELECT l FROM LkAttr l")
public class LkAttr implements LkGeneric, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** Instantiates a new lk attr. */
  public LkAttr() {}

  /* see superclass */
  @Override
  @Id
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /* see superclass */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(final String description) {
    this.description = description;
  }
}
