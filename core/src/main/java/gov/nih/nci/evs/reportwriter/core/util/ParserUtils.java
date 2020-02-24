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
import org.apache.commons.codec.binary.Base64;


/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2008-2017 NGIS. This software was developed in conjunction
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
 *      "This product includes software developed by NGIS and the National
 *      Cancer Institute."   If no such end-user documentation is to be
 *      included, this acknowledgment shall appear in the software itself,
 *      wherever such third-party acknowledgments normally appear.
 *   3. The names "The National Cancer Institute", "NCI" and "NGIS" must
 *      not be used to endorse or promote products derived from this software.
 *   4. This license does not authorize the incorporation of this software
 *      into any third party proprietary programs. This license does not
 *      authorize the recipient to use any trademarks owned by either NCI
 *      or NGIS
 *   5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 *      WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *      OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE
 *      DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE,
 *      NGIS, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT,
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
 *     Initial implementation kim.ong@ngc.com
 *
 */


public class ParserUtils {
	public ParserUtils() {

	}


    public Vector getValues(Vector v, int k) {
		if (v == null) return null;
		Vector w = new Vector();
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, '|');
			String value = (String) u.elementAt(k);
			w.add(value);
		}
		return w;
	}

    public String getValue(String t) {
		if (t == null) return null;
		Vector v = parseData(t, '|');
		if (v.size() == 0) return null;
		return (String) v.elementAt(v.size()-1);
	}

	public String parseLabel(Vector v) {
		if (v == null) return null;
		String t = (String) v.elementAt(0);
		return getValue(t);
	}

	public Vector getResponseVariables(Vector v) {
		if (v == null) return null;
		Vector w = new Vector();
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, '|');
			String var = (String) u.elementAt(0);
			if (!w.contains(var)) {
				w.add(var);
			}
		}
		return w;
	}

	public String getVariableName(String line) {
		Vector u = parseData(line, '|');
		return (String) u.elementAt(0);
	}


	public Vector getResponseValues(Vector v) {
		if (v == null || v.size() == 0) return null;
		Vector w = new Vector();
		Vector vars = getResponseVariables(v);
		String firstVar = (String) vars.elementAt(0);
		String[] values = new String[vars.size()];
		for (int i=0; i<vars.size(); i++) {
			values[i] = null;
		}
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			String var = getVariableName(line);
			if (var.compareTo(firstVar) == 0 && values[0] != null) {
				StringBuffer buf = new StringBuffer();
				for (int j=0; j<vars.size(); j++) {
					String t = values[j];
					if (t == null) {
						t = "null";
					}
					buf.append(t);
					if (j < vars.size()-1) {
						buf.append("|");
					}
				}
				String s = buf.toString();
				w.add(s);

				for (int k=0; k<vars.size(); k++) {
					values[k] = null;
			    }
		    }
		    String value = getValue(line);
			for (int k=0; k<vars.size(); k++) {
				if (var.compareTo((String) vars.elementAt(k)) == 0) {
					values[k] = value;
				}
			}

		}
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<vars.size(); i++) {
			String t = values[i];
			if (t == null) {
				t = "null";
			}
			buf.append(t);
			if (i < vars.size()-1) {
				buf.append("|");
			}
		}
		String s = buf.toString();
		w.add(s);
		return w;
	}

    public String extractLabel(String line) {
		if (line == null) return null;
		int n = line.lastIndexOf("#");
		if (n == -1) return line;
		return line.substring(n+1, line.length());
	}

	public Vector formatOutput(Vector v) {
		if (v == null) return null;
		if (v.size() == 0) return new Vector();
		v = getResponseValues(v);
		//v = new SortUtils().quickSort(v);
		return v;
	}

    public static Vector parseData(String line, char delimiter) {
		if(line == null) return null;
		Vector w = new Vector();
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<line.length(); i++) {
			char c = line.charAt(i);
			if (c == delimiter) {
				w.add(buf.toString());
				buf = new StringBuffer();
			} else {
				buf.append(c);
			}
		}
		w.add(buf.toString());
		return w;
	}


    public static Vector<String> parseData(String line) {
		if (line == null) return null;
        String tab = "|";
        return parseData(line, tab);
    }

    public static Vector<String> parseData(String line, String tab) {
		if (line == null) return null;
        Vector data_vec = new Vector();
        StringTokenizer st = new StringTokenizer(line, tab);
        while (st.hasMoreTokens()) {
            String value = st.nextToken();
            data_vec.add(value);
        }
        return data_vec;
    }
}
