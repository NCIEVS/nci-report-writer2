package gov.nih.nci.evs.reportwriter.core.util;
import gov.nih.nci.evs.restapi.util.*;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class NCImASCII2HTMLTreeConverter {
    String asciifile = null;
    TreeItem root = null;
    HashMap localNameMap = null;
    HashMap localNmMap = null;

	public NCImASCII2HTMLTreeConverter() {

	}

	public NCImASCII2HTMLTreeConverter(String asciifile) {
        this.asciifile = asciifile;
		ASCII2HTMLTreeConverter generator = new ASCII2HTMLTreeConverter();
		root = generator.createTreeItem(asciifile);
		Vector v = Utils.readFile("localNameMap.txt");
		localNameMap = new HashMap();
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
		    Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(line, '|');
		    localNameMap.put((String) u.elementAt(0),(String) u.elementAt(1));
		    System.out.println((String) u.elementAt(0) + " --> " + (String) u.elementAt(1));
		}
	}

	public static String encode(String t) {
		t = t.replace("<", "&lt");
		t = t.replace(">", "&gt");
		return t;
	}

	public static TreeItem searchTree(TreeItem ti, String text) {
		if (ti._text.compareTo(text) == 0) return ti;
		TreeItem node = null;
        for (String association : ti._assocToChildMap.keySet()) {
            List<TreeItem> children = ti._assocToChildMap.get(association);
            if (children != null) {
				for (int i=0; i<children.size(); i++) {
					TreeItem childItem = (TreeItem) children.get(i);
					node = searchTree(childItem, text);
					if (node != null) {
						return node;
					}
				}
			}
        }
        return null;
	}

	public void generate(PrintWriter out) {
		writeHeader(out);

		writeTree(out, root);
		writeFooter(out);
	}


	public void writeHeader(PrintWriter out) {
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
		out.println("<html xmlns:c=\"http://java.sun.com/jsp/jstl/core\">");
		out.println("<head>");
		out.println("<title>Core Mappings: NCIm Terms</title>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<script type=\"text/javascript\">");
		out.println("");
		out.println("	function show_hide(div_id) {");
		out.println("		var img_id = \"IMG_\" + div_id.substring(4, div_id.length);");
		out.println("		var img_obj = document.getElementById(img_id);");
		out.println("		if (img_obj.getAttribute(\"src\").indexOf(\"minus\") != -1) {");
		out.println("			document.getElementById(div_id).style.display = \"none\";");
		out.println("		} else if (img_obj.getAttribute(\"src\").indexOf(\"plus\") != -1) {");
		out.println("			document.getElementById(div_id).style.display = \"block\";");
		out.println("		}");
		out.println("		changeImage(img_id);");
		out.println("	}");
		out.println("");
		out.println("	function changeImage(img_id) {");
		out.println("		var img_obj = document.getElementById(img_id);");
		out.println("		if (img_obj.getAttribute(\"src\").indexOf(\"minus\") != -1) {");
		out.println("			var s = img_obj.getAttribute(\"src\");");
		out.println("			s = s.replace(\"minus\", \"plus\");");
		out.println("			img_obj.setAttribute(\"src\", s);");
		out.println("		} else if (img_obj.getAttribute(\"src\").indexOf(\"plus\") != -1) {");
		out.println("			var s = img_obj.getAttribute(\"src\");");
		out.println("			s = s.replace(\"plus\", \"minus\");");
		out.println("			img_obj.setAttribute(\"src\", s);");
		out.println("		}");
		out.println("	}");
		out.println("");
		out.println("	function show(div_id) {");
		out.println("		var img_id = \"IMG_\" + div_id.substring(4, div_id.length);");
		out.println("		var img_obj = document.getElementById(img_id);");
		out.println("		if (img_obj.getAttribute(\"src\").indexOf(\"plus\") != -1) {");
		out.println("			document.getElementById(div_id).style.display = \"block\";");
		out.println("			changeImage(img_id);");
		out.println("		}");
		out.println("	}");
		out.println("");
		out.println("	function hide(div_id) {");
		out.println("		var img_id = \"IMG_\" + div_id.substring(4, div_id.length);");
		out.println("		var img_obj = document.getElementById(img_id);");
		out.println("		if (img_obj.getAttribute(\"src\").indexOf(\"minus\") != -1) {");
		out.println("			document.getElementById(div_id).style.display = \"none\";");
		out.println("			changeImage(img_id);");
		out.println("		}");
		out.println("	}");
		out.println("");
		out.println("	function expand_node(prefix) {");
		out.println("		var div = \"DIV_\";");
		out.println("		var child_cnt = 1;");
		out.println("		child_id = prefix.concat(child_cnt.toString());");
		out.println("		while (document.getElementById(div.concat(child_id)) != null) {");
		out.println("			show(div.concat(child_id));");
		out.println("			expand_node(child_id.concat(\"_\"));");
		out.println("			child_cnt++;");
		out.println("			child_id = prefix.concat(child_cnt.toString());");
		out.println("		}");
		out.println("	}");
		out.println("");
		out.println("	function getLevel(div) {");
		out.println("		var n = div.length;");
		out.println("		var c;");
		out.println("		var knt = 0;");
		out.println("		for (var i=0; i<n; i++) {");
		out.println("		   c = div.charAt(i);");
		out.println("		   if (c == \"_\") {");
		out.println("			knt++;");
		out.println("		   }");
		out.println("		}");
		out.println("		return knt-1;");
		out.println("	}");
		out.println("");
		out.println("");
		out.println("	function collapse_all() {");
		out.println("		var divTags = document.getElementsByTagName('div');");
		out.println("		for (var i=0;i<divTags.length;i++) {");
		out.println("			if (divTags[i].id.indexOf(\"DIV_N_\") == 0) {");
		out.println("				hide(divTags[i].id);");
		out.println("			}");
		out.println("		}");
		out.println("	}");
		out.println("	function expand_all() {");
		out.println("		var divTags = document.getElementsByTagName('div');");
		out.println("		for (var i=0;i<divTags.length;i++) {");
		out.println("			if (divTags[i].id.indexOf(\"DIV_N_\") == 0) {");
		out.println("				show(divTags[i].id);");
		out.println("			}");
		out.println("		}");
		out.println("	}");
		out.println("");
		out.println("    function expand(level) {");
		out.println("        expand_all();");
		out.println("        collapse_all();");
		out.println("        var divTags = document.getElementsByTagName('div');");
		out.println("		   for (var i=0;i<divTags.length;i++) {");
		out.println("		       var div_id = divTags[i].id;");
		out.println("		       if (divTags[i].id.indexOf(\"DIV_N_\") >= 0) {");
		out.println("				   if (divTags[i].id.indexOf(\"DIV_N_\") == 0) {");
		out.println("					   if (getLevel(div_id) <= level) {");
		out.println("						   document.getElementById(div_id).style.display = \"block\";");
		out.println("						   var img_id = \"IMG_\" + div_id.substring(4, div_id.length);");
		out.println("						   changeImage(img_id);");
		out.println("					   }");
		out.println("				   }");
		out.println("		       }");
		out.println("		   }");
		out.println("    }");
		out.println("");
		out.println("	function on_node_clicked(code) {");
		out.println("	    var url = \"https://nciterms.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI%20Thesaurus&code=\" + code;");
		out.println("		window.open(url, '_blank', 'top=100, left=100, height=740, width=780, status=no, menubar=yes, resizable=yes, scrollbars=yes, toolbar=yes, location=no, directories=no');");
		out.println("	}");
		out.println("");
		out.println("	function expand_tree() {");
		out.println("	    var level = document.getElementById('level').value;");
		out.println("	    expand(level);");
		out.println("	}");
		out.println("");
		out.println("");
		out.println("	function on_cui_clicked(code) {");
		out.println("	    var url = \"https://ncim.nci.nih.gov/ncimbrowser/ConceptReport.jsp?dictionary=NCI%20Metathesaurus&code=\" + code;");
		out.println("		window.open(url, '_blank', 'top=100, left=100, height=740, width=780, status=no, menubar=yes, resizable=yes, scrollbars=yes, toolbar=yes, location=no, directories=no');");
		out.println("	}");
		out.println("");
		out.println("	function on_source_code_clicked(source, code) {");
		out.println("	    var url = \"https://nciterms.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=\" + source + \"&code=\" + code;");
		out.println("		window.open(url, '_blank', 'top=100, left=100, height=740, width=780, status=no, menubar=yes, resizable=yes, scrollbars=yes, toolbar=yes, location=no, directories=no');");
		out.println("	}");
		out.println("");
		out.println("	function on_vocabulary_home_clicked(source) {");
		out.println("	    var url = \"https://nciterms.nci.nih.gov/ncitbrowser/pages/vocabulary.jsf?dictionary=\" + source;");
		out.println("		window.open(url, '_blank', 'top=100, left=100, height=740, width=780, status=no, menubar=yes, resizable=yes, scrollbars=yes, toolbar=yes, location=no, directories=no');");
		out.println("	}");
		out.println("</script>");
		out.println("</head>");
		out.println("<body onload=\"collapse_all();\">");
		out.println("<center>");
		out.println("<h1>NCIt Neoplasm Core Mappings to NCIm Source Terms</h1>");
		out.println("<p>");
		out.println("<h2>NCIt Version: Core (2017-02-07)</h2>");
		out.println("</p>");
		out.println("</center>");
	}


	public void writeContent(PrintWriter out, HashMap localNmMap) {
		out.println("<div>");
		out.println("<h2>Includes These EVS Standalone Sources</h2>");
		out.println("<ul>");

		Vector keys = new Vector();
		Iterator it = localNmMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			keys.add(key);
		}
		keys = new gov.nih.nci.evs.restapi.util.SortUtils().quickSort(keys);
		for (int i=0; i<keys.size(); i++) {
			String key = (String) keys.elementAt(i);
			String value = (String) localNmMap.get(key);
			out.println("<li><a href=\"#\" onclick=\"on_vocabulary_home_clicked('" + key + "');return false;\" >" + key + " (" + value + ")" + "</a></li>");
		}

		out.println("</ul>");
		out.println("<p>Other sources shown are only available in EVS as part of NCIm.");
		out.println("<br>NCIt Core concepts with no mappings do not expand.</p>");
		out.println("</div>");
		out.println("<div id=\"expandcontractdiv\">");
		out.println("");
		out.println("<a href=\"#\" onclick=\"expand_all();\" tabindex=\"1\" >Expand all</a>");
		out.println("&nbsp;");
		out.println("Expand&nbsp;");
		out.println("<select id=\"level\" onchange=\"expand_tree()\">");
		out.println("    <option value=\"0\" selected>0</option>");
		out.println("    <option value=\"1\">1</option>");
		out.println("    <option value=\"2\">2</option>");
		out.println("    <option value=\"3\">3</option>");
		out.println("    <option value=\"4\">4</option>");
		out.println("    <option value=\"5\">5</option>");
		out.println("</select>");
		out.println("&nbsp;");
		out.println("Levels");
		out.println("&nbsp;");
		out.println("<a href=\"#\" onclick=\"collapse_all();\" tabindex=\"2\">Collapse all</a>");
		out.println("</div>");
	}

	//public TreeItem searchNode(TreeItem root, String code) {
	//	return searchTree(root, code);
	//}

    public void writeNode(PrintWriter out, TreeItem ti, int j) {
		String code = ti._code;
		String text = ti._text;
		text = encode(text);

	  boolean expandable = ti._expandable;

      out.println("<li>");
      if (!expandable) {
		  out.println("<img src=\"dot.gif\" id=\"IMG_N_1_" + j + "\" alt=\"show_hide\" onclick=\"show_hide('DIV_N_1_" + j + "');\" tabindex=\"2\">	" + text + "&nbsp;<a href=\"#\" onclick=\"on_node_clicked('" + code + "');return false;\" tabindex=\"3\">" + code + "</a>");
	  } else {
          out.println("<img src=\"minus.gif\" id=\"IMG_N_1_" + j + "\" alt=\"show_hide\" onclick=\"show_hide('DIV_N_1_" + j + "');\" tabindex=\"2\">	" + text + "&nbsp;<a href=\"#\" onclick=\"on_node_clicked('" + code + "');return false;\" tabindex=\"3\">" + code + "</a>");
          out.println("<div id=\"DIV_N_1_" + j + "\">");
          out.println("<ul>");
		for (String association : ti._assocToChildMap.keySet()) {
			List<TreeItem> children = ti._assocToChildMap.get(association);
			if (children != null) {
				for (int i=0; i<children.size(); i++) {
					TreeItem childItem = (TreeItem) children.get(i);
					int k = i+1;

		String parameters = childItem._code;
		Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(parameters, ':');
		String src = (String) u.elementAt(0);
		String data = (String) u.elementAt(1);
		u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(parameters, ',');
		String term_code = (String) u.elementAt(0);
		String term_type = (String) u.elementAt(1);
		term_type = term_type.trim();
		String cui = (String) u.elementAt(2);
		cui = cui.trim();
		text = childItem._text;
		text = encode(text);
      out.println("<li>");
      if (localNmMap.containsKey(src)) {
          out.println("<img src=\"dot.gif\" id=\"IMG_N_1_" + j + "_" + k + "\" alt=\"show_hide\" onclick=\"show_hide('DIV_N_1_" + j + "_" + k + "');\" >		" + text + "&nbsp;(<a href=\"#\" onclick=\"on_source_code_clicked('" + src + "', '" + cui + "');return false;\" >" + src + ":" + term_code + "</a>, " + term_type + ", <a href=\"#\" onclick=\"on_cui_clicked('" + cui + "');return false;\" >" + cui + "</a>)");
      } else {
          out.println("<img src=\"dot.gif\" id=\"IMG_N_1_" + j + "_" + k + "\" alt=\"show_hide\" onclick=\"show_hide('DIV_N_1_" + j + "_" + k + "');\" >		" + text + "&nbsp;(" + term_code + ", " + term_type + ", <a href=\"#\" onclick=\"on_cui_clicked('" + cui + "');return false;\" >" + cui + "</a>)");
	  }

      out.println("</li>");

				}
			}
		}
		   out.println("</ul>");
           out.println("</div");

        out.println("</li>");
      }
	}

    public Vector getSources(TreeItem ti) {
		Vector v = new Vector();
		for (String association : ti._assocToChildMap.keySet()) {
			List<TreeItem> children = ti._assocToChildMap.get(association);
			for (String asso : ti._assocToChildMap.keySet()) {
				List<TreeItem> childitems = ti._assocToChildMap.get(asso);
				if (childitems != null) {
					for (int i=0; i<childitems.size(); i++) {
						TreeItem childItem = (TreeItem) childitems.get(i);

						for (String asso2 : childItem._assocToChildMap.keySet()) {
							List<TreeItem> childitems2 = childItem._assocToChildMap.get(asso2);
							if (childitems2 != null) {
								for (int i2=0; i2<childitems2.size(); i2++) {
									TreeItem childItem2 = (TreeItem) childitems2.get(i2);
									String parameters = childItem2._code;
									//System.out.println(parameters);
									Vector u = gov.nih.nci.evs.restapi.util.StringUtils.parseData(parameters, ':');
									String src = (String) u.elementAt(0);
									v.add(src);
								}
							}
						}
					}
				}
			}
		}
		return v;
	}

    public void writeTree(PrintWriter out, TreeItem root) {
        TreeItem ti = searchTree(root, "NCIt Neoplasm Core");
        Vector v = getSources(ti);
        localNmMap = new HashMap();
        for (int i=0; i<v.size(); i++) {
			String src = (String) v.elementAt(i);
			String supportedSrc = (String) localNameMap.get(src);
            if (supportedSrc != null) {
				localNmMap.put(src, (String) localNameMap.get(src));
			}
		}

        writeContent(out, localNmMap);

        out.println("<ul>");
        out.println("<li>");
        out.println("<img src=\"minus.gif\" id=\"IMG_N_1\" alt=\"show_hide\" onclick=\"show_hide('DIV_N_1');\" tabindex=\"1\">&nbsp;NCIt Neoplasm Core");
        out.println("<ul>");

		for (String association : ti._assocToChildMap.keySet()) {
			List<TreeItem> children = ti._assocToChildMap.get(association);
			if (children != null) {
				for (int i=0; i<children.size(); i++) {
					TreeItem childItem = (TreeItem) children.get(i);
					int j = i+1;
					writeNode(out, childItem, j);
				}
			}
		}

        out.println("</ul>");
        out.println("</li>");
        out.println("<ul>");
	}

	public void writeFooter(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

	public void generate(String outputfile) {
        long ms = System.currentTimeMillis();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputfile, "UTF-8");
            generate(pw);

		} catch (Exception ex) {

		} finally {
			try {
				pw.close();
				System.out.println("Output file " + outputfile + " generated.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Total run time (ms): " + (System.currentTimeMillis() - ms));
	}

	public static void main(String[] args) {
		String asciifile = args[0];
		NCImASCII2HTMLTreeConverter converter = new NCImASCII2HTMLTreeConverter(asciifile);
		int n = asciifile.lastIndexOf(".");
		String outputfile = asciifile.substring(0, n) + ".html";
		converter.generate(outputfile);
	}
}

