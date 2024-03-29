/*L
 * Copyright Northrop Grumman Information Technology.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/nci-report-writer/LICENSE.txt for details.
 */

package gov.nih.nci.evs.reportwriter.formatter;

//import gov.nih.nci.evs.reportwriter.service.*;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */

/**
 * @author EVS Team (David Yee)
 * @version 1.0
 */

public class AsciiToHtmlFormatter extends FileFormatterBase
    implements FormatterConstant {

	private static final Logger _logger = LoggerFactory.getLogger(AsciiToHtmlFormatter.class);
    private static boolean _specialCases_CDISC = true;//SpecialCases.CDISC.ON;

    public Boolean convert(String textfile, String delimiter) throws Exception {
        return convert2(textfile, "htm", delimiter);
    }

    public Boolean convert(String textfile, String delimiter, String outfile)
            throws Exception {
        TabFormatterFileOutputStream out = new TabFormatterFileOutputStream(outfile);
        printHeader(out);
        printContent(out, textfile, delimiter);
        printFooter(out);
        out.close();
        return Boolean.TRUE;
    }

    protected void printContent(TabFormatterInterface out, String textfile,
        String delimiter) throws Exception {
        BufferedReader br = getBufferReader(textfile);

        // Prints topmost report banner:
        Vector<String> headings = getColumnHeadings(textfile, delimiter);
        out.writeln_indent("<tr><td colspan=\"" + (headings.size() + 1)
            + "\" class=\"dataTablePrimaryLabel\" height=\"20\">");
        out.writeln_inden1("Report: " + getReportName(out.getFilename()));
        out.writeln_undent("</td></tr>");

        // Prints table heading:
        headings.add(0, "#");
        out.writeln_indent("<tr class=\"dataTableHeader\">");
        for (String heading : headings)
            out.writeln_inden1("<th class=\"dataCellText\">" + heading
                + "</th>");
        out.writeln_undent("</tr>");

        // Note: Special Case for CDISC STDM Terminology report.
        int extensible_col = -1;
        if (_specialCases_CDISC)
            extensible_col = findColumnIndicator(headings, "Extensible");

        // Prints contents:
        int row = 0;
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            if (line.length() <= 0)
                continue;

            Vector<String> v = parseData(line, delimiter);
            v.add(0, Integer.toString(row)); // From adding # column
            int numMissingCells = headings.size() - v.size();
            for (int i = 0; i < numMissingCells; ++i)
                v.add(null);

            String rowColor = row % 2 == 1 ? "dataRowDark" : "dataRowLight";
            out.writeln_indent("<tr class=\"" + rowColor + "\">");
            String bgColor = "";
            if (_specialCases_CDISC && extensible_col >= 0) {
                // Note: Special Case for CDISC STDM Terminology report.
                String eValue = v.get(extensible_col - 1); // -1 from # column
                if (eValue == null || eValue.trim().length() <= 0)
                    bgColor = " bgColor=\"skyblue\"";
            }

            for (int col = 0; col < v.size(); col++) {
                if (row <= 0) // Skip heading row
                    continue;

                String value = v.get(col);
                if (value == null || value.trim().length() <= 0)
                    value = "&nbsp;";
                else if (_ncitCodeColumns.contains(col - 1)) // -1 from # column
                    value = getNCItCodeUrl(value);
                out.writeln_inden1("<td class=\"dataCellText\"" + bgColor + ">"
                    + value + "</td>");
            }
            out.writeln_undent("</tr>");
            row++;
        }
        br.close();
    }

    protected void printHeader(TabFormatterInterface out) throws Exception {
        out.writeln_normal("<html>");
        out.writeln_indent("<head>");
        out.writeln_inden1("<title>" + getReportName(out.getFilename())
            + "</title>");
        printStyles(out);
        out.writeln_undent("</head>");
        out.writeln_indent("<body>");
        out.writeln_indent("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"dataTable\" width=\"100%\">");
    }

    protected void printFooter(TabFormatterInterface out) throws Exception {
        out.writeln_undent("</table>");
        out.writeln_undent("</body>");
        out.writeln_normal("</html>");
    }

    private void printStyles(TabFormatterInterface out) throws Exception {
        out.writeln_indent("<style>");
        out.writeln_normal("  * {");
        out.writeln_normal("    font-family: Helvetica, Geneva, Times, Verdana, sans-serif;");
        out.writeln_normal("    font-size: 7pt;");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataTablePrimaryLabel{ /* for the first row */");
        out.writeln_normal("    font-family:arial,helvetica,verdana,sans-serif;");
        out.writeln_normal("    font-size:0.9em;");
        out.writeln_normal("    font-weight:bold;");
        out.writeln_normal("    background-color:#5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    color:#FFFFFF; /* constant: white */");
        out.writeln_normal("    border-top:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    border-left:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    border-right:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    padding-left:0.4em;");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataTable{ /* for the main table below the labels */");
        out.writeln_normal("    font-family:arial,helvetica,verdana,sans-serif;");
        out.writeln_normal("    font-size:0.9em;");
        out.writeln_normal("    color:#000000; /* constant: black */");
        out.writeln_normal("    border-top:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    border-left:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataTableHeader{ /* for the horizontal column headers */");
        out.writeln_normal("    font-family:arial,helvetica,verdana,sans-serif;");
        out.writeln_normal("    background-color:#CCCCCC; /* constant: medium gray */");
        out.writeln_normal("    color:#000000; /* constant: black */");
        out.writeln_normal("    font-weight:bold;");
        out.writeln_normal("    border-right:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    border-bottom:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataCellText{ /* for text output cells */");
        out.writeln_normal("    border-right:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    border-bottom:1px solid #5C5C5C; /* constant: dark gray */");
        out.writeln_normal("    text-align:left;");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataRowLight{ /* for the light color of alternating rows */");
        out.writeln_normal("    background-color:#FFFFFF; /* constant: white */");
        out.writeln_normal("    color:#000000; /* constant: black */");
        out.writeln_normal("  }");
        out.writeln_normal("  .dataRowDark{ /* for the dark color of alternating rows */");
        out.writeln_normal("    background-color:#F4F4F5; /* constant: light gray */");
        out.writeln_normal("    color:#000000; /* constant: black */");
        out.writeln_normal("  }");
        out.writeln_undent("</style>");
    }

    // -- NCItCodeUrl ----------------------------------------------------------
    public enum DisplayNCItCodeUrl {
        CurrentWindow, SeparateSingleWindow, SeparateMultipleWindows;

        public static DisplayNCItCodeUrl value_of(String text) {
            for (DisplayNCItCodeUrl v : values()) {
                if (v.name().equalsIgnoreCase(text))
                    return v;
            }

            DisplayNCItCodeUrl value = SeparateSingleWindow;
            _logger.warn("No matching enum for: " + text);
            _logger.warn("  * Defaulting to: " + value);
            return value;
        }
    };

    private DisplayNCItCodeUrl _displayNCItCodeUrl =
        DisplayNCItCodeUrl.CurrentWindow;

    public void setDisplayNCItCodeUrl(DisplayNCItCodeUrl displayNCItCodeUrl) {
        _displayNCItCodeUrl = displayNCItCodeUrl;
    }

    public void setDisplayNCItCodeUrl(String displayNCItCodeUrl) {
        _displayNCItCodeUrl = DisplayNCItCodeUrl.value_of(displayNCItCodeUrl);
    }

    public DisplayNCItCodeUrl getDisplayNCItCodeUrl() {
        return _displayNCItCodeUrl;
    }

    protected String getNCItCodeUrl(String code) {
        String ncitCodeUrl = super.getNCItCodeUrl(code);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<a href=\"" + ncitCodeUrl + "\"");
        if (_displayNCItCodeUrl == DisplayNCItCodeUrl.SeparateMultipleWindows)
            buffer.append(" target=\"_blank\"");
        else if (_displayNCItCodeUrl == DisplayNCItCodeUrl.SeparateSingleWindow)
            buffer.append(" target=\"rwNcitCodeUrl\"");
        buffer.append(">");
        buffer.append(code);
        buffer.append("</a>");
        return buffer.toString();
    }

    // -- Miscellaneous --------------------------------------------------------
    protected String getReportName(String filename) {
        String reportName = filename.replace("__", " (");
        reportName = reportName.replace(".htm", ")");
        return reportName;
    }

    // -- Test Program- --------------------------------------------------------
    public static void test(String textfile, int[] ncitCodeColumns) {
        try {
            String delimiter = "\t";
            AsciiToHtmlFormatter formatter = new AsciiToHtmlFormatter();
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
        test(CDISC_SDTM_FILE, CDISC_SDTM_NCIT_COLUMNS);
        test(CDISC_SUBSET_FILE, CDISC_SUBSET_NCIT_COLUMNS);
        test(CDRH_SUBSET_FILE, CDRH_COLUMNS);
        test(FDA_SPL_FILE, FDA_SPL_NCIT_COLUMNS);
        test(FDA_UNII_FILE, FDA_UNII_NCIT_COLUMNS);
        test(ICS_SUBSET_FILE, ICS_SUBSET_NCIT_COLUMNS);
        test(NICHD_SUBSET_FILE, NICHD_SUBSET_COLUMNS);
        test(SPL_FILE, SPL_NCIT_COLUMNS);
        _logger.debug("Done");
    }
*/
}
