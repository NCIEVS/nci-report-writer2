# nci-report-writer2
SPARQL-based Report Writer query and reporting tool.

# Overview
The ReportWriter SPARQL application is a prototype for creating reports
using a SPARQL endpoint. This version was written in Java and was tested
against a Jena triple store.

This repository has several parts:

1. web/: Contains the complete Java application source code, combining both the report generation engine and the web interface into a single module. The
application generates reports based on YAML configuration templates and provides a web interface for managing templates and viewing results.
2. database/: Contains the init.sql database dump file used for populating the project MySQL database. There is a simple README file contained in this directory that outlines the steps for building a new database.
3. examples/: Contains sample templates in YAML format that demonstrate various report configurations.
4. frontend/: Contains the Angular web application that provides the user interface for building new report templates, running existing reports, and viewing completed reports.

# Architecture
The application uses a Spring Boot backend with JPA for database access and REST endpoints for the frontend. The core report generation functionality uses SPARQL queries against triple stores to extract data and format it according to configurable templates.

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
export GRAPHDB_URL="http://localhost:3030/NCIT2"
export GRAPHDB_MONTHLY_QUERY_URL="http://localhost:3030/NCIT2/query"
export GRAPHDB_MONTHLY_GRAPH_NAME="http://NCI_T"
export GRAPHDB_WEEKLY_QUERY_URL="http://localhost:3030/NCIT2/query"
export GRAPHDB_WEEKLY_GRAPH_NAME="http://NCI_T"
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

In the web/build/libs directory the nci-report-writer-{version}-SNAPSHOT.war file should now exist.

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
curl http://localhost:8080/ncireportwriter2/reportwriter/
```

It should contain the content "Welcome to ReportWriter".

# Running the frontend

This launches the app on http://localhost:4200

```
make runfrontend

```


Open a web browser to http://localhost:4200/ncreportwriter/home to view the application.


