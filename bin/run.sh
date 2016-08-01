#!/bin/bash


Templates=(
CDISC_SDTM_Anatomical_Location.txt
CDISC_SDTM_Directionality_Terminology.txt
CDISC_SDTM_Laterality_Terminology.txt
CDRH_Subset_REPORT.txt
FDA-SPL_Country_Code_REPORT.txt
FDA-UNII_Subset_REPORT.txt
FDA_County_Report.txt
FDA_Quality_of_Metrics_Terminology.txt
GENC_Subset_Report.txt
GUDID_Subset_Report.txt
"Individual_Case_Safety_(ICS)_Subset_REPORT.txt"
NCPDP_Report.txt
NICHD_NRNT_Report.txt
NICHD_Neurological_Development_Terminology.txt
NICHD_Newborn_Screening_Terminology.txt
NICHD_Pediatric_Adverse_Events.txt
NICHD_Pediatric_Immunization_Terminology.txt
NICHD_Pediatric_Medical_Device_Terminology.txt
NICHD_Pediatric_Nephrology_Terminology.txt
NICHD_Pediatric_Report.txt
NICHD_Perinatal.txt
NICHD_Rheumatology_Report.txt
NICHD_Subset_Report.txt
Neoplasm_Core.txt
"Structured_Product_Labeling_(SPL)_REPORT.txt"
eStability_Subset_Report.txt
)

PYTHONPATH="../lib"
export PYTHONPATH

for template in ${Templates[*]}
do
    echo "#############################################################"
    echo "Working on $template"
    echo "#############################################################"
    time python reportWriter.py --template ../templates/${template} --output_directory ../output --log WARNING
    echo ""
    echo ""
done
