package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;

/** The Interface ReportTemplateService. */
public interface ReportTemplateService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<ReportTemplateUI> findAll();

  /**
   * Find one.
   *
   * @param id the id
   * @return the report template
   */
  public ReportTemplate findOne(Integer id);

  /**
   * Creates the.
   *
   * @param reportTemplate the report template
   * @return the report template UI
   */
  public ReportTemplateUI create(ReportTemplateUI reportTemplate);

  /**
   * Save.
   *
   * @param reportTemplate the report template
   * @return the report template UI
   */
  public ReportTemplateUI save(ReportTemplateUI reportTemplate);

  /**
   * Clone.
   *
   * @param reportTemplate the report template
   * @return the report template UI
   */
  public ReportTemplateUI clone(ReportTemplateUI reportTemplate);
}
