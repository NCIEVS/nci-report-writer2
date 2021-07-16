package gov.nih.nci.evs.reportwriter.core.util;

import gov.nih.nci.evs.restapi.util.*;

import com.opencsv.CSVReader;

import java.io.*;
import java.text.*;
import java.util.*;


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


public class NeoplasmCoreRelationships {
    String value_set_ascii_file = null;
    String owlfile = null;
	OWLScanner scanner = null;
	Vector code_vec = null;
	HashSet code_set = null;
	HashMap objectPropertiesCode2LabelMap = null;
	HashMap cid2PTMap = null;

	HashMap roleMap = null;


    public NeoplasmCoreRelationships(String value_set_ascii_file, String owlfile) {
		this.value_set_ascii_file = value_set_ascii_file;
		this.owlfile = owlfile;
		initialize();
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



	private void initialize() {
        scanner = new OWLScanner(owlfile);
        code_vec = get_value_set_codes(value_set_ascii_file);
        code_set = new HashSet();
        for (int i=0; i<code_vec.size(); i++) {
			String t = (String) code_vec.elementAt(i);
			code_set.add(t);
		}

        System.out.println("code_vec: " + code_vec.size());
		Vector w = scanner.extractObjectProperties(scanner.get_owl_vec());
		objectPropertiesCode2LabelMap = new HashMap();
		for (int i=0; i<w.size(); i++) {
			String t = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(t, '|');
			objectPropertiesCode2LabelMap.put((String) u.elementAt(0),(String) u.elementAt(1));
		}

        w = scanner.extractProperties(scanner.get_owl_vec(), "P108");
		cid2PTMap = new HashMap();

		for (int i=0; i<w.size(); i++) {
			String t = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(t, '|');
			cid2PTMap.put((String) u.elementAt(0),(String) u.elementAt(2));
		}
	}

	public void getRestrictions(String outputfile) {
		roleMap = new HashMap();
		Vector w = scanner.extractOWLRestrictions(scanner.get_owl_vec());
		System.out.println("w: " + w.size());
		System.out.println("code_set: " + code_set.size());

		for (int i=0; i<w.size(); i++) {
			String t = (String) w.elementAt(i);
			Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(t, '|');
			String src = (String) u.elementAt(0);
			String r = (String) u.elementAt(1);
			String target = (String) u.elementAt(2);
			if (code_set.contains(src)) {
				Vector v = new Vector();
				if (roleMap.containsKey(src)) {
					v = (Vector) roleMap.get(src);
				}
				if (!v.contains(r + "|" + target)) {
					v.add(r + "|" + target);
				}
				roleMap.put(src, v);
			}
		}
		Vector w0 = new Vector();

		Iterator it = roleMap.keySet().iterator();
		while (it.hasNext()) {
			String src = (String) it.next();
			String src_pt = (String) cid2PTMap.get(src);
			Vector v = (Vector) roleMap.get(src);
			for (int i=0; i<v.size(); i++) {
				String s = (String) v.elementAt(i);
				Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(s, '|');
				String r = (String) u.elementAt(0);
				String target = (String) u.elementAt(1);
				String target_pt = (String) cid2PTMap.get(target);

				w0.add(src_pt + "|" + src + "|" + (String) objectPropertiesCode2LabelMap.get(r) + "|" + target + "|" + target_pt);
			}
		}
		w0 = new gov.nih.nci.evs.restapi.util.SortUtils().quickSort(w0);
		saveToFile(outputfile, w0);
	}

	public void generateCSVFile(String inputfile, String csvfile) {
		//String outputfile = getOutputfileName(inputfile);
		Vector v = readFile(inputfile);
		int line_count = v.size();
		//line_count = line_count-1;
		System.out.println("Totol number of records: " + line_count);

        PrintWriter pw = null;

        Vector w = new Vector();
        HashMap hmap = new HashMap();
        String key = null;
        Vector key_vec = new Vector();

        try {
			pw = new PrintWriter(csvfile, "UTF-8");
			//	private String Constants.HEADING_STR = "\"Code\",\"Preferred Term\",\"Relationship\",\"Code\",\"Preferred Term\"";
			pw.println(Constants.HEADING_STR);
			for (int i=0; i<v.size(); i++) {
				String t = (String) v.elementAt(i);
				Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(t, '|');
			    //String direction = (String) u.elementAt(0);
			    String src_pt = (String) u.elementAt(0);
			    String src_code = (String) u.elementAt(1);
			    //String role_code = (String) u.elementAt(2);
			    String role_name = (String) u.elementAt(2);
			    String target_code = (String) u.elementAt(3);
			    String target_pt = (String) u.elementAt(4);

				StringBuffer buf = new StringBuffer();
				buf.append("\"").append(src_code).append("\"").append(",");
				buf.append("\"").append(src_pt).append("\"").append(",");
				buf.append("\"").append(role_name).append("\"").append(",");
				buf.append("\"").append(target_code).append("\"").append(",");
				buf.append("\"").append(target_pt).append("\"");
				pw.println(buf.toString());
			}
		} catch (Exception ex) {

		} finally {
			try {
				pw.close();
				System.out.println("Output file " + csvfile + " generated.");
		    } catch (Exception ex) {
				ex.printStackTrace();
			}
		}
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

	 public static void saveToFile(String outputfile, String t) {
		 Vector v = new Vector();
		 v.add(t);
		 saveToFile(outputfile, v);
	 }

	 public static void saveToFile(String outputfile, Vector v) {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputfile, "UTF-8");
			if (v != null && v.size() > 0) {
				for (int i=0; i<v.size(); i++) {
					String t = (String) v.elementAt(i);
					pw.println(t);
				}
		    }
		} catch (Exception ex) {

		} finally {
			try {
				pw.close();
				System.out.println("Output file " + outputfile + " generated.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void saveToFile(PrintWriter pw, Vector v) {
		if (v != null && v.size() > 0) {
			for (int i=0; i<v.size(); i++) {
				String t = (String) v.elementAt(i);
				pw.println(t);
			}
		}
	}
}

