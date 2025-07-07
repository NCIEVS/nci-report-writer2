#!/bin/sh

export LOG_FILE="../logs/rw.log"
export LOGGING_LEVEL_ORG_SPRINGFRAMEWORK="ERROR"
export LOGGING_LEVEL_GOV_NIH_NCI_EVS_RW="INFO"

export GRAPHDB_USERNAME="adminuser"
export GRAPHDB_PASSWORD="adminpassword"
export GRAPHDB_URL="http://localhost:5820/NCIT2"
export GRAPHDB_QUERY_URL="http://localhost:5820/NCIT2/query"
export GRAPHDB_GRAPH_NAME="http://NCI_T"

export GRAPHDB_OWL_FILENAME="Thesaurus.owl"
export GRAPHDB_READ_TIMEOUT="10000"
export GRAPHDB_CONNECT_TIMEOUT="10000"

export RW_TEMPLATE_DIRECTORY="../templates"
export RW_OUTPUT_DIRECTORY="../output"

# Run Association Report
if [ "$#" -eq 2 ]; then
    java -Xms2g -Xmx2G -jar reportwriter-program-1.0.0-RELEASE.jar --templateFile $1 --outputFile $2
    exit
fi

# Run ConceptList Report
if [ "$#" -eq 3 ]; then
    java -Xms2g -Xmx2G -jar reportwriter-program-1.0.0-RELEASE.jar --templateFile $1 --outputFile $2 --conceptFile $3
    exit
fi

