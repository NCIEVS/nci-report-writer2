package gov.nih.nci.evs.reportwriter.core.model.template;

import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

/** The Class Template. */
public class Template {

  /** The name. */
  private String name;

  /** The type. */
  private String type;

  /** The root concept code. */
  private String rootConceptCode;

  /** The association. */
  private String association;

  /** The level. */
  private Integer level;

  /** The sort column. */
  private Integer sortColumn;

  /** The columns. */
  private List<TemplateColumn> columns;

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the root concept code.
   *
   * @return the root concept code
   */
  public String getRootConceptCode() {
    return rootConceptCode;
  }

  /**
   * Sets the root concept code.
   *
   * @param rootConceptCode the new root concept code
   */
  public void setRootConceptCode(String rootConceptCode) {
    this.rootConceptCode = rootConceptCode;
  }

  /**
   * Gets the association.
   *
   * @return the association
   */
  public String getAssociation() {
    return association;
  }

  /**
   * Sets the association.
   *
   * @param association the new association
   */
  public void setAssociation(String association) {
    this.association = association;
  }

  /**
   * Gets the level.
   *
   * @return the level
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * Sets the level.
   *
   * @param level the new level
   */
  public void setLevel(Integer level) {
    this.level = level;
  }

  /**
   * Gets the sort column.
   *
   * @return the sort column
   */
  public Integer getSortColumn() {
    return sortColumn;
  }

  /**
   * Sets the sort column.
   *
   * @param sortColumn the new sort column
   */
  public void setSortColumn(Integer sortColumn) {
    this.sortColumn = sortColumn;
  }

  /**
   * Gets the columns.
   *
   * @return the columns
   */
  public List<TemplateColumn> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the new columns
   */
  public void setColumns(List<TemplateColumn> columns) {
    this.columns = columns;
  }

  /**
   * Save to file.
   *
   * @param outputfile the outputfile
   * @param v the v
   */
  public void saveToFile(String outputfile, Vector<?> v) {
    try (PrintWriter pw = new PrintWriter(outputfile, "UTF-8"); ) {
      if (v != null && v.size() > 0) {
        for (int i = 0; i < v.size(); i++) {
          String t = (String) v.elementAt(i);
          pw.println(t);
        }
      }
    } catch (Exception ex) {
      // n/a
    }
  }

  /**
   * Save.
   *
   * @param outputfile the outputfile
   */
  public void save(String outputfile) {
    Vector<String> w = new Vector<>();
    w.add(this.to_string());
    saveToFile(outputfile, w);
  }

  /**
   * To string.
   *
   * @return the string
   */
  public String to_string() {
    StringBuffer str = new StringBuffer();
    str.append("name: " + name + "\n");
    str.append("type: " + type + "\n");
    str.append("rootConceptCode: " + rootConceptCode + "\n");
    str.append("association: " + association + "\n");
    str.append("level: " + level + "\n");
    str.append("sortColumn: " + sortColumn + "\n");
    str.append("columns: \n");
    for (int i = 0; i < columns.size(); i++) {
      TemplateColumn col = columns.get(i);
      str.append(col.to_string() + "\n");
    }
    return str.toString();
  }
}
