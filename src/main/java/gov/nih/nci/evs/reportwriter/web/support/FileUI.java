package gov.nih.nci.evs.reportwriter.web.support;

import java.io.InputStream;

public class FileUI implements java.io.Serializable {

	private String filePath;
	private InputStream rawFileStream;
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getRawFileStream() {
		return rawFileStream;
	}

	public void setRawFileStream(InputStream rawFileStream) {
		this.rawFileStream = rawFileStream;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}