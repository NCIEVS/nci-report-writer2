#!/bin/bash

if [ $# != 4 ]
  then
    echo ""
    echo "Usage: loadData.sh Host, User, Password, Database"
    echo ""
    exit 1
fi

HOST=$1
USER=$2
PASSWORD=$3
DATABASE=$4

CMD="mysql --local_infile -h $HOST -u $USER -p$PASSWORD $DATABASE"

echo "Loading lk_association"
$CMD < ../load/lk_association.load

echo "Loading lk_display"
$CMD < ../load/lk_display.load

echo "Loading lk_group"
$CMD < ../load/lk_group.load

echo "Loading lk_property"
$CMD < ../load/lk_property.load

echo "Loading lk_property_type"
$CMD < ../load/lk_property_type.load

echo "Loading lk_report_status"
$CMD < ../load/lk_report_status.load

echo "Loading lk_report_template_status"
$CMD < ../load/lk_report_template_status.load

echo "Loading lk_report_template_type"
$CMD < ../load/lk_report_template_type.load

echo "Loading lk_source"
$CMD < ../load/lk_source.load

echo "Loading lk_subsource"
$CMD < ../load/lk_subsource.load
