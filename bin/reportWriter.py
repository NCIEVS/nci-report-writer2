#!/usr/bin/env python
'''
ReportWriter is the main entry point for generating a report. ReportWriter
has 2 required parameters (--template and --output_directory) and one
optional parameter (--log)

:param template: ReportWriter template
:param output_directory: location of the output files
:param log: controls the logging level: (CRITICAL, ERROR, WARNING,INFO
                                           DEBUG, NOTSET)
'''

import sys
import os
import logging
import json
from argparse import ArgumentParser

import excel_utils as excel
import report_utils as report
import template
import sparql
from report_template import ReportTemplate

if __name__ == "__main__":
    parser = ArgumentParser(
             prog="reportWriter",
             description="Create output based on a Report Template.")

    parser.add_argument(
            '--template',
            dest="report_template",
            required=True,
            help="EVS Report Template.")

    parser.add_argument(
            '--output_directory',
            dest="output_directory",
            required=True,
            help="Output Direictory.")

    parser.add_argument(
        '--log',
        dest="loglevel",
        default="WARNING",
        required=False,
        help="Specify the log level: DEBUG, INFO, WARNING, ERROR, CRITICAL"
    )

    args = parser.parse_args()

    #
    # Setup Logging
    #
    logging.basicConfig(format='%(levelname)s:%(module)s:%(message)s',
                        level=args.loglevel)
    log = logging.getLogger(__name__)

    try:
        template_file = open(args.report_template,"r")
    except IOError:
        print("The Report Template does not exit")
        sys.exit(1)

    basename = os.path.basename(args.report_template)
    output_file_name = args.output_directory + "/" + basename
    try:
        output_file = open(output_file_name,"w")
    except IOError:
        print("The Output file can not be created")
        sys.exit(1)

    #
    # Parse the template
    #
    report_template = template.parse_template(template_file)
    report_columns = report_template.report_columns

    if log.isEnabledFor(logging.INFO):
        for column in report_columns:
            log.info(column.label)

    #
    # Deterimine the root concept, then retrieve the
    # details for the the concept.
    #
    root_concept_code = report_template.root_concept_code
    root_concept = sparql.get_concept(root_concept_code)
    if root_concept is None:
        print("Failed to locate the Root Concept")
        sys.exit(1)
    junk,uri_short = root_concept['uri'].split("#")
    root_concept['code'] = root_concept_code
    log.info("Root Concept Code: " + root_concept['code'])
    root_concept['uri_short'] = uri_short
    root_concept['properties'] = sparql.get_properties(root_concept_code)
    root_concept['axioms'] = sparql.get_axioms(root_concept['uri_short'])

    #
    # Printout report header
    #
    report.print_report_header(report_columns,output_file)

    #
    # Find all the concepts that are in this subset,
    # then recurse down through all the children.
    #
    report.process_concept_in_subset(root_concept,report_columns,output_file)
    report.process_concept_children(root_concept,report_columns,output_file)

    output_file.close()
    excel.write_excel(output_file_name,report_columns)
