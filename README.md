# nci-report-writer2
SPARQL-based Report Writer query and reporting tool.

# Overview
The ReportWriter SPARQL application is a prototype for creating reports
using a SPARQL endpoint. This version was written in Java and was tested
against a Jena triple store.

This repository has several parts:

1. `web/`: Java report engine and Spring Boot API, packaged with the compiled frontend in a WAR.
2. `database/`: MySQL schema and seed data for templates and task history.
3. `examples/`: Sample YAML report templates.
4. `frontend/`: Angular UI for editing templates, running reports, and downloading results.

# Architecture
The application uses a Spring Boot backend with JPA for database access and REST endpoints for the frontend. The core report generation functionality uses SPARQL queries against triple stores to extract data and format it according to configurable templates.

# Building with Gradle

Requires Java 25. Both projects use the Gradle 9.3.0 wrapper; the backend uses
Spring Boot 4.1.1. Set `JAVA_HOME` to your JDK 25 installation. The frontend
wrapper downloads the configured Node.js and npm versions.

Jackson 3 uses Jackson 2-compatible defaults to preserve existing JSON and YAML
formats. Tomcat 11.0.25 and Jackson 3.1.7 override Boot's managed versions for
security fixes; review these overrides when updating Boot.

External WAR deployment requires a Servlet 6.1 container such as Tomcat 11.
The Linux launcher defaults to `/usr/local/corretto-jdk25` and honors `JAVA_HOME`.
Set the systemd unit's `JAVA_HOME` to the deployment's JDK path.

Building and running the regression tests require no external services. To run
the application, set the following environment variables in your shell or a local
setup script. They override `application.yml`; replace the example connection
settings with those for your MySQL database and SPARQL endpoint. Graph timeouts
are in seconds, and the named graph is discovered from ontology metadata.

```
export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK="ERROR"
export LOGGING_LEVEL_GOV_NIH_NCI_EVS_RW="INFO"
export GRAPHDB_USERNAME="GRAPHDB_USERNAME"
export GRAPHDB_PASSWORD="GRAPHDB_PASSWORD"
export GRAPHDB_MONTHLY_QUERY_URL="http://localhost:3030/NCIT2/query"
export GRAPHDB_WEEKLY_QUERY_URL="http://localhost:3030/NCIT2/query"
export GRAPHDB_READ_TIMEOUT="120"
export GRAPHDB_CONNECT_TIMEOUT="10"
export RW_TEMPLATE_DIRECTORY="/tmp/templates"
export RW_OUTPUT_DIRECTORY="/tmp/output"
export RW_API_DATASOURCE_URL="jdbc:mysql://localhost:3312/reportwriter?useSSL=false&allowPublicKeyRetrieval=true"
export RW_API_DATASOURCE_USERNAME="MYSQL_USERNAME"
export RW_API_DATASOURCE_PASSWORD="MYSQL_PASSWORD"
```

Create the template/output directories before running. With Cygwin and a Windows
JDK, use Windows paths (for example `D:/reportwriter/output`) for these variables.

# Building the database

Follow [database/README](database/README). Reuse an existing Report Writer
database if available; importing `init.sql` replaces its tables and data.

# Building the server code

```
make build
```

In the web/build/libs directory the `ncireportwriter2-2.5.0.RELEASE.war` file should now exist.

`make build` packages the application and runs the encoding checks, skipping the
default test task. The regression tests use H2 and local fixtures without MySQL
or Jena. CI runs all regression and encoding tests.

```bash
make testjava       # Backend regression and encoding checks
make test           # Frontend Jest tests
```

For live report testing, configure the database, graph, and writable directories
above. Use a disposable test database with a small, nonempty Association or
Inverse Association template. The test creates tasks and report files.

```bash
export RW_TEST_TEMPLATE_ID=123 # Replace with the fixture's template ID
make integrationtest
```

The test starts its own server at `/ncireportwriter2`, uses the monthly endpoint,
and waits up to five minutes. It checks completion and TXT/XLS/template/log
downloads. It is separate from the build and CI regression tests.

# Building the frontend code

```
make frontend
```

This builds `frontend/dist/` and copies it into `web/src/main/resources/static/`.
Run `make build` afterward to include the updated frontend in the WAR.


# Running the server

Build and launch the WAR with Gradle's Java 25 toolchain and the database/graph
settings exported in your shell:

```
make run
```

For `Access denied for user` errors, check the `RW_API_DATASOURCE_*` settings in
this shell. Without them, the app connects to `localhost:3306/rw` as `root`.
The subsequent Hibernate dialect error is caused by the failed connection.
See [database setup](database/README) for connection settings.

Open http://localhost:8080/ncireportwriter2/ to use the packaged application.
Choose a template, click **Run**, select the monthly or weekly graph, and find
the completed report under **All Report Tasks**. The configured graph service
must be running for report generation.

To confirm that the server is running, run 

```
curl http://localhost:8080/ncireportwriter2/reportwriter/
```

It should contain the content "Welcome to ReportWriter".

# Running the frontend

For frontend development, run the backend with `make run` in another terminal.
The Angular development server proxies API requests to that backend:

```
make runfrontend

```


Open http://localhost:4200/home. This separate frontend server is not needed
when using the packaged application on port 8080.


