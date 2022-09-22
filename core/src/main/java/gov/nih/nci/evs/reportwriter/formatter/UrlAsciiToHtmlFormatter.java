/*L
 * Copyright Northrop Grumman Information Technology.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/nci-report-writer/LICENSE.txt for details.
 */

package gov.nih.nci.evs.reportwriter.formatter;

import java.io.*;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */

/**
 * @author EVS Team (David Yee)
 * @version 1.0
 */

public class UrlAsciiToHtmlFormatter extends AsciiToHtmlFormatter implements
        FormatterConstant {
	private static final Logger _logger = LoggerFactory.getLogger(UrlAsciiToHtmlFormatter.class);


    protected Boolean convert2(String textfile, String toExt, String delimiter)
            throws Exception {
        int i = textfile.lastIndexOf("/");
        String outfile = textfile.substring(i + 1);
        i = outfile.lastIndexOf(".");
        outfile = outfile.substring(0, i) + "." + toExt;
        outfile = outfile.replaceAll("%20", " ");
        return convert(textfile, delimiter, outfile);
    }

    public Boolean convert(String textfile, String delimiter, String outfile)
            throws Exception {
        TabFormatterStringBuffer out =
            new TabFormatterStringBuffer(outfile);
        printHeader(out);
        printContent(out, textfile, delimiter);
        printFooter(out);
        _logger.debug(out.toString());
        return Boolean.TRUE;
    }

    protected BufferedReader getBufferReader(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        _logger.debug("fileUrl: " + fileUrl);
        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br;
    }

    protected String getReportName(String filename) {
        String reportName = filename.replace(".htm", "");
        return reportName;
    }

    // -- Test Program- --------------------------------------------------------
    public static void test(String textfile, int[] ncitCodeColumns) {
        try {
            String delimiter = "\t";
            UrlAsciiToHtmlFormatter formatter = new UrlAsciiToHtmlFormatter();
            formatter.setNcitUrl("http://ncit.nci.nih.gov/ncitbrowser/");
            formatter.setNcitCodeColumns(ncitCodeColumns);
            formatter
                .setDisplayNCItCodeUrl(DisplayNCItCodeUrl.SeparateSingleWindow);
            formatter.convert(textfile, delimiter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) {
        test(CDISC_SDTM_REPORT_URL, CDISC_SDTM_NCIT_COLUMNS);
        // test(dir + "CDISC_Subset_REPORT__10.06e.txt", new int[] { 1, 3 });
        test(CDRH_REPORT_URL, CDRH_COLUMNS);
        test(FDA_SPL_REPORT_URL, FDA_SPL_NCIT_COLUMNS);
        test(FDA_UNII_REPORT_URL, FDA_UNII_NCIT_COLUMNS);
        // test(dir + "Individual_Case_Safety_(ICS)_Subset_REPORT__10.06e.txt",
        // new int[] { 1, 3 });
        // test(dir + "Structured_Product_Labeling_(SPL)_REPORT__10.06e.txt",
        // new int[] { 1, 3 });
        _logger.debug("Done");
    }
*/
}
