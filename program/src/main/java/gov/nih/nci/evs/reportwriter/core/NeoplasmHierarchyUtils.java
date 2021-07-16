package gov.nih.nci.evs.reportwriter.core.util;

import gov.nih.nci.evs.restapi.util.*;
import java.io.*;
import java.util.*;
import java.text.*;

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

public class NeoplasmHierarchyUtils {

	static String NEOPLASM = "Neoplasm"; //Neoplasm (Code C3262)
	static String NEOPLASM_BY_SITE = "Neoplasm by Site"; //Neoplasm by Site (Code C3263)
	static String NEOPLASM_BY_MORPHOLOGY = "Neoplasm by Morphology"; //Neoplasm by Morphology (Code C4741)

	static String NEOPLASM_CODE = "C3262"; //Neoplasm (Code C3262)
	static String NEOPLASM_BY_SITE_CODE = "C3263"; //Neoplasm by Site (Code C3263)
	static String NEOPLASM_BY_MORPHOLOGY_CODE = "C4741"; //Neoplasm by Morphology (Code C4741)

    static Vector NEOPLASM_ROOTS = null;
    static Vector NEOPLASM_BY_SITE_ROOTS = null;
    static Vector NEOPLASM_BY_MORPHOLOGY_ROOTS = null;

    static HashMap NEOPLASM_ROOT_CODE_MAP = null;

    static String NEOPLASM_CORE_TERMINOLOGY_CODE = "C126659";

    static String MALIGNANT = "Malignant";
    static String BENIGN = "Benign";
    static String UNDETERMINED = "Undetermined";

    static String NEOPLASM_CORE_HIERARCHY_FILE = "Neoplasm_Core_Hierarchy.txt";
    static String NEOPLASM_CORE_HIERARCHY_PLUS_FILE = "Neoplasm_Core_Hierarchy_Plus.txt";
    static String NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE = "Neoplasm_Core_Hierarchy_By_Neoplastic_Status.txt";
    static String NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_PLUS_FILE = "Neoplasm_Core_Hierarchy_By_Neoplastic_Status_Plus.txt";


    OWLScanner owlScanner = null;
    Vector parent_child_vec = null;
    HashMap code2MalignantStatusMap = null;
    HashMap code2PreferredTermMap = null;
    EmbeddedHierarchy eh = null;
	Vector code_vec = null;
	HashSet code_set = null;
    gov.nih.nci.evs.restapi.util.HierarchyHelper hh = null;
    String value_set_ascii_file = null;
    String owlfile = null;

    public NeoplasmHierarchyUtils(String owlfile) {
		this.owlfile = owlfile;
		owlScanner = new OWLScanner(owlfile);
		initialize();
	}

    public NeoplasmHierarchyUtils(String value_set_ascii_file, String owlfile) {
		this.value_set_ascii_file = value_set_ascii_file;
		this.owlfile = owlfile;
		owlScanner = new OWLScanner(owlfile);
		initialize();
	}

	public HashSet restrictToMalignantStatus(Vector code_vec, String status) {
		HashSet hset = new HashSet();
		for (int i=0; i<code_vec.size(); i++) {
			String code = (String) code_vec.elementAt(i);
			String st = (String) code2MalignantStatusMap.get(code);
			if (status.compareTo(st) == 0) {
				hset.add(code);
			}
		}
		return hset;
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

	public Vector get_value_set_codes(String value_set_ascii_file) {
		Vector v = readFile(value_set_ascii_file);
		code_set = new HashSet();
		code_vec = new Vector();
		for (int i=1; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(t, '\t');
			String code = (String) u.elementAt(0);
			if (!code_set.contains(code)) {
				code_vec.add(code);
				code_set.add(code);
			}
		}
		return code_vec;
	}

	public void initialize() {
		parent_child_vec = owlScanner.extractHierarchicalRelationships();
		System.out.println("parent_child_vec: " + parent_child_vec.size());
		hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(parent_child_vec);

		code_vec = get_value_set_codes(value_set_ascii_file);
		System.out.println("code_vec: " + code_vec.size());

		eh = new EmbeddedHierarchy(parent_child_vec);

		String prop_code = "P363";
		Vector w = owlScanner.extractProperties(owlScanner.get_owl_vec(), prop_code);
		code2MalignantStatusMap = new HashMap();
		for (int i=0; i<w.size(); i++) {
			String line = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(line, '|');
			code2MalignantStatusMap.put((String) u.elementAt(0), (String) u.elementAt(2));
		}
		prop_code = "P108";
		w = owlScanner.extractProperties(owlScanner.get_owl_vec(), prop_code);
		code2PreferredTermMap = new HashMap();
		for (int i=0; i<w.size(); i++) {
			String line = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(line, '|');
			code2PreferredTermMap.put((String) u.elementAt(0), (String) u.elementAt(2));
		}
	}

	public Vector exportTree(Vector v, String code, int level) {
		return hh.exportTree(v, code, level);
	}

	public Vector exportTree(String code) {
		return hh.exportTree(new Vector(), code, 0);
	}

    public Vector appendNeoplasticStatus(Vector v) {
		Vector w = new Vector();
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			String line_trim = line;
			line_trim = line_trim.trim();
			if (line_trim.length() > 0) {
				//Epithelial Neoplasm (C3709)
				int n = line.lastIndexOf("(");
				String code = line.substring(n+1, line.length()-1);
				String status = (String) code2MalignantStatusMap.get(code);
				//System.out.println(code + " " + status);
				if (status == null) {
					System.out.println(line);
					System.out.println("WARNING: " + code + " malignant status not known???");
					w.add(line);
				} else {
					String status_abbrev = "" + status.charAt(0);
					w.add(line + " (" + status_abbrev + ")");
				}
			}
		}
        return w;
	}

    public Vector generateEmbeddedHierarchy(String rootCode, HashSet nodeSet, boolean trim) {
		return eh.generateEmbeddedHierarchy(rootCode, nodeSet, trim);
	}

    public Vector generateEmbeddedHierarchy() {
		String rootCode = NEOPLASM_CODE;
		HashSet nodeSet = code_set;
		boolean trim = false;
		return eh.generateEmbeddedHierarchy(rootCode, nodeSet, trim);
	}

    public static void main0(String[] args) {
		String owlfile = args[0];
        NeoplasmHierarchyUtils util = new NeoplasmHierarchyUtils(owlfile);
        String textfile = "Neoplasm_Core_Hierarchy.txt";
        Vector v = util.exportTree(NEOPLASM_BY_MORPHOLOGY_CODE);
        Vector w = util.exportTree(NEOPLASM_BY_SITE_CODE);
        v.addAll(w);
        Utils.saveToFile(textfile, v);

        w = util.appendNeoplasticStatus(v);
        textfile = "Neoplasm_Core_Hierarchy_plus.txt";
        Utils.saveToFile(textfile, w);
	}

	public Vector get_code_vec() {
		return this.code_vec;
	}

	public HashSet get_code_set() {
		return this.code_set;
	}

	public Vector addRoot(Vector v, String rootCode, String rootLabel) {
		Vector w = new Vector();
		w.add(rootLabel + " (" + rootCode + ")");
		for (int i=0; i<v.size(); i++) {
			w.add("\t" + (String) v.elementAt(i));
		}
		return w;
	}

    public Vector getASCIITree(String code, HashSet hset) {
        Vector w = generateEmbeddedHierarchy(code, hset, false);
        gov.nih.nci.evs.restapi.util.HierarchyHelper hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        Vector v = hh.exportTree();
        v = addRoot(v, code, (String) code2PreferredTermMap.get(code));
        return v;
	}

    public Vector getASCIITree(Vector codes, HashSet hset) {
        Vector w0 = new Vector();
        for (int i=0; i<codes.size(); i++) {
			String code = (String) codes.elementAt(i);
			Vector w = generateEmbeddedHierarchy(code, hset, false);
			gov.nih.nci.evs.restapi.util.HierarchyHelper hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
			Vector v = hh.exportTree();
			v = addRoot(v, code, (String) code2PreferredTermMap.get(code));
			w0.addAll(v);
		}
        return w0;
	}

}
