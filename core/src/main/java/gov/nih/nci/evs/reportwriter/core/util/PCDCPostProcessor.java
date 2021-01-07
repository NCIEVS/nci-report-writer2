package gov.nih.nci.evs.reportwriter.core.util;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.*;
import org.json.*;

/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2021 MSC. This software was developed in conjunction
 * with the National Cancer Institute, and so to the extent government
 * employees are co-authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *   1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the disclaimer of Article 3,
 *      below. Redistributions in binary form must reproduce the above
 *      copyright notice, this list of conditions and the following
 *      disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *   2. The end-user documentation included with the redistribution,
 *      if any, must include the following acknowledgment:
 *      "This product includes software developed by MSC and the National
 *      Cancer Institute."   If no such end-user documentation is to be
 *      included, this acknowledgment shall appear in the software itself,
 *      wherever such third-party acknowledgments normally appear.
 *   3. The names "The National Cancer Institute" and "MSC" must
 *      not be used to endorse or promote products derived from this software.
 *   4. This license does not authorize the incorporation of this software
 *      into any third party proprietary programs. This license does not
 *      authorize the recipient to use any trademarks owned by either NCI
 *      or MSC
 *   5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 *      WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *      OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE
 *      DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE,
 *      MSC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT,
 *      INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *      BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *      LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *      CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *      LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *      ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *      POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */

/**
 * @author EVS Team
 * @version 1.0
 *
 * Modification history:
 *     Initial implementation kim.ong@nih.gov
 *
 */


public class PCDCPostProcessor {
    String ordered_file = null;
    HashMap row2CodeAndSourceMap = new HashMap();
    HashMap codeAndSource2RowMap = new HashMap();
    Vector row_data = null;
    Vector ordered_row_data = null;
    Vector ordered_data = null;
    String rw2textfile = null;
    Vector member_concepts = null;
    HashMap rw2Code2RowDataMap = null;

    public PCDCPostProcessor() {

    }

    public HashMap generateRW2Code2RowDataMap(String rw2textfile) {
		rw2Code2RowDataMap = new HashMap();
		Vector v = ReportLoader.readFile(rw2textfile);
		for (int i=1; i<v.size(); i++) { // skip heading
			String line = (String) v.elementAt(i);
			Vector u = ReportLoader.parseData(line, '\t');
			String code = (String) u.elementAt(2);
			rw2Code2RowDataMap.put(code, line);
		}
		return rw2Code2RowDataMap;
	}

    public Vector convertRowData(String rw2textfile, String ordered_file) {
		Vector w = new Vector();
        set_ordered_file(ordered_file);
        Vector rw2_data = ReportLoader.readFile(rw2textfile);
        String heading = (String) rw2_data.elementAt(0);
        w.add("Row\t" + heading);
        Vector u_heading = ReportLoader.parseData(heading, '\t');
        int source_code_col = -1;
        for (int i=0; i<u_heading.size(); i++) {
			String col_nm = (String) u_heading.elementAt(i);
			if (col_nm.compareTo("Source Code") == 0) {
				source_code_col = i;
				break;
			}
		}
        rw2Code2RowDataMap = generateRW2Code2RowDataMap(rw2textfile);
        for (int i=1; i<row_data.size(); i++) {
			String line = (String) row_data.elementAt(i);
			Vector u0 = ReportLoader.parseData(line, '|');
			String row = null;
			String member_concept_code = null;
			String sourceCode = "";
			row = (String) u0.elementAt(0);
			member_concept_code = (String) u0.elementAt(1);
            if (u0.size() == 3) {
				sourceCode = (String) u0.elementAt(2);
			}
            String rw2RowData = (String) rw2Code2RowDataMap.get(member_concept_code);
            String newRowData = convertRowData(rw2RowData, row, sourceCode, source_code_col);
            w.add(newRowData);
		}
		return w;
    }

    public String convertRowData(String rw2RowData, String row, String sourceCode, int source_code_col) {
		Vector u = ReportLoader.parseData(rw2RowData, '\t');
		int n = u.size();
		StringBuffer buf = new StringBuffer();
		buf.append(row).append('\t');
		for (int i=0; i<u.size(); i++) {
			String value = (String) u.elementAt(i);
			if (i == source_code_col) {
				value = sourceCode;
			}
			buf.append(value);
			if (i < u.size()-1) {
				buf.append('\t');
			}
		}
		return buf.toString();
	}

    public void set_ordered_file(String ordered_file) {
		this.ordered_file = ordered_file;
        this.row2CodeAndSourceMap = new HashMap();
        this.codeAndSource2RowMap = new HashMap();
        this.ordered_data = ReportLoader.readFile(ordered_file);
        this.row_data = new Vector();
        for (int i=0; i<this.ordered_data.size(); i++) {
			String line = (String) this.ordered_data.elementAt(i);
			line = line.trim();
			if (line.length() > 0) {
				Vector u = ReportLoader.parseData(line, '\t');
				String row = (String) u.elementAt(0);
				String member_conept_code = (String) u.elementAt(3);
				String source_code = null;
				if (u.size() > 7) {
					source_code = (String) u.elementAt(7);
				}
				if (source_code == null) {
					this.row2CodeAndSourceMap.put(row, member_conept_code);
					this.codeAndSource2RowMap.put(member_conept_code, row);
					this.row_data.add(row + "|" + member_conept_code);
				} else {
					this.row2CodeAndSourceMap.put(row, member_conept_code + "|" + source_code);
					this.codeAndSource2RowMap.put(member_conept_code + "|" + source_code, row);
					this.row_data.add(row + "|" + member_conept_code + "|" + source_code);
				}
			}
		}
	}

	public static void validateVS(String row_data_file, String vs_data_file) {
		//row_data: 6Jan_AML_Ordered_row
		//vs_data: VS_C168549.txt
		Vector row_data = ReportLoader.readFile(row_data_file);
		Vector vs_data = ReportLoader.readFile(vs_data_file);

		Vector code_in_row_data = new Vector();
		for (int i=0; i<row_data.size(); i++) {
			String line = (String) row_data.elementAt(i);
			Vector u = ReportLoader.parseData(line, '|');
			String code = (String)u.elementAt(1);
			if (!code_in_row_data.contains(code)) {
				code_in_row_data.add(code);
			}
		}
		Vector code_in_vs_data = new Vector();
		for (int i=0; i<vs_data.size(); i++) {
			String line = (String) vs_data.elementAt(i);
			Vector u = ReportLoader.parseData(line, '|');
			String code = (String)u.elementAt(0);
			if (!code_in_vs_data.contains(code)) {
				code_in_vs_data.add(code);
			}
		}

        int knt = 0;
		for (int i=0; i<code_in_row_data.size(); i++) {
			String code = (String) code_in_row_data.elementAt(i);
			if (!code_in_vs_data.contains(code)) {
				knt++;
				System.out.println("WARNING: " + code + " in_row_data but not in_vs_data.");
			}
		}
		System.out.println("Number of concepts in_row_data but not in_vs_data: " + knt);

        knt = 0;
		for (int i=0; i<code_in_vs_data.size(); i++) {
			String code = (String) code_in_vs_data.elementAt(i);
			if (!code_in_row_data.contains(code)) {
				knt++;
				System.out.println("WARNING: " + code + " in_vs_data but not in_row_data.");
			}
		}
		System.out.println("Number of concepts in_vs_data but not in_row_data: " + knt);
	}

    public static void main(String[] args) {
		long ms = System.currentTimeMillis();
        String rw2textfile = args[0]; // "pcdc_aml_terminology.txt";
        String ordered_file = args[1]; // "6Jan_AML_Ordered.txt";
        PCDCPostProcessor test = new PCDCPostProcessor();
        int n = rw2textfile.lastIndexOf(".");
        String sorted_rw2textfile = "sorted_" + rw2textfile;
        test.set_ordered_file(ordered_file);
        Vector v = test.convertRowData(rw2textfile, ordered_file);
        ReportLoader.saveToFile(sorted_rw2textfile, v);
    }
}
