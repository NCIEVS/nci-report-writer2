package gov.nih.nci.evs.reportwriter.core.util;

import gov.nih.nci.evs.restapi.util.*;

import java.io.*;
import java.util.*;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Font;

import com.opencsv.CSVReader;
import java.awt.Color;
import java.io.*;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFFont;

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


public class ExcelReadWriteUtils {

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
        char delim = '|';
        return parseData(line, delim);
    }


	public static String tab2CSV(String line, char delim) {
		Vector u = parseData(line, delim);
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<u.size(); i++) {
			String t = (String) u.elementAt(i);
			t = "\"" + t + "\"";
			buf.append(t).append(",");
		}
		String s = buf.toString();
		s = s.substring(0, s.length()-1);
		return s;
	}

	public static String delimited2CSV(String tabfile, char delim) {
		Vector w = new Vector();
		Vector v = readFile(tabfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			w.add(tab2CSV(line, delim));
		}
		int n = tabfile.lastIndexOf(".");
		String outputfile = tabfile.substring(0, n) + ".csv";
		saveToFile(outputfile, w);
		return outputfile;
	}

	public static String tabDelimited2CSV(String tabfile) {
		Vector w = new Vector();
		Vector v = readFile(tabfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			w.add(tab2CSV(line, '\t'));
		}
		int n = tabfile.lastIndexOf(".");
		String outputfile = tabfile.substring(0, n) + ".csv";
		saveToFile(outputfile, w);
		return outputfile;
	}

	public static Vector readXLSFile(String xlsfile) throws IOException
	{
		Vector w = new Vector();
		InputStream ExcelFileToRead = new FileInputStream(xlsfile);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			StringBuffer buf = new StringBuffer();
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					//System.out.print(cell.getStringCellValue()+" ");
					buf.append(cell.getStringCellValue());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append(cell.getNumericCellValue());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append(cell.getCellFormula());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append("");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_ERROR)
				{
				    buf.append("#ERROR#");
				}
				else
				{
					//Handel Boolean
				}
				buf.append("\t");
			}
			//System.out.println();
			String t = buf.toString();
			t = t.substring(0, t.length()-1);
			w.add(t);
		}
		return w;
	}

	public static String writeXLSFile(String textfile, char delim, String sheetName) throws IOException {
		int n = textfile.lastIndexOf(".");
		String excelFileName = textfile.substring(0, n) + ".xls";
		//String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		Vector w = new Vector();
		Vector v = readFile(textfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, delim);
			HSSFRow row = sheet.createRow(i);
			for (int c=0;c < u.size(); c++ )
			{
				HSSFCell cell = row.createCell(c);
				String value = (String) u.elementAt(c);
				cell.setCellValue(value);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return excelFileName;
	}

	public static Vector readXLSXFile(String xlsxfile) throws IOException
	{
		Vector w = new Vector();
		InputStream ExcelFileToRead = new FileInputStream(xlsxfile);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);

		XSSFWorkbook test = new XSSFWorkbook();

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			StringBuffer buf = new StringBuffer();
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();

				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					//System.out.print(cell.getStringCellValue()+" ");
					buf.append(cell.getStringCellValue());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append(cell.getNumericCellValue());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append(cell.getCellFormula());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
				{
					//System.out.print(cell.getNumericCellValue()+" ");
					buf.append("");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_ERROR)
				{
				    buf.append("#ERROR#");
				}
				else
				{
					//Handel Boolean
				}
				buf.append("\t");
			}
			//System.out.println();
			String t = buf.toString();
			t = t.substring(0, t.length()-1);
			w.add(t);
		}
        return w;
	}


	public static String writeXLSXFile(String textfile, char delim, String sheetName) throws IOException {
		int n = textfile.lastIndexOf(".");
		String excelFileName = textfile.substring(0, n) + ".xlsx";
		//String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		Vector w = new Vector();
		Vector v = readFile(textfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, delim);
			XSSFRow row = sheet.createRow(i);
			for (int c=0; c<u.size(); c++ )
			{
				XSSFCell cell = row.createCell(c);
				String value = (String) u.elementAt(c);
				cell.setCellValue(value);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return excelFileName;
	}

    public static List<String> getXLSXSheetNames(String xslxfile) {
		File file = new File(xslxfile);
		List<String> sheetNames = null;
		try {
			XSSFWorkbook wb = new XSSFWorkbook(file);
			sheetNames = new ArrayList<String>();
			for (int i=0; i<wb.getNumberOfSheets(); i++) {
				sheetNames.add( wb.getSheetName(i) );
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sheetNames;
	}

    public static List<String> getXLSSheetNames(String xlsfile) {
		List<String> sheetNames = null;
		try {
			InputStream is = new FileInputStream(xlsfile);
			HSSFWorkbook wb = new HSSFWorkbook(is);
			sheetNames = new ArrayList<String>();
			for (int i=0; i<wb.getNumberOfSheets(); i++) {
				sheetNames.add( wb.getSheetName(i) );
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sheetNames;
	}

	public static String getNCItHyperlink(String code) {
        String line = "https://nciterms.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=" + code;
        return line;
    }

	public static String getNCImHyperlink(String code) {
        String line = "https://ncim.nci.nih.gov/ncimbrowser/ConceptReport.jsp?dictionary=NCI%20Metathesaurus&code=" + code;
        return line;
	}

	public static String getSourceHyperlink(String source, String code) {
        String line = "https://nciterms.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=" + source + "&code=" + code;
        line = line.replace(" ", "%20");
        return line;
    }

    public static boolean isCode(String str) {
		if (str == null || str.length() == 0) return false;
		char ch = str.charAt(0);
		if (ch != 'C') return false;
		String s = str.substring(1, str.length());
		try {
			int n = Integer.parseInt(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

    public static boolean isNCItCode(String str) {
		if (str == null || str.length() == 0) return false;
		char ch = str.charAt(0);
		if (ch != 'C') return false;
		String s = str.substring(1, str.length());
		try {
			int n = Integer.parseInt(s);
			if (str.length() < 8)
			return true;
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

    public HSSFCell createHSSFCell(HSSFSheet sheet, int rowId, int colId, String value) {
		HSSFRow row = sheet.createRow(rowId);
		HSSFCell cell = row.createCell(colId);
		cell.setCellValue(value);
		return cell;
	}

    public XSSFCell createHSSFCell(XSSFSheet sheet, int rowId, int colId, String value) {
		XSSFRow row = sheet.createRow(rowId);
		XSSFCell cell = row.createCell(colId);
		cell.setCellValue(value);
		return cell;
	}

/*
    public static HSSFCell createHSSFCell(HSSFWorkbook wb, HSSFHyperlink link, HSSFCellStyle hlink_style,
        HSSFSheet sheet, int rowId, int colId, String urlLink, String address) {

		HSSFCreationHelper helper = wb.getCreationHelper();
		HSSFCellStyle hlink_style = wb.createCellStyle();
		HSSFFont hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(HSSFColorPredefined.BLUE.getIndex());
		hlink_style.setFont(hlink_font);


        HSSFCell cell = sheet.createRow(rowId).createCell(colId);
		cell.setCellValue(urlLink);
		//HSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
		link.setAddress(address);
		cell.setHyperlink(link);
		cell.setCellStyle(hlink_style);
		return cell;
	}
*/

    public static XSSFCell createXSSFCell(XSSFWorkbook wb, XSSFSheet sheet, int rowId, int colId, String urlLink, String address) {
		XSSFCreationHelper helper = wb.getCreationHelper();
		XSSFCellStyle hlink_style = wb.createCellStyle();
		XSSFFont hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(HSSFColorPredefined.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
        XSSFCell cell = sheet.createRow(rowId).createCell(colId);
		cell.setCellValue(urlLink);
		XSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
		link.setAddress(address);
		cell.setHyperlink(link);
		cell.setCellStyle(hlink_style);
		return cell;
	}

	public static void test(String[] args) throws IOException {
		String code = "C12345";
		boolean retval = isCode(code);
		System.out.println(code + ": " + retval);

		code = "A12345";
		retval = isCode(code);
		System.out.println(code + ": " + retval);

		code = "C1234a";
		retval = isCode(code);
		System.out.println(code + ": " + retval);

	}

	public static String text2XLS(String textfile, char delim, String sheetName) throws IOException {
		int n = textfile.lastIndexOf(".");
		String excelFileName = textfile.substring(0, n) + ".xls";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;
		HSSFCreationHelper helper = wb.getCreationHelper();
		HSSFCellStyle hlink_style = wb.createCellStyle();
		HSSFFont hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(HSSFColorPredefined.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		Vector w = new Vector();
		Vector v = readFile(textfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, delim);
			HSSFRow row = sheet.createRow(i);
			for (int c=0;c < u.size(); c++ )
			{
				HSSFCell cell = null;
				String value = (String) u.elementAt(c);
				if (!isCode(value)) {
					cell = row.createCell(c);
					cell.setCellValue(value);
				} else {
					String urlLink = value;
					String address = getNCItHyperlink(value);
					cell = row.createCell(c);
					HSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
					link.setAddress(address);
					cell.setHyperlink(link);
					cell.setCellStyle(hlink_style);
					cell.setCellValue(urlLink);
				}
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return excelFileName;
	}

	public static String text2XLSX(String textfile, char delim, String sheetName) throws IOException {
		int n = textfile.lastIndexOf(".");
		String excelFileName = textfile.substring(0, n) + ".xls";
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		XSSFCreationHelper helper = wb.getCreationHelper();
		XSSFCellStyle hlink_style = wb.createCellStyle();
		XSSFFont hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(HSSFColorPredefined.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		Vector w = new Vector();
		Vector v = readFile(textfile);
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = parseData(line, delim);
			XSSFRow row = sheet.createRow(i);
			for (int c=0;c < u.size(); c++ )
			{
				XSSFCell cell = null;
				String value = (String) u.elementAt(c);
				if (!isCode(value)) {
					cell = row.createCell(c);
					cell.setCellValue(value);
				} else {
					String urlLink = value;
					String address = getNCItHyperlink(value);
					cell = row.createCell(c);
					XSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
					link.setAddress(address);
					cell.setHyperlink(link);
					cell.setCellStyle(hlink_style);
					cell.setCellValue(urlLink);
				}
			}
		}
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return excelFileName;
	}

	public static void main(String[] args) throws IOException {
		//String textfile = "Neoplasm_Core_Terminology.txt";
        //String excelFileName = writeXLSXFile(textfile, '\t', "21.06a");
        //System.out.println(excelFileName + " generated.");
        /*
        String xlsxfile = "Neoplasm_Core_Terminology.xlsx";
        List<String> sheetNames = getXLSXSheetNames(xlsxfile);
        System.out.println(sheetNames.get(0));
        */

/*
        String xlsfile = "Neoplasm_Core_Terminology.xls";
        List<String> sheetNames = getXLSSheetNames(xlsfile);
        System.out.println(sheetNames.get(0));

        String excelFileName = writeXLSXFile(textfile, '\t', sheetNames.get(0));
        System.out.println(excelFileName + " generated.");

        String csvfile = tabDelimited2CSV(textfile);
        System.out.println(csvfile + " generated.");
*/

		/*

		Vector w = readXLSFile("Neoplasm_Core.xls");
		saveToFile("test1.txt", w);

		w = readXLSXFile("Neoplasm_Core.xlsx");
		saveToFile("test2.txt", w);

        String csvfile = tabDelimited2CSV("test2.txt");

        String excelFileName = writeXLSFile("test1.txt", '\t', "Sheet1");
        System.out.println(excelFileName + " genereated.");

        excelFileName = writeXLSXFile("test2.txt", '\t', "Sheet1");
        System.out.println(excelFileName + " genereated.");
        */

        String textfile = args[0];
        try {
        	String xlsfile = text2XLS(textfile, '|', "21.06a");
        	System.out.println(xlsfile + " generated.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
