package gov.nih.nci.evs.reportwriter.core.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hpsf.SummaryInformation;
import java.io.*;

public class DocSummaryInfo {
	static String APPLICATION_NAME = "Application Name";
	static String AUTHOR = "Author";
	static String CHARACTER_COUNT = "Character Count";
	static String COMMENTS = "Comments";
	static String CREATE_DATE_TIME = "Create Date Time"; //java.util.Date createDateTime
	static String EDIT_TIME = "Edit Time"; //long time
	static String KEYWORDS = "Keywords";
	static String LAST_AUTHOR = "Last Author";
	static String LAST_PRINTED = "Last Printed"; //java.util.Date lastPrinted
	static String LAST_SAVE_DATE_TIME  = "Last Save Date Time"; //java.util.Date time
	static String PAGE_COUNT  = "Page Count"; //int pageCount
	static String REV_NUMBER  = "Rev Number"; //java.lang.String revNumber
	static String SECURITY  = "Security"; //int security
	static String SUBJECT  = "Subject";
	static String TEMPLATE  = "Template";
	static String THUMBNAIL  = "Thumnail"; //byte[] thumbnail
	static String TITLE  = "Title";
	static String WORD_COUNT  = "Word Count";

	private HSSFWorkbook workbook = null;

	public DocSummaryInfo(String filename) {
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		this.workbook = loadWorkbook(filename);
		sheet = this.workbook.createSheet();
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(1);
		cell = row.createCell(1);
		cell.setCellValue(2);
		cell = row.createCell(2);
		cell.setCellValue(3);
		cell = row.createCell(3);
		cell.setCellValue(4);
	}

    public HSSFWorkbook loadWorkbook(String filename) {
		HSSFWorkbook workbook = null;
		SummaryInformation summaryInfo = null;
		FileInputStream fis = null;
		try {
			System.out.println(filename);
			fis = new FileInputStream(filename);

			workbook = new HSSFWorkbook(fis);
			return workbook;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception ex) {

			}
		}
		return workbook;
	}

	public void setAuthor(String author) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		summaryInfo.setAuthor(author);
	}

	public void setTitle(String title) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		summaryInfo.setTitle(title);
	}

	public void setKeywords(String kewords) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		summaryInfo.setKeywords(kewords);
	}

	public void setSubject(String subject) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		summaryInfo.setSubject(subject);
	}

	public void setComments(String comments) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		summaryInfo.setComments(comments);
	}


	public void setMetadata(String title, String author, String subject, String keywords, String comments) {
		this.workbook.createInformationProperties();
		SummaryInformation summaryInfo =
		this.workbook.getSummaryInformation();
		if (title != null) summaryInfo.setTitle(title);
		if (author != null) summaryInfo.setAuthor(author);
		if (subject != null) summaryInfo.setSubject(subject);
		if (keywords != null) summaryInfo.setKeywords(keywords);
		if (comments != null) summaryInfo.setComments(comments);
	}

	public void dumpMetadata() {
		SummaryInformation summaryInfo = this.workbook.getSummaryInformation();
		System.out.println("Title: " + summaryInfo.getTitle() + "\n" +
		                   "Author: " + summaryInfo.getAuthor() + "\n" +
		                   "Subject: " + summaryInfo.getSubject() + "\n" +
		                   "Keywords: " + summaryInfo.getKeywords() + "\n" +
		                   "Comments: " + summaryInfo.getComments());
	}

	public void saveWorkbook(String filename) {
		OutputStream os = null;
		File file = null;
		try {
			file = new File(filename);
			os = new FileOutputStream(file);
			this.workbook.write(os);
		}
		catch(IOException ioEx) {
			printExceptionDetails(ioEx);
		}
		finally {
			try {
				if(os != null) {
					os.close();
					os = null;
				}
			}
			catch(IOException ioEx) {
				printExceptionDetails(ioEx);
			}
		}
	}

	private static final void printExceptionDetails(Exception ex) {
		System.out.println("Caught a: " + ex.getClass().getName());
		System.out.println("Message: " + ex.getMessage());
		System.out.println("Stacktrace follows:.....");
		ex.printStackTrace(System.out);
	}

	/**
	* @param args the command line arguments
	*/
	public static void main(String[] args) {
		try {
			String xlsfile = args[0];
			DocSummaryInfo dsi = new DocSummaryInfo(xlsfile);
			int n = xlsfile.lastIndexOf(".");
			String title = xlsfile.substring(0, n);
			String author = "NCI-EVS";
			dsi.setMetadata(title, author, title, title, null);
			dsi.saveWorkbook(xlsfile);
			dsi.dumpMetadata();
		}
		catch(Exception ex) {
			System.out.println(ex.getClass().getName());
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}
}

