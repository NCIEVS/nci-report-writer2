package gov.nih.nci.evs.reportwriter.core.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.*;
import java.util.*;

import gov.nih.nci.evs.restapi.util.*;


/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2011, MSC. This software was developed in conjunction
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
 *      or MSC.
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
 *     Initial implementation ongki@nih.gov
 *
 */

public class CUIAndStatusAppender {

    static int FORMAT_TEXT = 1;
    static int FORMAT_HTML = 2;

    OWLScanner owlScanner = null;
    Vector parent_child_vec = null;
    HashMap code2UMLSCUIMap = null;
    HashMap code2PreferredTermMap = null;
    HashMap code2MalignantStatusMap = null;

    EmbeddedHierarchy eh = null;
	Vector code_vec = null;
	HashSet code_set = null;
    gov.nih.nci.evs.restapi.util.HierarchyHelper hh = null;
    String value_set_ascii_file = null;
    String owlfile = null;

    public CUIAndStatusAppender(String owlfile) {
		this.owlfile = owlfile;
		owlScanner = new OWLScanner(owlfile);
		initialize();
	}

    public CUIAndStatusAppender(OWLScanner owlScanner) {
		this.owlScanner = owlScanner;
		initialize();
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
				v.add(str);
			}
            in.close();
		} catch (Exception ex) {
            ex.printStackTrace();
		}
		return v;
	}

	public void initialize() {
		String prop_code = "P363";
		Vector w = owlScanner.extractProperties(owlScanner.get_owl_vec(), prop_code);
		code2MalignantStatusMap = new HashMap();
		for (int i=0; i<w.size(); i++) {
			String line = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(line, '|');
			code2MalignantStatusMap.put((String) u.elementAt(0), (String) u.elementAt(2));
		}

		prop_code = "P207";
		w = owlScanner.extractProperties(owlScanner.get_owl_vec(), prop_code);
		code2UMLSCUIMap = new HashMap();
		for (int i=0; i<w.size(); i++) {
			String line = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(line, '|');
			code2UMLSCUIMap.put((String) u.elementAt(0), (String) u.elementAt(2));
		}
	}

    public String appendCUIAndStatus(String inputfile) {
		int format = FORMAT_TEXT;
		if (inputfile.endsWith(".html")) {
			format = FORMAT_HTML;
		}
		Vector v = new Vector();
		Vector w = Utils.readFile(inputfile);

		if (format == FORMAT_TEXT) {
			for (int i=0; i<w.size(); i++) {
				String line = (String) w.elementAt(i);
				int n = line.lastIndexOf("(");
				String code = line.substring(n+1, line.length()-1);
                String umls_code = (String) code2UMLSCUIMap.get(code);
				String status = (String) code2MalignantStatusMap.get(code);
				//line = line + " (" + umls_code + ", " + status + ")";
				line = line + " (" + status + ")";
				v.add(line);
			}
		} else {
			for (int i=0; i<w.size(); i++) {
				String line = (String) w.elementAt(i);
				v.add(line);
				if (line.indexOf("on_node_clicked") != -1 && line.indexOf("function") == -1) {
					String t = line.substring(0, line.length()-4);
					int n = t.lastIndexOf(">");
					t = t.substring(n+1, t.length());
					String umls_code = (String) code2UMLSCUIMap.get(t);
					String status = (String) code2MalignantStatusMap.get(t);
					v.addAll(appendUMLSCodeAndNeoplasticStatus(umls_code, status));
				}
			}
		}
		int n = inputfile.lastIndexOf(".");
		String outputfile = inputfile.substring(0, n) + "_Plus" + "." + inputfile.substring(n+1, inputfile.length());
		Utils.saveToFile(outputfile, v);
		return outputfile;
	}

	public Vector appendUMLSCodeAndNeoplasticStatus(String umls_code, String status) {
		Vector v = new Vector();
		v.add("(");
		v.add("<a href=\"#\" onclick=\"on_cui_clicked('" + umls_code + "');return false;\" >" + umls_code + "</a>");
		v.add(",&nbsp;");
		v.add("<a title=" + status + ">" + status + ")</a>");
		return v;
	}

	public static void main(String[] args) {
        String owlfile = args[0];
		CUIAndStatusAppender test = new CUIAndStatusAppender(owlfile);
		String inputfile = args[1];
		String outputfile = test.appendCUIAndStatus(inputfile);
		System.out.println(outputfile + " generated.");
	}
}

