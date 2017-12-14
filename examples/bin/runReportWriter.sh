#!/bin/sh

export LOG_FILE="../logs/rw.log"
export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK="ERROR"
export LOGGING_LEVEL_GOV_NIH_NCI_EVS_RW="INFO"

export STARDOG_USERNAME="adminuser"
export STARDOG_PASSWORD="adminpassword"
export STARDOG_URL="http://localhost:5820/NCIT2"
export STARDOG_QUERY_URL="http://localhost:5820/NCIT2/query"
export STARDOG_GRAPH_NAME="http://NCI_T"

export STARDOG_OWL_FILENAME="Thesaurus.owl"
export STARDOG_READ_TIMEOUT="10000"
export STARDOG_CONNECT_TIMEOUT="10000"

export RW_TEMPLATE_DIRECTORY="../templates"
export RW_OUTPUT_DIRECTORY="../output"

# Run Association Report
if [ "$#" -eq 2 ]; then
    java -Xms2g -Xmx2G -jar reportwriter-program-0.0.1-SNAPSHOT.jar --templateFile $1 --outputFile $2
    exit
fi

# Run ConceptList Report
if [ "$#" -eq 3 ]; then
    java -Xms2g -Xmx2G -jar reportwriter-program-0.0.1-SNAPSHOT.jar --templateFile $1 --outputFile $2 --conceptFile $3
    exit
fi

