package gov.nih.nci.evs.reportwriter.core.util;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.text.*;


public class SetupUtils {

	public static String getToday() {
		//return getToday("MM-dd-yyyy");
		return getToday("MMddyyyy");
	}

	public static String getToday(String format) {
		java.util.Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
	  throws IOException {
	    Files.walk(Paths.get(sourceDirectoryLocation))
	      .forEach(source -> {
		  Path destination = Paths.get(destinationDirectoryLocation, source.toString()
		    .substring(sourceDirectoryLocation.length()));
		  try {
		      Files.copy(source, destination);
		  } catch (IOException e) {
		      e.printStackTrace();
		  }
	      });
	}

    public static Vector listFiles(String startDir) {
		return listFiles(startDir, true, false);
	}

    public static Vector listFiles(String startDir, boolean recursion) {
		return listFiles(startDir, true, recursion);
	}

    public static Vector listFiles(String startDir, boolean withSize, boolean recursion) {
		Vector w = new Vector();
        File dir = new File(startDir);
        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            for (File file : files) {

                // Check if the file is a directory
                if (file.isDirectory()) {
                    // We will not print the directory name, just use it as a new
                    // starting point to list files from
                    if (recursion) {
						Vector v = listFiles(file.getAbsolutePath(), withSize);
						w.addAll(v);
					}
                } else {
                    // We can use .length() to get the file size
                    //System.out.println(file.getName() + " (size in bytes: " + file.length()+")");
                    if (withSize) {
                    	w.add(file.getName() + "|" + file.length());
					} else {
						w.add(file.getName());
					}
                }
            }
        }
        return w;
    }

    public static void dumpVector(String label, Vector w) {
		dumpVector(label, w, null);
	}


    public static void dumpVector(String label, Vector w, String ext) {
		System.out.println(label);
		for (int i=0; i<w.size(); i++) {
			String t = (String) w.elementAt(i);
			if (ext != null) {
				if (t.endsWith("." + ext)) {
					System.out.println(t);
				}
		    } else {
				System.out.println(t);
			}
		}
	}

	public static void main(String[] args) {
         try {
			 String sourceDirectoryLocation = "baseDir";
			 String today = getToday();
			 System.out.println(today);
			 String destinationDirectoryLocation = today;
			 copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);

		 } catch (Exception ex) {
			 ex.printStackTrace();
		 }
	}
}
