package resources;

import java.util.Arrays;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static int parseTradeNumber(String str) {
		AutoLogger log = AutoLogger.getInstance();
	    int number = 0;
	    try {
	        number =  Integer.parseInt(str);
	    } catch(NumberFormatException e) {
	        try {
	            number = (int) Double.parseDouble(str);
	        } catch(NumberFormatException e1) {
	            try {
	                number = (int) Float.parseFloat(str);
	            } catch(NumberFormatException e2) {
	                try {
	                    number = (int) Long.parseLong(str);
	                } catch(NumberFormatException e3) {
	                	log.writeInLog(Arrays.toString(e3.getStackTrace()));
	                    throw e3;
	                }       
	            }       
	        }       
	    }
	    return number;
	}
	
}
