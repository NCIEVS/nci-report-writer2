package gov.nih.nci.evs.reportwriter.core.util;

import java.io.*;
import java.util.*;

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

/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2008-2016 NGIS. This software was developed in conjunction
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


public class XLStoXLSX {

    /**
     * @param args
     * @throws InvalidFormatException
     * @throws IOException
     */

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

    public static void run(String inputfile, String outputfile) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(inputfile));
        try {
            Workbook wbIn = new HSSFWorkbook(in);
            File outFn = new File(outputfile);
            if (outFn.exists()) {
                outFn.delete();
			}

            //Workbook wbOut = new XSSFWorkbook();
			XSSFWorkbook wbOut = new XSSFWorkbook();
			//XSSFSheet sheet = wbOut.createSheet(sheetName) ;
			XSSFCreationHelper helper = wbOut.getCreationHelper();
			XSSFCellStyle hlink_style = wbOut.createCellStyle();
			XSSFFont hlink_font = wbOut.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(HSSFColorPredefined.BLUE.getIndex());
			hlink_style.setFont(hlink_font);

            int sheetCnt = wbIn.getNumberOfSheets();
            for (int i = 0; i < sheetCnt; i++) {
                Sheet sIn = wbIn.getSheetAt(i);
                XSSFSheet sOut = wbOut.createSheet(sIn.getSheetName());
                Iterator<Row> rowIt = sIn.rowIterator();
                while (rowIt.hasNext()) {
                    Row rowIn = rowIt.next();
                    XSSFRow rowOut = sOut.createRow(rowIn.getRowNum());

                    Iterator<Cell> cellIt = rowIn.cellIterator();
                    while (cellIt.hasNext()) {
                        Cell cellIn = cellIt.next();
                        XSSFCell cellOut = rowOut.createCell(
                                cellIn.getColumnIndex(), cellIn.getCellType());

                        switch (cellIn.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            cellOut.setCellValue(cellIn.getBooleanCellValue());
                            break;

                        case Cell.CELL_TYPE_ERROR:
                            cellOut.setCellValue(cellIn.getErrorCellValue());
                            break;

                        case Cell.CELL_TYPE_FORMULA:
                            cellOut.setCellFormula(cellIn.getCellFormula());
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            cellOut.setCellValue(cellIn.getNumericCellValue());
                            break;

                        case Cell.CELL_TYPE_STRING:

							String value = cellIn.getStringCellValue();
							if (!isCode(value)) {
								cellOut.setCellValue(value);
							} else {
								String urlLink = value;
								String address = getNCItHyperlink(value);
								//cell = row.createCell(c);
								XSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
								link.setAddress(address);
								cellOut.setHyperlink(link);
								cellOut.setCellStyle(hlink_style);
								cellOut.setCellValue(urlLink);
								cellOut.setCellValue(value);
							}

                            //cellOut.setCellValue(cellIn.getStringCellValue());
                            break;
                        }
						CellStyle styleIn = cellIn.getCellStyle();
						CellStyle styleOut = cellOut.getCellStyle();
						styleOut.setDataFormat(styleIn.getDataFormat());
                        cellOut.setCellComment(cellIn.getCellComment());
                    }
                }
            }
            OutputStream out = new BufferedOutputStream(new FileOutputStream(
                    outFn));
            try {
                wbOut.write(out);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
	}

    public static void main(String[] args) throws IOException {
        String inputfile = args[0];
        String outputfile = args[1];
        run(inputfile, outputfile);
    }
}

