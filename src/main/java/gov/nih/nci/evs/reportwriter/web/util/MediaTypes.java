package gov.nih.nci.evs.reportwriter.web.util;

import java.util.HashMap;

public class MediaTypes {

	private static final String DOT = ".";
	private static final String DOT_PATTERN = "\\.";

	private static final String STANDARD_MEDIA_TYPE = "application/octet-stream";
	private static final HashMap<String, String> MEDIA_TYPES_MAP = new HashMap<String, String>();
	static {
		MEDIA_TYPES_MAP.put(".ai", "application/postscript");
		MEDIA_TYPES_MAP.put(".bed", "application/vnd.realvnc.bed");
		MEDIA_TYPES_MAP.put(".bmp", "image/bmp");
		MEDIA_TYPES_MAP.put(".bz2", "application/x-bzip2");
		MEDIA_TYPES_MAP.put(".csv", "text/csv");
		MEDIA_TYPES_MAP.put(".doc", "application/msword");
		MEDIA_TYPES_MAP
				.put(".docx",
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		MEDIA_TYPES_MAP.put(".gif", "image/gif");
		MEDIA_TYPES_MAP.put(".gz", "application/x-gzip");
		MEDIA_TYPES_MAP.put(".htm", "text/html");
		MEDIA_TYPES_MAP.put(".html", "text/html");
		MEDIA_TYPES_MAP.put(".jp2", "image/jp2");
		MEDIA_TYPES_MAP.put(".jpg", "image/jpeg");
		MEDIA_TYPES_MAP.put(".js", "application/javascript");
		MEDIA_TYPES_MAP.put(".map", "application/x-navimap");
		MEDIA_TYPES_MAP.put(".mdb", "application/x-msaccess");
		MEDIA_TYPES_MAP.put(".pdf", "application/pdf");
		MEDIA_TYPES_MAP.put(".pl", "text/plain");
		MEDIA_TYPES_MAP.put(".png", "image/png");
		MEDIA_TYPES_MAP.put(".ppt", "application/vnd.ms-powerpoint");
		MEDIA_TYPES_MAP
				.put(".pptx",
						"application/vnd.openxmlformats-officedocument.presentationml.presentation");
		MEDIA_TYPES_MAP.put(".rar", "application/x-rar-compressed");
		MEDIA_TYPES_MAP.put(".rtf", "application/rtf");
		MEDIA_TYPES_MAP.put(".sitx", "application/x-stuffitx");
		MEDIA_TYPES_MAP.put(".tif", "image/tiff");
		MEDIA_TYPES_MAP.put(".tiff", "image/tiff");
		MEDIA_TYPES_MAP.put(".tsv", "text/tab-separated-values");
		MEDIA_TYPES_MAP.put(".txt", "text/plain");
		MEDIA_TYPES_MAP.put(".log", "text/plain");
		MEDIA_TYPES_MAP.put(".template", "text/plain");
		MEDIA_TYPES_MAP.put(".xls", "application/vnd.ms-excel");
		MEDIA_TYPES_MAP
				.put(".xlsx",
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		MEDIA_TYPES_MAP.put(".xml", "application/xml");
		MEDIA_TYPES_MAP.put(".zip", "application/zip");
	}

	public static String getMediaType(String fileName) {
		String[] components = fileName.split(MediaTypes.DOT_PATTERN);
		String suffix = String.join(SearchConstants.EMPTY_STR, DOT,
				components[components.length - 1].toLowerCase());
		String mediaType = STANDARD_MEDIA_TYPE;
		if (MEDIA_TYPES_MAP.containsKey(suffix)) {
			mediaType = MEDIA_TYPES_MAP.get(suffix);
		}
		return mediaType;
	}

}
