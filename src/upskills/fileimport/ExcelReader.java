package upskills.fileimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
	private String csvFile;
    private BufferedReader br = null;
    private String line = "";
    private static String csvSplitBy = "_;_";
    private String irrelevantColumns;
    private String filePath;
    
    public static List<String[]> readCSVFile(String filename) throws FileNotFoundException, IOException
    {
    	List<String[]> lines = new ArrayList<>();
    	try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
    	    String line = br.readLine();
    	    while (line != null) {
    	    	String[] cols = line.split(csvSplitBy);
    	    	lines.add(cols);
    	        line = br.readLine();
    	    }
    	}
    	return lines;
    }

    public static List<String[]> readXLSXFile(String filename) throws FileNotFoundException
    {
    	File myFile = new File("C://temp/Employee.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        return null;
        
    }
}
