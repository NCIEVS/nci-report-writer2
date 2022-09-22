package gov.nih.nci.evs.reportwriter.core.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;


import java.io.File;
import java.io.*;
import java.util.*;
import java.text.*;

public class ExcelReader {
	public static DataFormatter dataFormatter = new DataFormatter();

    public static Vector csv2Text(String xlsxfile, String sheetName) throws Exception {
		DataFormatter dataFormatter  = new DataFormatter();
		File file = new File(xlsxfile);
        FileInputStream ip = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(ip);
        Sheet sheet = wb.getSheet(sheetName);
		Vector w = new Vector();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            StringBuffer buf = new StringBuffer();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                buf.append(cellValue + "\t");
            }
            String t = buf.toString();
            t = t.substring(0, t.length()-1);
            w.add(t);
        }
        ip.close();
        return w;
    }

    public static int getNumberOfSheets(String excelfile) {
		try {
			Workbook workbook = WorkbookFactory.create(new File(excelfile));
			return workbook.getNumberOfSheets();
		} catch (Exception ex) {
			//return -1;
		}
		return -1;
	}

    public static Workbook openWorkbook(String excelfile) {
		try {
			Workbook workbook = WorkbookFactory.create(new File(excelfile));
			return workbook;
		} catch (Exception ex) {
			//return -1;
		}
		return null;
	}

    public static int getNumberOfSheets(Workbook workbook) {
        return workbook.getNumberOfSheets();
	}

    public static Vector getSheetNames(String excelfile) {
		try {
			Workbook workbook = WorkbookFactory.create(new File(excelfile));
			return getSheetNames(workbook);
		} catch (Exception ex) {
			//return -1;
		}
		return null;
	}


    public static Vector getSheetNames(Workbook workbook) {
		Vector<String> v = new Vector();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            v.add(sheet.getSheetName());
        }
        return v;
	}

    public Sheet getSheet(Workbook workbook, int sheetNumber) {
		return workbook.getSheetAt(sheetNumber);
	}


    public Sheet getSheet(String excelfile, int sheetNumber) {
		try {
		     Workbook workbook = openWorkbook(excelfile);
 		     return getSheet(workbook, sheetNumber);
		} catch (Exception e) {

		}
		return null;
	}

    public static Vector toDelimited(String excelfile, char delim) {
		return toDelimited(excelfile, 0, delim);
	}

    public static Vector toDelimited(String excelfile, int sheetNumber, char delim) {
		Vector w = new Vector();
		Workbook workbook = openWorkbook(excelfile);
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
			StringBuffer buf = new StringBuffer();
            Row row = rowIterator.next();
            int lastCellNumber = row.getLastCellNum();
            for (int lcv=0; lcv<lastCellNumber; lcv++) {
				Cell cell = row.getCell(lcv);
				String cellValue = "";
				if (cell != null) {
					cellValue = getCellValue(cell);
				}
                buf.append(cellValue).append(delim);
			}
            String line = buf.toString();
            line = line.substring(0, line.length()-1);
            w.add(line);
        }
        try {
        	workbook.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return w;
	}

    private static void printCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                break;
            case STRING:
                System.out.print(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.print(cell.getDateCellValue());
                } else {
                    System.out.print(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                System.out.print(cell.getCellFormula());
                break;
            case BLANK:
                System.out.print("");
                break;
            default:
                System.out.print("");
        }
        System.out.print("\t");
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                Boolean bool_obj = cell.getBooleanCellValue();
                boolean bool = Boolean.valueOf(bool_obj);
                return "" + bool;

            case STRING:
                return (cell.getRichStringCellValue().getString());

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return ("" + cell.getDateCellValue());
                } else {
                    return ("" + cell.getNumericCellValue());
                }

            case FORMULA:
                return(cell.getCellFormula().toString());

            case BLANK:
                return "";

            default:
                return "";
        }
    }


    public static void dumpVector(String label, Vector v) {
		System.out.println("\n" + label + ":");
		if (v == null) return;
		if (v.size() == 0) {
			System.out.println("\tNone");
			return;
		}
        for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
			int j = i+1;
			System.out.println("\t(" + j + ") " + t);
		}
		System.out.println("\n");
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

	public static String getToday() {
		return getToday("MM-dd-yyyy");
	}

	public static String getToday(String format) {
		java.util.Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
/*
    public static void main(String[] args) throws IOException, InvalidFormatException {
        String excelfile = args[0];
        System.out.println(excelfile);
        Vector w = toDelimited(excelfile, '|');
        dumpVector(excelfile, w);
        int n = excelfile.lastIndexOf(".");
        String textfile = excelfile.substring(0, n) + "_" + getToday() + ".txt";
        saveToFile(textfile, w);
    }
*/
}
