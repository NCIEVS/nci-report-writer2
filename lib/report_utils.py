'''
Methods for writing a the output for a ReportWriter Report.
'''
import sys
import json
import sparql
def print_report_header(report_columns,output_file):
    '''Print the report header for the text file output.

    :param array report_columns: array of ReportColumn's
    :param file output_file: output file

    :returns: None
    '''

    column_headers = []
    for column in report_columns:
        column_headers.append(column.label)
    print("\t".join(column_headers),file=output_file)

def write_column_data(parent_concept = None,
                    concept = None,
                    report_columns = None,
                    output_file = None):

    '''Write the information to text file output.
    This method contains the bulk of the logic used to
    generate the output content for each column definition.

    :param json parent_concept: Parent Concept
    :param json concept: Concept
    :param array report_columns: array of ReportColumn's
    :param file output_file: output file

    :returns: None
    '''
    parent_properties = parent_concept['properties']
    parent_axioms = parent_concept['axioms']

    concept_properties = concept['properties']
    concept_axioms = concept['axioms']

    column_data = []
    columnNumber = 0
    for column in report_columns:
        field_id = column.field_id
        property_type = column.property_type
        property_name = column.property_name
        representational_form = column.representational_form
        source = column.source
        qualifier_name = column.qualifier_name
        if qualifier_name == "subsource-name":
            qualifier_name = "subsource_name"
        qualifier_value = column.qualifier_value
        values = []
        properties = []

        if  field_id == "Property":
            if  property_type == "GENERIC":
                if property_name == "Contributing_Source":
                    properties = get_property_by_label(
                        property_name,
                        parent_properties)
                    for property in properties:
                        values.append(property['value'])

                if property_name == "FDA_UNII_Code":
                    properties = get_property_by_label(
                        property_name,
                        concept_properties)
                    for property in properties:
                        values.append(property['value'])


            if property_type == "PRESENTATION":
                axioms = get_axioms(
                        annotated_property = property_name,
                        term_group = representational_form,
                        term_source = source,
                        def_source = "",
                        qualifier_name = qualifier_name,
                        qualifier_value = qualifier_value,
                        axioms = concept_axioms)
                for axiom in axioms:
                    if qualifier_name == "subsource_name":
                        if qualifier_value == axiom['subsource_name']:
                            values.append(axiom['annotated_target'])
                    else:
                        values.append(axiom['annotated_target'])

            if property_type == "DEFINITION":
                if property_name == "DEFINITION":
                    axioms = get_axioms(
                            annotated_property = property_name,
                            term_group = None,
                            term_source = None,
                            def_source = None,
                            qualifier_name = None,
                            qualifier_value = None,
                            axioms = concept_axioms)
                if property_name == "ALT_DEFINITION":
                    axioms = get_axioms(
                            annotated_property = property_name,
                            term_group = None,
                            term_source = None,
                            def_source = source,
                            qualifier_name = None,
                            qualifier_value = None,
                            axioms = concept_axioms)
                for axiom in axioms:
                    values.append(axiom['annotated_target'])

        elif field_id == "Property Qualifier":
            if property_type == "PRESENTATION":
                if property_name == "FULL_SYN":
                    if representational_form == "PT":
                        axioms = get_axiom_source_code(
                                property_name,
                                source,
                                representational_form,
                                qualifier_value,
                                concept_axioms)
                        for axiom in axioms:
                            values.append(axiom['source_code'])


        elif field_id == "Code":
            properties = get_property_by_label(
                    "code",concept_properties)
            values.append(properties[0]['value'])

        elif field_id == "Associated Concept Code":
            values.append(parent_concept['code'])

        elif field_id == "Associated Concept Property":
            if property_type == "PRESENTATION":
                if property_name == "Preferred_Name":
                    properties = get_property_by_label(
                            "Preferred_Name", parent_properties)
                    for property in properties:
                        values.append(property['value'])
                else:
                    axioms = get_axioms(
                            annotated_property = property_name,
                            term_group = representational_form,
                            term_source = source,
                            def_source = None,
                            qualifier_name = None,
                            qualifier_value = None,
                            axioms = parent_axioms)
                    for axiom in axioms:
                        values.append(axiom['annotated_target'])

            if property_type == "GENERIC":
                properties = get_property_by_label(
                            "Contributing_Source", parent_properties)
                for property in properties:
                    values.append(property['value'])

        elif field_id == "1st CDRH Parent Code":
            properties = get_property_by_label(
                    "Has_CDRH_Parent",concept_properties)
            if len(properties) > 0:
                junk,uri_short = properties[0]['value'].split("#")
                cdrh_parent = sparql.get_concept_by_uri(uri_short)
                cdrh_parent['uri_short'] = uri_short
                values.append(cdrh_parent['code'])

        elif field_id == "1st CDRH Parent Property Qualifier":
            properties = get_property_by_label(
                    "Has_CDRH_Parent",concept_properties)
            if len(properties) > 0:
                junk,uri_short = properties[0]['value'].split("#")
                cdrh_parent = sparql.get_concept_by_uri(uri_short)
                cdrh_parent['uri_short'] = uri_short
                cdrh_parent['properties'] = sparql.get_properties(cdrh_parent['code'])
                cdrh_parent['axioms'] = sparql.get_axioms(cdrh_parent['uri_short'])
                properties = get_axioms(
                        annotated_property = property_name,
                        term_group = representational_form,
                        term_source = source,
                        def_source = None,
                        qualifier_name = None,
                        qualifier_value = None,
                        axioms = cdrh_parent['axioms'])
                for property in properties:
                    if (qualifier_name == "source-code" and
                            property['source_code']):
                        values.append(property['source_code'])

        elif field_id == "1st CDRH Parent Property":
            properties = get_property_by_label(
                    "Has_CDRH_Parent",concept_properties)
            if len(properties) > 0:
                junk,uri_short = properties[0]['value'].split("#")
                cdrh_parent = sparql.get_concept_by_uri(uri_short)
                cdrh_parent['uri_short'] = uri_short
                cdrh_parent['properties'] = sparql.get_properties(cdrh_parent['code'])
                cdrh_parent['axioms'] = sparql.get_axioms(cdrh_parent['uri_short'])
                properties = get_axiom_source_code(
                        property_name, source, representational_form,
                        qualifier_value, cdrh_parent['axioms'])
                for property in properties:
                        values.append(property['annotated_target'])

        elif field_id == "1st NICHD Parent Code":
            properties = get_property_by_label(
                    "Has_NICHD_Parent",concept_properties)
            if len(properties) > 0:
                junk,uri_short = properties[0]['value'].split("#")
                nichd_parent = sparql.get_concept_by_uri(uri_short)
                nichd_parent['uri_short'] = uri_short
                values.append(nichd_parent['code'])

        elif field_id == "1st NICHD Parent Property":
            properties = get_property_by_label(
                    "Has_NICHD_Parent",concept_properties)
            if len(properties) > 0:
                junk,uri_short = properties[0]['value'].split("#")
                nichd_parent = sparql.get_concept_by_uri(uri_short)
                nichd_parent['uri_short'] = uri_short
                nichd_parent['properties'] = sparql.get_properties(nichd_parent['code'])
                nichd_parent['axioms'] = sparql.get_axioms(nichd_parent['uri_short'])
                axioms = get_axioms(
                        annotated_property = property_name,
                        term_group = representational_form,
                        term_source = source,
                        def_source = None,
                        qualifier_name = None,
                        qualifier_value = None,
                        axioms = nichd_parent['axioms'])
                for axiom in axioms:
                        values.append(axiom['annotated_target'])

        elif field_id == "2nd NICHD Parent Code":
            properties = get_property_by_label(
                    "Has_NICHD_Parent",concept_properties)
            if len(properties) > 1:
                junk,uri_short = properties[1]['value'].split("#")
                nichd_parent = sparql.get_concept_by_uri(uri_short)
                nichd_parent['uri_short'] = uri_short
                values.append(nichd_parent['code'])

        elif field_id == "2nd NICHD Parent Property":
            properties = get_property_by_label(
                    "Has_NICHD_Parent",concept_properties)
            if len(properties) > 1:
                junk,uri_short = properties[1]['value'].split("#")
                nichd_parent = sparql.get_concept_by_uri(uri_short)
                nichd_parent['uri_short'] = uri_short
                nichd_parent['properties'] = sparql.get_properties(nichd_parent['code'])
                nichd_parent['axioms'] = sparql.get_axioms(nichd_parent['uri_short'])
                axioms = get_axioms(
                        annotated_property = property_name,
                        term_group = representational_form,
                        term_source = source,
                        def_source = None,
                        qualifier_name = None,
                        qualifier_value = None,
                        axioms = nichd_parent['axioms'])
                for axiom in axioms:
                        values.append(axiom['annotated_target'])

        else:
            pass

        column_str = "|".join(values)
        column_data.append(column_str)

    print("\t".join(column_data),file=output_file)

def get_property_by_label(label,concept_properties):
    '''Search the concept properties for a specific "labeL".
    If there is a match, append the property to the results.

    :param str label: label of a property
    :param array concept_properties: array of concept properties in JSON format

    :rtype: Array
    :returns: array of results

    For example::

      [
          {
              "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#code",
              "value": "C122629",
              "label": "code",
              "code": "NHC0"
          }
      ]

    '''

    results = []
    for property in concept_properties:
        if label == property['label']:
            results.append(property)

    return results;


def get_axioms(annotated_property = None,
               term_group = None,
               term_source = None,
               def_source = None,
               qualifier_name = None,
               qualifier_value = None,
               axioms = None):
    '''Search the axioms based on search criteria.
    If there is a match, append the property to the results.

    :param str annotated_property: annotated_property
    :param str term_group: term_group
    :param str term_source: term_source
    :param str def_source: def_source
    :param str qualifier_name: qualifier_name
    :param str qualifier_value: qualifier_value
    :param array axioms: array of axioms in JSON format

    :rtype: Array
    :returns: array of results

    For example::

      [
          {
              "annotated_property": "FULL_SYN",
              "source_code": "",
              "def_source": "",
              "term_source": "NCPDP",
              "term_group": "PT",
              "subsource_name": "",
              "annotated_target": "NCPDP DoseUnitOfMeasure Terminology"
          }
      ]

    '''
    results = []
    for axiom in axioms:
        if annotated_property is not None:
            if annotated_property != axiom['annotated_property']:
                continue

        if term_group is not None:
            if term_group != axiom['term_group']:
                continue

        if term_source is not None:
            if term_source != axiom['term_source']:
                continue

        if def_source is not None:
            if def_source != axiom['def_source']:
                continue

        if qualifier_name is not None:
            if qualifier_name not in axiom:
                continue

        results.append(axiom)

    return results;

def get_axiom_source_code(property_name,term_source,representational_form,
                          subsource_value, concept_axioms):
    results = []
    for property in concept_axioms:
        if (property_name == property['annotated_property'] and
            term_source == property['term_source'] and
            representational_form == property['term_group'] and
            subsource_value == property['subsource_name']):
            results.append(property)
    return results;

def get_axiom_code(property_name,term_source,representational_form,
                          concept_axioms):
    results = []
    for property in concept_axioms:
        if (property_name == property['annotated_property'] and
            term_source == property['term_source'] and
            representational_form == property['term_group']):
            results.append(property)
    return results;

def process_concept_in_subset(parent_concept,report_columns,output_file):
    concept_in_subset = sparql.get_concept_in_subset(parent_concept['uri'])
    for concept in concept_in_subset:
        junk,uri_short = concept['concept'].split("#")
        concept['uri_short'] = uri_short
        concept['properties'] = sparql.get_properties(concept['concept_code'])
        concept['axioms'] = sparql.get_axioms(concept['uri_short'])
        write_column_data(parent_concept,concept,report_columns,output_file)

def process_concept_children(parent_concept,report_columns,output_file):
    children = sparql.get_children(parent_concept['code'])
    for child in children:
        child_concept = sparql.get_concept(child['concept_code'])
        junk,uri_short = child_concept['uri'].split("#")
        child_concept['code'] = child['concept_code']
        child_concept['uri_short'] = uri_short
        child_concept['properties'] = sparql.get_properties(child_concept['code'])
        child_concept['axioms'] = sparql.get_axioms(child_concept['uri_short'])
        process_concept_in_subset(child_concept,report_columns,output_file)
        process_concept_children(child_concept,report_columns,output_file)
