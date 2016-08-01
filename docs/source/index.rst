.. ReportWriter documentation master file, created by
   sphinx-quickstart on Tue Jul 26 11:09:36 2016.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to ReportWriter's documentation!
========================================

Overview
########################################
The ReportWriter application is a prototype for creating reports using a
SPARQL endpoint, which will eventually replace the current process that uses
the LexEVS API. For now we are using Python to implement the functionality, but
there needs to be discussion on whether we should switch to JAVA, especially if
there is a decision to implement a WEB UI.

The current application has been developed using the Apache Jena - Fuseki
triple store, and is currently being tested using the StarDog RDF database.

.. warning:: Not all the functionality of the current ReportWriter has been
             implemented or tested. The Excel output definitely needs work and
             needs documentation on how it is supposed to work.

.. warning:: Some connection information to the SPARQL endpoints has been hard
             coded, and needs to be factored out into a configuration properties
             file. For now the "lib/sparql.py" needs to be edited to point to
             the proper SPARQL endpoing.

Contents:

.. toctree::
   :maxdepth: 2

   runningReportWriter
   bin
   lib

Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`
