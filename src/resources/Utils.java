package resources;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static int parseTradeNumber(String str) {
	    int number = 0;
	    try {
	        number = (int) Float.parseFloat(str);
	    } catch(NumberFormatException e) {
	        try {
	            number = (int) Double.parseDouble(str);
	        } catch(NumberFormatException e1) {
	            try {
	                number = Integer.parseInt(str);
	            } catch(NumberFormatException e2) {
	                try {
	                    number = (int) Long.parseLong(str);
	                } catch(NumberFormatException e3) {
	                    throw e3;
	                }       
	            }       
	        }       
	    }
	    return number;
	}
	
}
