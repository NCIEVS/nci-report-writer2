'''
Functions used for parsing templates. Creating a ReportTemplate, which
contains ReportColumn's.
'''

import json
from report_template import ReportTemplate
from report_column import ReportColumn

def parse_template(template_file):
    '''Parse the report template

    :param str template_file: Report template file
    :returns: parsed report template
    :rtype: ReportTemplate

    '''

    report_template = ReportTemplate()
    report_column = ReportColumn()
    report_columns = []
    header = True
    for line in template_file:
        line = line.rstrip()
        if line.startswith("columnCollection"):
            header = False
            # Create ReportTemplate Record
        elif header:
            key,value = line.split(":")
            if key == "label":
                report_template.label = value
            elif key == "rootConceptCode":
                report_template.root_concept_code = value
            elif key == "codingSchemeName":
                report_template.coding_scheme_name = value
            elif key == "codingSchemeVersion":
                report_template.coding_scheme_version = value
            elif key == "associationName":
                report_template.association_name = value
            elif key == "direction":
                report_template.direction = value
            elif key == "level":
                report_template.level = value
            elif key == "delimiter":
                report_template.delimiter = value
            else:
                pass
        else:
            if line == "":
                continue
            line = line.lstrip()
            key,value = line.split(":")
            if value == "":
                value = None

            if key == "columnNumber":
                report_column.column_number = value
            elif key == "label":
                report_column.label = value
            elif key == "fieldId":
                report_column.field_id = value
            elif key == "propertyType":
                # The templates are inconsistent about case,
                # so convert to all upper case
                if value is not None:
                    report_column.property_type = value.upper()
                else:
                    report_column.property_type = None
            elif key == "propertyName":
                report_column.property_name = value
            elif key == "isPreferred":
                report_column.is_preferred = value
            elif key == "representationalForm":
                report_column.representational_form = value
            elif key == "source":
                report_column.source = value
            elif key == "qualifierName":
                report_column.qualifier_name = value
            elif key == "qualifierValue":
                report_column.qualifier_value = value
            elif key == "delimiter":
                report_column.delimiter = value
            elif key == "conditionalColumnId":
                report_column.conditional_column_id = value
                report_columns.append(report_column)
                report_column = ReportColumn()
            else:
                pass

    report_template.report_columns = report_columns
    return report_template
