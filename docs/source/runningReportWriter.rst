Running ReportWriter
***********************************************************
The main program for generating a report is the reportWriter.py. The
reportWriter.py program was written using Python 3.X and will not run using
Python 2.X.

The program can be run from the command line. run::

    $ python reportWriter.py --template ./templates/CDRH_Subset_REPORT.txt
                             --output ./output --log WARINING

.. note::
    The --log parameter is optional, the --template and --output are required.

.. note::
    In the "bin" directory there is a sample run.sh script that can be used
    to run all the templates specified and provides an example of how to
    run the "reportWriter.py" program. This script is for a Linux/Mac based
    OS.
