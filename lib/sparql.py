'''
Contains methods for running SPARQL queries and converting
the results to JSON format.

- Standard SPARQL Prefixes::

    PREFIX :<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>
    PREFIX base:<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>
    PREFIX owl:<http://www.w3.org/2002/07/owl#>
    PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
    PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>

'''

import requests
from requests.auth import HTTPBasicAuth
import json
import sys

SPARQL_ENDPOINT_TYPE = "Fuseki"
#SPARQL_ENDPOINT_TYPE = "StarDog"
SPARQL_ENDPOINT_FUSEKI = "http://localhost:3030/ncit2/query"
SPARQL_ENDPOINT_STARDOG = "http://localhost:5820/ncit2/query"
SPARQL_ENDPOINT_HEADER = { 'Accept': 'application/sparql-results+json'}
SPARQL_ENDPOINT_USER = "REPLACE"
SPARQL_ENDPOINT_PASSWORD = "REPLACE"

prefix = '''
PREFIX :<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>
PREFIX base:<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>
PREFIX owl:<http://www.w3.org/2002/07/owl#>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>
'''

#
# concept_query
#
concept_query = '''
SELECT ?concept ?concept_label
WHERE {
   ?concept a owl:Class .
   ?concept :code "CONCEPT_CODE" .
   ?concept rdfs:label ?concept_label
}
'''

#
# concept_query_by_uri
#
concept_query_by_uri = '''
SELECT ?concept_code ?concept_label
WHERE {
   :CONCEPT_URI :code ?concept_code;
                 rdfs:label ?concept_label
}
'''

#
# concept_properties_query
#
concept_properties_query = '''
SELECT ?property ?property_code ?property_label ?property_value
WHERE { ?concept a owl:Class .
        ?concept :code "CONCEPT_CODE" .
        ?property a owl:AnnotationProperty .
        ?property rdfs:label ?property_label .
        ?property :code ?property_code .
        ?concept ?property ?property_value
}
ORDER BY ?property_label
'''

#
# concept_axioms_query
#
concept_axioms_query = '''
SELECT ?annotated_property ?annotated_target ?term_source
                ?term_group ?source_code ?def_source ?subsource_name
WHERE {
  ?axiom a owl:Axiom .
  ?axiom owl:annotatedSource :CONCEPT_URI .
  OPTIONAL {?axiom owl:annotatedProperty ?annotated_property} .
  OPTIONAL {?axiom owl:annotatedTarget ?annotated_target} .
  OPTIONAL {?axiom :term-source ?term_source} .
  OPTIONAL {?axiom :term-group ?term_group} .
  OPTIONAL {?axiom :source-code ?source_code} .
  OPTIONAL {?axiom :def-source ?def_source} .
  OPTIONAL {?axiom :subsource-name ?subsource_name }
}
'''

#
# concept_in_subset_query
#
concept_in_subset_query = '''
SELECT ?concept ?concept_code ?concept_label
WHERE {
   ?concept a owl:Class .
   ?concept :code ?concept_code .
   ?concept rdfs:label ?concept_label .
   ?concept_in_subset a owl:AnnotationProperty .
   ?concept_in_subset :code "A8" .
   ?concept ?concept_in_subset ?concept_uri
   FILTER (str(?concept_uri) = "CONCEPT_URI")
}
ORDER BY ?concept_label
'''

#
# concept_parents
#
concept_parents_query = '''
SELECT ?parent_concept ?parent_label ?parent_code
WHERE
{
  {
    {
      ?child_concept a owl:Class .
      ?child_concept :code "CONCEPT_CODE" .
      ?child_concept rdfs:subClassOf ?parent_concept .
      ?parent_concept rdfs:label ?parent_label .
      ?parent_concept :code ?parent_code
    }
    FILTER (?child_concept != ?parent_concept)
  }
  UNION
  {
    {
      ?child_concept a owl:Class .
      ?child_concept :code "CONCEPT_CODE" .
      ?equiv_concept owl:intersectionOf ?list .
      ?list rdf:rest*/rdf:first ?child_concept .
      ?parent_concept owl:equivalentClass ?child_concept .
      ?parent_concept a owl:Class .
      ?parent_concept :code ?parent_code .
      ?parent_concept rdfs:label ?parent_label
    }
    FILTER (?child_concept != ?parent_concept)
  }
}
'''

#
# concept_children
#
concept_children_query = '''
SELECT ?child_concept ?child_label ?child_code
WHERE
{
  {
    {
      ?parent_concept a owl:Class .
      ?parent_concept :code "CONCEPT_CODE" .
      ?child_concept rdfs:subClassOf ?parent_concept .
      ?child_concept rdfs:label ?child_label .
      ?child_concept :code ?child_code
    }
    FILTER (?parent_concept != ?child_concept)
  }
  UNION
  {
    {
      ?parent_concept a owl:Class .
      ?parent_concept :code "CONCEPT_CODE" .
      ?equiv_concept owl:intersectionOf ?list .
      ?list rdf:rest*/rdf:first ?parent_concept .
      ?child_concept owl:equivalentClass ?parent_concept .
      ?child_concept a owl:Class .
      ?child_concept :code ?child_code .
      ?child_concept rdfs:label ?child_label
    }
    FILTER (?parent_concept != ?child_concept)
  }
}
'''

#
# run_sparql_query
#
def run_sparql_query(query):
    '''Generic method for running a SPARQL query.
    Checks the status code and returns results in JSON format.

    :param str query: SPARQL query
    :rtype: JSON
    :returns: query results in JSON format

    .. warning:: If the SPARQL query fails the module will call sys.exit(1)

    '''
    if SPARQL_ENDPOINT_TYPE == "Fuseki":
        r = requests.post(SPARQL_ENDPOINT_FUSEKI,data= { "query": query })

    elif SPARQL_ENDPOINT_TYPE == "StarDog":
        headers = {'Accept': 'application/sparql-results+json'}
        r = requests.post(SPARQL_ENDPOINT_STARDOG,
                headers=headers,data={ "query": query },
                auth=HTTPBasicAuth("admin","admin"))

    else:
        pass

    if r.status_code != 200:
        sys.stderr.write("Problem Status Code: " + str(r.status_code) + "\n")
        sys.exit(1)

    return r.json()

#
# get_concept
#
def get_concept(concept_code):
    '''Run a SPARQL query to retrieve a Concept using the concept code

    :param str concept_code: concept code
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        {
            "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#CDISC_SDTM_Laterality_Terminology",
            "label": "CDISC SDTM Laterality Terminology"
        }

    - SPARQL Query::

        SELECT ?concept ?concept_label
        WHERE {
           ?concept a owl:Class .
           ?concept :code "CONCEPT_CODE" .
           ?concept rdfs:label ?concept_label
        }
    '''

    query = prefix + concept_query
    query = query.replace("CONCEPT_CODE",concept_code)
    obj = run_sparql_query(query)
    if len(obj['results']['bindings']) == 0:
        return None
    result = obj['results']['bindings'][0]
    result_obj = {
        'uri': result['concept']['value'],
        'label': result['concept_label']['value']
    }

    return result_obj

#
# get_concept_by_uri
#
def get_concept_by_uri(concept_uri):
    '''Run a SPARQL query to retrieve a Concept using the concept uri

    :param str concept_uri: concept uri
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        {
            "label": "Qualitative Concept",
            "code": "C92722"
        }

    - SPARQL Query::

        SELECT ?concept_code ?concept_label
        WHERE {
           :CONCEPT_URI :code ?concept_code;
                         rdfs:label ?concept_label
        }
    '''

    query = prefix + concept_query_by_uri
    query = query.replace("CONCEPT_URI",concept_uri)
    obj = run_sparql_query(query)
    result = obj['results']['bindings'][0]
    result_obj = {
        'code': result['concept_code']['value'],
        'label': result['concept_label']['value']
    }
    return result_obj


#
# get_properties
#
def get_properties(concept_code):
    '''Run a SPARQL query to retrieve the Concept properties

    :param str concept_code: concept code
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        [
            {
                "value": "CDISC terminology for anatomical location or specimen further detailing the side(s) of interest.",
                "label": "ALT_DEFINITION",
                "code": "P325",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#ALT_DEFINITION"
            },
            {
                "value": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#CDISC_SDTM_Terminology",
                "label": "Concept_In_Subset",
                "code": "A8",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Concept_In_Subset"
            },
            {
                "value": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#CDISC_SEND_Terminology",
                "label": "Concept_In_Subset",
                "code": "A8",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Concept_In_Subset"
            },
            {
                "value": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Clinical_Data_Interchange_Standards_Consortium",
                "label": "Concept_In_Subset",
                "code": "A8",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Concept_In_Subset"
            },
            {
                "value": "CDISC",
                "label": "Contributing_Source",
                "code": "P322",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Contributing_Source"
            },
            {
                "value": "NICHD",
                "label": "Contributing_Source",
                "code": "P322",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Contributing_Source"
            },
            {
                "value": "Terminology associated with the laterality codelist of the Clinical Data Interchange Standards Consortium (CDISC) Study Data Tabulation Model (SDTM).",
                "label": "DEFINITION",
                "code": "P97",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#DEFINITION"
            },
            {
                "value": "Yes",
                "label": "Extensible_List",
                "code": "P361",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Extensible_List"
            },
            {
                "value": "CDISC SDTM Laterality Terminology",
                "label": "FULL_SYN",
                "code": "P90",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#FULL_SYN"
            },
            {
                "value": "LAT",
                "label": "FULL_SYN",
                "code": "P90",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#FULL_SYN"
            },
            {
                "value": "Laterality",
                "label": "FULL_SYN",
                "code": "P90",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#FULL_SYN"
            },
            {
                "value": "SDTM-LAT",
                "label": "FULL_SYN",
                "code": "P90",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#FULL_SYN"
            },
            {
                "value": "CDISC SDTM Laterality Terminology",
                "label": "Preferred_Name",
                "code": "P108",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Preferred_Name"
            },
            {
                "value": "Intellectual Product",
                "label": "Semantic_Type",
                "code": "P106",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Semantic_Type"
            },
            {
                "value": "C3274523",
                "label": "UMLS_CUI",
                "code": "P207",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#UMLS_CUI"
            },
            {
                "value": "C99073",
                "label": "code",
                "code": "NHC0",
                "uri": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#code"
            }
        ]

    - SPARQL Query::

        SELECT ?property ?property_code ?property_label ?property_value
        WHERE { ?concept a owl:Class .
                ?concept :code "CONCEPT_CODE" .
                ?property a owl:AnnotationProperty .
                ?property rdfs:label ?property_label .
                ?property :code ?property_code .
                ?concept ?property ?property_value
        }
        ORDER BY ?property_label
    '''

    query = prefix + concept_properties_query
    query = query.replace("CONCEPT_CODE",concept_code)
    obj = run_sparql_query(query)
    results = []
    for row in obj['results']['bindings']:
        uri = row['property']['value']
        code = row['property_code']['value']
        label = row['property_label']['value']
        value = row['property_value']['value']
        result_obj = {
            'uri': uri,
            'code': code,
            'label': label,
            'value': value
        }
        results.append(result_obj)

    return results

#
# get_concept_axioms
#
def get_axioms(concept_uri):
    '''Run a SPARQL query to retrieve the Concept axioms

    :param str concept_uri: concept uri
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        [
            {
                "term_group": "",
                "term_source": "",
                "def_source": "CDISC",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "CDISC terminology for anatomical location or specimen further detailing the side(s) of interest.",
                "annotated_property": "ALT_DEFINITION"
            },
            {
                "term_group": "",
                "term_source": "",
                "def_source": "NCI",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "Terminology associated with the laterality codelist of the Clinical Data Interchange Standards Consortium (CDISC) Study Data Tabulation Model (SDTM).",
                "annotated_property": "DEFINITION"
            },
            {
                "term_group": "PT",
                "term_source": "NCI",
                "def_source": "",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "CDISC SDTM Laterality Terminology",
                "annotated_property": "FULL_SYN"
            },
            {
                "term_group": "PT",
                "term_source": "NICHD",
                "def_source": "",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "CDISC SDTM Laterality Terminology",
                "annotated_property": "FULL_SYN"
            },
            {
                "term_group": "PT",
                "term_source": "CDISC",
                "def_source": "",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "LAT",
                "annotated_property": "FULL_SYN"
            },
            {
                "term_group": "SY",
                "term_source": "CDISC",
                "def_source": "",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "Laterality",
                "annotated_property": "FULL_SYN"
            },
            {
                "term_group": "AB",
                "term_source": "NCI",
                "def_source": "",
                "subsource_name": "",
                "source_code": "",
                "annotated_target": "SDTM-LAT",
                "annotated_property": "FULL_SYN"
            }
        ]

    - SPARQL Query::

        SELECT ?annotated_property ?annotated_target ?term_source
                        ?term_group ?source_code ?def_source ?subsource_name
        WHERE {
          ?axiom a owl:Axiom .
          ?axiom owl:annotatedSource :CONCEPT_URI .
          OPTIONAL {?axiom owl:annotatedProperty ?annotated_property} .
          OPTIONAL {?axiom owl:annotatedTarget ?annotated_target} .
          OPTIONAL {?axiom :term-source ?term_source} .
          OPTIONAL {?axiom :term-group ?term_group} .
          OPTIONAL {?axiom :source-code ?source_code} .
          OPTIONAL {?axiom :def-source ?def_source} .
          OPTIONAL {?axiom :subsource-name ?subsource_name }
        }

    '''

    query = prefix + concept_axioms_query
    query = query.replace("CONCEPT_URI",concept_uri)
    obj = run_sparql_query(query)
    results = []
    for row in obj['results']['bindings']:
        annotated_property = ""
        annotated_target = ""
        term_source = ""
        term_group = ""
        source_code = ""
        def_source = ""
        subsource_name = ""

        if "annotated_property" in row:
            uri,annotated_property = row['annotated_property']['value'].split("#")

        if "annotated_target" in row:
            annotated_target = row['annotated_target']['value']

        if "term_source" in row:
            term_source = row['term_source']['value']

        if "term_group" in row:
            term_group = row['term_group']['value']

        if "source_code" in row:
            source_code = row['source_code']['value']

        if "def_source" in row:
            def_source = row['def_source']['value']

        if "subsource_name" in row:
            subsource_name = row['subsource_name']['value']

        result_obj = {
            'annotated_property': annotated_property,
            'annotated_target': annotated_target,
            'term_source': term_source,
            'term_group': term_group,
            'source_code': source_code,
            'def_source': def_source,
            'subsource_name': subsource_name
        }
        results.append(result_obj)

    return results

#
# get_concept_in_subset
#
def get_concept_in_subset(concept_uri):
    '''Run a SPARQL query to retrieve the Concept in a subset

    :param str concept_uri: concept uri
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        [
            {
                "concept_label": "Bilateral",
                "concept_code": "C13332",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Bilateral"
            },
            {
                "concept_label": "Contralateral",
                "concept_code": "C25307",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Contralateral"
            },
            {
                "concept_label": "Ipsilateral",
                "concept_code": "C25308",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Ipsilateral"
            },
            {
                "concept_label": "Lateral",
                "concept_code": "C25230",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Lateral"
            },
            {
                "concept_label": "Left",
                "concept_code": "C25229",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Left"
            },
            {
                "concept_label": "Right",
                "concept_code": "C25228",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Right"
            },
            {
                "concept_label": "Unilateral",
                "concept_code": "C68598",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Unilateral_Generic"
            }
        ]

    - SPARQL Query::

        SELECT ?concept ?concept_code ?concept_label
        WHERE {
           ?concept a owl:Class .
           ?concept :code ?concept_code .
           ?concept rdfs:label ?concept_label .
           ?concept_in_subset a owl:AnnotationProperty .
           ?concept_in_subset :code "A8" .
           ?concept ?concept_in_subset ?concept_uri
           FILTER (str(?concept_uri) = "CONCEPT_URI")
        }
        ORDER BY ?concept_label

    '''

    query = prefix + concept_in_subset_query
    query = query.replace("CONCEPT_URI",concept_uri)
    obj = run_sparql_query(query)
    results = []
    for row in obj['results']['bindings']:
        concept = row['concept']['value']
        concept_code = row['concept_code']['value']
        concept_label = row['concept_label']['value']
        result_obj = {
            'concept': concept,
            'concept_code': concept_code,
            'concept_label': concept_label
        }
        results.append(result_obj)

    return results

#
# get_concept_children
#
def get_children(concept_code):
    '''Run a SPARQL query to retrieve the children of a Concept

    :param str concept_code: concept code
    :rtype: JSON
    :returns: query results in JSON format.
      For example::

        [
            {
                "concept_label": "Medical Device Component Or Accessory Terminology FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Medical_Device_Component_Or_Accessory_Terminology_CDRH",
                "concept_code": "C54577"
            },
            {
                "concept_label": "Medical Device Evaluation Conclusions Terminology FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Medical_Device_Evaluation_Conclusions_Terminology_FDA_CDRH",
                "concept_code": "C91802"
            },
            {
                "concept_label": "Medical Device Evaluation Methods Terminology FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Medical_Device_Evaluation_Methods_Terminology_FDA_CDRH",
                "concept_code": "C91800"
            },
            {
                "concept_label": "Medical Device Evaluation Results Terminology FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Medical_Device_Evaluation_Results_Terminology_FDA_CDRH",
                "concept_code": "C91801"
            },
            {
                "concept_label": "Medical Device Problem Codes FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Medical_Device_Problem_Codes_FDA_CDRH",
                "concept_code": "C54451"
            },
            {
                "concept_label": "Patient Problem Codes FDA CDRH",
                "concept": "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Patient_Problem_Codes_FDA_CDRH",
                "concept_code": "C54450"
            }
        ]

    - SPARQL Query::

        SELECT ?child_concept ?child_label ?child_code
        WHERE
        {
          {
            {
              ?parent_concept a owl:Class .
              ?parent_concept :code "CONCEPT_CODE" .
              ?child_concept rdfs:subClassOf ?parent_concept .
              ?child_concept rdfs:label ?child_label .
              ?child_concept :code ?child_code
            }
            FILTER (?parent_concept != ?child_concept)
          }
          UNION
          {
            {
              ?parent_concept a owl:Class .
              ?parent_concept :code "CONCEPT_CODE" .
              ?equiv_concept owl:intersectionOf ?list .
              ?list rdf:rest*/rdf:first ?parent_concept .
              ?child_concept owl:equivalentClass ?parent_concept .
              ?child_concept a owl:Class .
              ?child_concept :code ?child_code .
              ?child_concept rdfs:label ?child_label
            }
            FILTER (?parent_concept != ?child_concept)
          }
        }

    '''

    query = prefix + concept_children_query
    query = query.replace("CONCEPT_CODE",concept_code)
    obj = run_sparql_query(query)
    results = []
    for row in obj['results']['bindings']:
        concept = row['child_concept']['value']
        concept_code = row['child_code']['value']
        concept_label = row['child_label']['value']
        result_obj = {
            'concept': concept,
            'concept_code': concept_code,
            'concept_label': concept_label
        }
        results.append(result_obj)

    return results

#
# get_concept_parents
#
def get_parents(concept_code):
    '''Run a SPARQL query to retrieve the parents of a Concept

    :param str concept_code: concept code
    :rtype: JSON
    :returns: query results in JSON format.

    - SPARQL Query::

        SELECT ?parent_concept ?parent_label ?parent_code
        WHERE
        {
          {
            {
              ?child_concept a owl:Class .
              ?child_concept :code "CONCEPT_CODE" .
              ?child_concept rdfs:subClassOf ?parent_concept .
              ?parent_concept rdfs:label ?parent_label .
              ?parent_concept :code ?parent_code
            }
            FILTER (?child_concept != ?parent_concept)
          }
          UNION
          {
            {
              ?child_concept a owl:Class .
              ?child_concept :code "CONCEPT_CODE" .
              ?equiv_concept owl:intersectionOf ?list .
              ?list rdf:rest*/rdf:first ?child_concept .
              ?parent_concept owl:equivalentClass ?child_concept .
              ?parent_concept a owl:Class .
              ?parent_concept :code ?parent_code .
              ?parent_concept rdfs:label ?parent_label
            }
            FILTER (?child_concept != ?parent_concept)
          }
        }
    '''

    query = prefix + concept_parents_query
    query = query.replace("CONCEPT_CODE",concept_code)
    obj = run_sparql_query(query)
    results = []
    for row in obj['results']['bindings']:
        concept = row['parent_concept']['value']
        concept_code = row['parent_code']['value']
        concept_label = row['parent_label']['value']
        result_obj = {
            'concept': concept,
            'concept_code': concept_code,
            'concept_label': concept_label
        }
        results.append(result_obj)

    return results
