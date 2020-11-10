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
import java.text.*;
import gov.nih.nci.evs.reportwriter.core.model.template.*;

/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2020 MSC. This software was developed in conjunction
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
 *   3. The names "The National Cancer Institute", "NCI" and "MSC" must
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


public class TemplateLoader {

    public TemplateLoader() {

    }

    public String getValue(Vector lines, String key) {
		for (int i=0; i<lines.size(); i++) {
			String line = (String) lines.elementAt(i);
			line = line.trim();
			String value = getValue(line, key);
			if (value != null) return value;
		}
		return null;
	}

    public String getValue(String line, String key) {
		line = line.trim();
		Vector u = parseData(line, ':');
		String t = (String) u.elementAt(0);
		if (t.compareTo(key) == 0) {
			String s = (String) u.elementAt(1);
			s = s.trim();
			if (s.startsWith("\"")) {
				s = s.substring(1, s.length());
			}
			if (s.endsWith("\"")) {
				s = s.substring(0, s.length()-1);
			}
			return s;
		}
		return  null;
	}

	public List<TemplateColumn> readTemplateColumns(Vector v) {
		List<TemplateColumn> list = new ArrayList<TemplateColumn>();
		boolean istart = false;
		TemplateColumn col = new TemplateColumn();
		col.setColumnNumber(0);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			line = line.trim();
			if (line.startsWith("columns:")) {
				istart = true;
			}
			if (istart) {
				if (line.indexOf("columnNumber") != -1) {
				    Vector u = parseData(line, ':');
				    String value = (String) u.elementAt(1);
				    value = value.trim();
				    if (col.getColumnNumber() != 0) {
				    	list.add(col);
					}
					col = new TemplateColumn();
					col.setColumnNumber(Integer.parseInt(value));
				} else {
					if (line.length() > 0) {
						Vector u = parseData(line, ':');
						String key = (String) u.elementAt(0);
						String value = (String) u.elementAt(1);

						value = value.trim();
						if (value.startsWith("\"")) {
							value = value.substring(1, value.length());
						}
						if (value.endsWith("\"")) {
							value = value.substring(0, value.length()-1);
						}

						key = key.trim();
						if (key.compareTo("label") == 0) {
							col.setLabel(value);
						} else if (key.compareTo("display") == 0) {
							col.setDisplay(value);
						} else if (key.compareTo("propertyType") == 0) {
							col.setPropertyType(value);
						} else if (key.compareTo("property") == 0) {
							col.setProperty(value);
						} else if (key.compareTo("source") == 0) {
							col.setSource(value);
						} else if (key.compareTo("group") == 0) {
							col.setGroup(value);
						} else if (key.compareTo("subsource") == 0) {
							col.setSubsource(value);
						} else if (key.compareTo("attr") == 0) {
							col.setAttr(value);
						}
					}
				}
			}
		}
		if (col.getColumnNumber() != 0) {
			list.add(col);
		}
		return list;
	}

	public static Vector readFile(String filename)
	{
		Vector v = new Vector();
		try {
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
						  new FileInputStream(filename), "UTF8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.length() > 0) {
					System.out.println(str);
					v.add(str);
				}
			}
            in.close();
		} catch (Exception ex) {
            ex.printStackTrace();
		}
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

	public Template load(String templateFile) {
		Vector v = readFile(templateFile);
		Template template = new Template();
		String key = "name";
		String value = getValue(v, key);
		template.setName(value);

		key = "rootConceptCode";
		value = getValue(v, key);
		template.setRootConceptCode(value);

		key = "sortColumn";
		value = getValue(v, key);
		template.setSortColumn(Integer.parseInt(value));

		key = "type";
		value = getValue(v, key);
		template.setType(value);

		key = "association";
		value = getValue(v, key);
		template.setAssociation(value);

		key = "level";
		value = getValue(v, key);
		template.setLevel(Integer.parseInt(value));

		List<TemplateColumn> cols = readTemplateColumns(v);
		template.setColumns(cols);
        return template;
	}

	public void dumpTemplate(Template template) {
        System.out.println(template.toString());
        List<TemplateColumn> list = template.getColumns();
        for (int i=0; i<list.size(); i++) {
			TemplateColumn col = (TemplateColumn) list.get(i);
            System.out.println("\n" + col.getColumnNumber());
            System.out.println("" + col.getLabel());
            System.out.println("" + col.getDisplay());
            System.out.println("" + col.getPropertyType());
            System.out.println("" + col.getProperty());
            System.out.println("" + col.getSource());
            System.out.println("" + col.getGroup());
            System.out.println("" + col.getSubsource());
            System.out.println("" + col.getAttr());
		}
	}

    public static void main(String[] args) {
		TemplateLoader test = new TemplateLoader();
		String templateFile = "v2_PCDC_EWS_Terminology-Task-598.template";
		System.out.println(templateFile);
		Template template = test.load(templateFile);
		test.dumpTemplate(template);
	}
}


