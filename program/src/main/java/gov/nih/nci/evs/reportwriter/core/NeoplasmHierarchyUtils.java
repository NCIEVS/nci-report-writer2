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

/*
    static {
		serviceUrl = ServiceTestCase.serviceUrl;
		NEOPLASM_ROOT_CODE_MAP = new HashMap();
		String codingSchemeName = "NCI_Thesaurus";
        LexBIGService lbSvc = RemoteServerUtil.createLexBIGService(serviceUrl);
        String vers = null;
        String ns = codingSchemeName;
        boolean use_ns = true;
        ConceptDetails cd = new ConceptDetails(lbSvc);
        Entity entity = cd.getConceptByCode(codingSchemeName, vers, NEOPLASM_BY_SITE_CODE, ns, use_ns);
        System.out.println(entity.getEntityDescription().getContent() + " (" + entity.getEntityCode() + ")");

        entity = cd.getConceptByCode(codingSchemeName, vers, NEOPLASM_BY_MORPHOLOGY_CODE, ns, use_ns);
        System.out.println(entity.getEntityDescription().getContent() + " (" + entity.getEntityCode() + ")");


        TreeUtils treeUtils = new TreeUtils(lbSvc);
        NEOPLASM_ROOTS = new Vector();
        NEOPLASM_BY_SITE_ROOTS = new Vector();
        NEOPLASM_BY_MORPHOLOGY_ROOTS = new Vector();

        String code = NEOPLASM_BY_SITE_CODE;
        HashMap hmap = treeUtils.getSubconcepts(codingSchemeName, vers, code, ns);
		if (hmap != null) {
			TreeItem ti = (TreeItem) hmap.get(code);
			if (ti != null) {
				for (String association : ti._assocToChildMap.keySet()) {
					List<TreeItem> children =
						ti._assocToChildMap.get(association);
					for (TreeItem childItem : children) {
						String t = childItem._text + "|" + childItem._code;
						NEOPLASM_BY_SITE_ROOTS.add(t);
						NEOPLASM_ROOTS.add(NEOPLASM_BY_SITE + "|R_1|" + t);
					}
				}
			}
		}
		NEOPLASM_BY_SITE_ROOTS = SortUtils.quickSort(NEOPLASM_BY_SITE_ROOTS);
		for (int i=0; i<NEOPLASM_BY_SITE_ROOTS.size(); i++) {
			int j = i+1;
			String t = (String) NEOPLASM_BY_SITE_ROOTS.elementAt(i);
			//System.out.println("(" + j + ") " + t);
		}

        System.out.println("\n");
        code = NEOPLASM_BY_MORPHOLOGY_CODE;
        hmap = treeUtils.getSubconcepts(codingSchemeName, vers, code, ns);
		if (hmap != null) {
			TreeItem ti = (TreeItem) hmap.get(code);
			if (ti != null) {
				for (String association : ti._assocToChildMap.keySet()) {
					List<TreeItem> children =
						ti._assocToChildMap.get(association);
					for (TreeItem childItem : children) {
						String t = childItem._text + "|" + childItem._code;
						NEOPLASM_BY_MORPHOLOGY_ROOTS.add(t);
						NEOPLASM_ROOTS.add(NEOPLASM_BY_MORPHOLOGY + "|R_2|" + t);
					}
				}
			}
		}
		NEOPLASM_BY_MORPHOLOGY_ROOTS = SortUtils.quickSort(NEOPLASM_BY_MORPHOLOGY_ROOTS);
		for (int i=0; i<NEOPLASM_BY_MORPHOLOGY_ROOTS.size(); i++) {
			int j = i+1;
			String t = (String) NEOPLASM_BY_MORPHOLOGY_ROOTS.elementAt(i);
			//System.out.println("(" + j + ") " + t);
		}

		NEOPLASM_ROOT_CODE_MAP.put("R_1", NEOPLASM_BY_SITE_CODE);
		NEOPLASM_ROOT_CODE_MAP.put("R_2", NEOPLASM_BY_MORPHOLOGY_CODE);

		System.out.println("\n");
		//NEOPLASM_ROOTS = SortUtils.quickSort(NEOPLASM_ROOTS);
		for (int i=0; i<NEOPLASM_ROOTS.size(); i++) {
			int j = i+1;
			String t = (String) NEOPLASM_ROOTS.elementAt(i);
			System.out.println("(" + j + ") " + t);
		}


	}

	public NeoplasmHierarchyUtils(LexBIGService lbSvc) {
		this.lbSvc = lbSvc;
	}

	public void setLexBIGService(LexBIGService lbSvc) {
		this.lbSvc = lbSvc;
	}
*/
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

/*
Name: "Neoplasm_Core_Terminology"
rootConceptCode: "C126659"
*/

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
			/*
			if (i < codes.size()-1) {
				w0.add("\n");
			}
			*/
		}
        return w0;
	}
/*
    public void testTree(String focusCode) {
		System.out.println("parent_child_vec: " + parent_child_vec.size());
		for (int i=0; i<5; i++) {
			String t = (String) parent_child_vec.elementAt(i);
			System.out.println(t);
		}
		TreeNavigationHelper treeNavigationHelper = new TreeNavigationHelper(focusCode, parent_child_vec);
		String outputfile= "test.html";
		treeNavigationHelper.generate(outputfile);
	}
*/
    public static void main(String[] args) {
		String value_set_ascii_file = args[0];
		String owlfile = args[1];
        NeoplasmHierarchyUtils util = new NeoplasmHierarchyUtils(value_set_ascii_file, owlfile);
/*
        Vector codes = new Vector();
        codes.add("C4741");
        codes.add("C3263");
        Vector v = util.getASCIITree(codes, util.get_code_set());
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_FILE, v);
        v = util.appendNeoplasticStatus(v);
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_PLUS_FILE, v);

        codes = new Vector();
        codes.add("C3677");
        codes.add("C9305");
        codes.add("C3646");
        v = util.getASCIITree(codes, util.get_code_set());
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE, v);
        v = util.appendNeoplasticStatus(v);
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_PLUS_FILE, v);
*/

        //util.testTree("C3677");


	}

    public static void main2(String[] args) {
		String value_set_ascii_file = args[0];
		String owlfile = args[1];
        NeoplasmHierarchyUtils util = new NeoplasmHierarchyUtils(value_set_ascii_file, owlfile);

        /*
            static String MALIGNANT = "Malignant";
		    static String BENIGN = "Benign";
		    static String UNDETERMINED = "Undetermined";
Hashset restrictToMalignantStatus(Vector code_vec, String status)
        */

/*
        String root = NEOPLASM_CODE;
        Vector w0 = new Vector();
        //w0.add("========================= MALIGNANT ============================");
//Neoplasm by Morphology (Code C4741)

        Vector w = util.generateEmbeddedHierarchy(root, util.restrictToMalignantStatus(util.get_code_vec(), MALIGNANT), false );
        gov.nih.nci.evs.restapi.util.HierarchyHelper hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        Vector v = hh.exportTree();
        v = util.addRoot(v, String rootCode, String rootLabel)
        w0.addAll(v);

        w0.add("========================= BENIGN ============================");
        w = util.generateEmbeddedHierarchy(root, util.restrictToMalignantStatus(util.get_code_vec(), BENIGN), false );
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        w0.addAll(v);

        w0.add("========================= UNDETERMINED ============================");
        w = util.generateEmbeddedHierarchy(root, util.restrictToMalignantStatus(util.get_code_vec(), UNDETERMINED), false );
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        w0.addAll(v);
*/

        Vector w0 = new Vector();
        Vector w = util.generateEmbeddedHierarchy("C4741", util.get_code_set(), false);
        gov.nih.nci.evs.restapi.util.HierarchyHelper hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        Vector v = hh.exportTree();
        v = util.addRoot(v, "C4741", "Neoplasm by Morphology");

        w0.addAll(v);
        w0.add("\n");
        w = util.generateEmbeddedHierarchy("C3263", util.get_code_set(), false);
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        v = util.addRoot(v, "C3263", "Neoplasm by Site");
        w0.addAll(v);
        Utils.saveToFile("Neoplasm_Core_Hierarchy.txt", w0);
        w0 = util.appendNeoplasticStatus(w0);
        Utils.saveToFile("Neoplasm_Core_Hierarchy_plus.txt", w0);

        //////////////////////////////////////////////////////////////////////////////////////

/*
Benign Neoplasm (Code C3677)
Malignant Neoplasm (Code C9305)
//Neoplasm of Intermediate or Uncertain Behavior, or Comprising Mixed Subtypes (R_3)
Neoplasm of Uncertain Malignant Potential (Code C3646)
*/

        w0 = new Vector();
        w = util.generateEmbeddedHierarchy("C3677", util.get_code_set(), false);
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        v = util.addRoot(v, "C3677", "Benign Neoplasm");
        w0.addAll(v);
        w0.add("\n");
        w = util.generateEmbeddedHierarchy("C9305", util.get_code_set(), false);
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        v = util.addRoot(v, "C9305", "Malignant Neoplasm");
        w0.addAll(v);
        w0.add("\n");
        w = util.generateEmbeddedHierarchy("C3646", util.get_code_set(), false);
        hh = new gov.nih.nci.evs.restapi.util.HierarchyHelper(w);
        v = hh.exportTree();
        v = util.addRoot(v, "C3646", "Neoplasm of Uncertain Malignant Potential");
        w0.addAll(v);
        Utils.saveToFile("Neoplasm_Core_Hierarchy_By_Neoplastic_Status.txt", w0);
        w0 = util.appendNeoplasticStatus(w0);
        Utils.saveToFile("Neoplasm_Core_Hierarchy_By_Neoplastic_Status_plus.txt", w0);
	}

}

/*
Disease, Disorder or Finding
	Disease or Disorder
		Neoplasm
			Neoplasm by Special Category
				Embryonal Neoplasm
					Intraocular Medulloepithelioma
						Benign Intraocular Medulloepithelioma (Code C66807)


Disease, Disorder or Finding
	Disease or Disorder
		Neoplasm
			Neoplasm by Special Category
				Embryonal Neoplasm
					Ewing Sarcoma/Peripheral Primitive Neuroectodermal Tumor (Code C27291)



Disease, Disorder or Finding
	Disease or Disorder
		Neoplasm
			Neoplasm by Special Category
				Embryonal Neoplasm
					Rhabdoid Tumor (Code C3808)



Neoplasm by Special Category (Code C7062)
        Embryonal Neoplasm (Code C3264)


Concepts in Tree but not in value set:
	Endocrine Neoplasm (C3010)
	Thoracic Neoplasm (C3406)
	Cardiovascular Neoplasm (C4784)
	Retinal Cell Neoplasm (C7061)
	Neoplastic Polyp (C7068)
	Giant Cell Neoplasm (C7069)
	Mesenchymal Cell Neoplasm (C7059)
	Urinary System Neoplasm (C3431)
	Reproductive System Neoplasm (C3674)
	Hematopoietic and Lymphoid System Neoplasm (C35813)
	Melanocytic Neoplasm (C7058)
	Neoplasm of Uncertain Histogenesis (C6974)
	Meningothelial Cell Neoplasm (C6971)
	Connective and Soft Tissue Neoplasm (C3810)
	Mixed Neoplasm (C6930)
	Respiratory Tract Neoplasm (C3355)
	Skin Neoplasm (C3372)
	Neuroepithelial, Perineurial, and Schwann Cell Neoplasm (C35562)
	Neoplasm by Morphology (R_2)
	Neoplasm by Site (R_1)
	Eye Neoplasm (C3030)
	Breast Neoplasm (C2910)
	Digestive System Neoplasm (C3052)
	Nervous System Neoplasm (C3268)
	Mesothelial Neoplasm (C3786)
	Head and Neck Neoplasm (C3077)
	Peritoneal and Retroperitoneal Neoplasms (C7337)
	Hematopoietic and Lymphoid Cell Neoplasm (C27134)
	Epithelial Neoplasm (C3709)


Neoplasm by Morphology (Code C4741)
Neoplasm by Site (Code C3263)


Neoplasm by Site|R_1|Breast Neoplasm|C2910
Neoplasm by Site|R_1|Cardiovascular Neoplasm|C4784
Neoplasm by Site|R_1|Connective and Soft Tissue Neoplasm|C3810
Neoplasm by Site|R_1|Digestive System Neoplasm|C3052
Neoplasm by Site|R_1|Endocrine Neoplasm|C3010
Neoplasm by Site|R_1|Eye Neoplasm|C3030
Neoplasm by Site|R_1|Head and Neck Neoplasm|C3077
Neoplasm by Site|R_1|Hematopoietic and Lymphoid System Neoplasm|C35813
Neoplasm by Site|R_1|Nervous System Neoplasm|C3268
Neoplasm by Site|R_1|Peritoneal and Retroperitoneal Neoplasms|C7337
Neoplasm by Site|R_1|Reproductive System Neoplasm|C3674
Neoplasm by Site|R_1|Respiratory Tract Neoplasm|C3355
Neoplasm by Site|R_1|Skin Neoplasm|C3372
Neoplasm by Site|R_1|Thoracic Neoplasm|C3406
Neoplasm by Site|R_1|Urinary System Neoplasm|C3431
Neoplasm by Morphology|R_2|Epithelial Neoplasm|C3709
Neoplasm by Morphology|R_2|Germ Cell Tumor|C3708
Neoplasm by Morphology|R_2|Giant Cell Neoplasm|C7069
Neoplasm by Morphology|R_2|Hematopoietic and Lymphoid Cell Neoplasm|C27134
Neoplasm by Morphology|R_2|Melanocytic Neoplasm|C7058
Neoplasm by Morphology|R_2|Meningothelial Cell Neoplasm|C6971
Neoplasm by Morphology|R_2|Mesenchymal Cell Neoplasm|C7059
Neoplasm by Morphology|R_2|Mesothelial Neoplasm|C3786
Neoplasm by Morphology|R_2|Mixed Neoplasm|C6930
Neoplasm by Morphology|R_2|Neoplasm of Uncertain Histogenesis|C6974
Neoplasm by Morphology|R_2|Neoplastic Polyp|C7068
Neoplasm by Morphology|R_2|Neuroepithelial, Perineurial, and Schwann Cell Neoplasm|C35562
Neoplasm by Morphology|R_2|Retinal Cell Neoplasm|C7061
Neoplasm by Morphology|R_2|Trophoblastic Tumor|C3422



Neoplasm by Site (Code C3263)
 Breast Neoplasm
 Cardiovascular Neoplasm
 Connective and Soft Tissue Neoplasm
 Digestive System Neoplasm
 Endocrine Neoplasm
 Eye Neoplasm
 Head and Neck Neoplasm
 Hematopoietic and Lymphoid System Neoplasm
 Nervous System Neoplasm
 Peritoneal and Retroperitoneal Neoplasms
 Reproductive System Neoplasm
 Respiratory Tract Neoplasm
 Skin Neoplasm
 Thoracic Neoplasm
 Urinary System Neoplasm

Neoplasm by Morphology (Code C4741)
 Epithelial Neoplasm
 Germ Cell Tumor
 Giant Cell Neoplasm
 Hematopoietic and Lymphoid Cell Neoplasm
 Melanocytic Neoplasm
 Meningothelial Cell Neoplasm
 Mesenchymal Cell Neoplasm
 Mesothelial Neoplasm
 Mixed Neoplasm
 Neoplasm of Uncertain Histogenesis
 Neoplastic Polyp
 Neuroepithelial, Perineurial, and Schwann Cell Neoplasm
 Retinal Cell Neoplasm
 Trophoblastic Tumor

*/

//Neoplastic_Status  P363
