# nci-report-writer2
SPARQL-based Report Writer query and reporting tool.

# Overview
The ReportWriter SPARQL application is a prototype for creating reports
using a SPARQL endpoint. This version was written in Java and was tested
against a Stardog/Jena triple store.

This repository has several parts:

1. core: This is Java code that generates a report based on
a YAML configuration template. This is library code that is used
by **web** application.

2. database: Contains the init.sql database dump file used for populating 
the project mysql database. There is a simple README file contained in this 
directory, that outlines the steps for building a new database.

3. examples: Contains sample templates in YAML format.

4. web: This is a Web application that can be used to build
new report templates, run existing reports, and view completed reports.
The web application uses the **core** to generate the reports.

# Building with Gradle
All the projects in this repository, require that environment variables be set
for your environment, before building jar files. These environment variables
override default variables set in the application.yml file. These variables are 
in a reportWriter.sh program in the config directory. Alternatively, they can be 
added to a .bashrc file for initialization. Below is an example, but most of the 
content should be replaced with content appropriate for your environment:

```
export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK="ERROR"
export LOGGING_LEVEL_GOV_NIH_NCI_EVS_RW="INFO"
export GRAPHDB_USERNAME="GRAPHDB_USERNAME"
export GRAPHDB_PASSWORD="GRAPHDB_PASSWORD"
export GRAPHDB_URL="http://localhost:5820/NCIT2"
export GRAPHDB_QUERY_URL="http://localhost:5820/NCIT2/query"
export GRAPHDB_GRAPH_NAME="http://NCI_T"
export GRAPHDB_OWL_FILENAME="Thesaurus.owl"
export GRAPHDB_READ_TIMEOUT="10000"
export GRAPHDB_CONNECT_TIMEOUT="10000"
export RW_BIN_DIRECTORY="/tmp/bin"
export RW_TEMPLATE_DIRECTORY="/tmp/templates"
export RW_OUTPUT_DIRECTORY="/tmp/output"
export RW_API_DATASOURCE_URL="jdbc:mysql://localhost:3312/reportwriter?useSSL=false&allowPublicKeyRetrieval=true"
export RW_API_DATASOURCE_USERNAME="MYSQL_USERNAME"
export RW_API_DATASOURCE_PASSWORD="MYSQL_PASSWORD"
```

# Building the database

Follow the instructions in the README in the database/ folder.

# Building the server code

```
make build
```

In the web/build/libs directory the web-2.3.0-SNAPSHOT.war file should now exist.

# Building the frontend code

```
make frontend
```

In the frontend/dist directory, there should now be static files from the build.


# Running the server

This launches the war file

```
make run
```

To confirm that the server is running, run 

```
curl http://localhost:8080/ncreportwriter/reportwriter/
```

It should contain the content "Welcome to ReportWriter".

# Running the frontend

This launches the app on http://localhost:4200

```
make runfrontend

```


Open a web browser to http://localhost:8080/ncreportwriter/home to view the application.


