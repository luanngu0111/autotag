package upskills.tagprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import resources.IConstants;

public class HeaderMap {

	public HeaderMap() {
		// TODO Auto-generated constructor stub
	}

	private static String[] trade_number_head = new String []{"NB","TRN_NB","M_NB"};
	private static String[] family_head = new String []{"FAM","FAMILY","TRN_FMLY","M_TRN_FMLY"};
	private static String[] group_head = new String []{"GROUP","TRN_GRP","M_TRN_GRP"};
	private static String[] type_head = new String []{"TYPE","TYP","TRN_TYPE","M_TRN_TYPE"};
	private static String[] portfolio_head = new String []{"PORT","PORTFOLIO","TP_PFOLIO","M_TP_PFOLIO"};
	private static String[] instrument_head = new String []{"INSTRUMENT","M_INSTRUMENT"};
	private static String[] currency_head = new String []{"CURRENCY","TP_FXBASE","M_TP_FXBASE"};
	
	
	public static String MapHeaderToColumn(String header)
	{
		if (Arrays.asList(trade_number_head).contains(header.toUpperCase()))
			return IConstants.HEADER_TRADE;
		if (Arrays.asList(family_head).contains(header.toUpperCase()))
			return IConstants.HEADER_FAMILY;
		if (Arrays.asList(group_head).contains(header.toUpperCase()))
			return IConstants.HEADER_GROUP;
		if (Arrays.asList(type_head).contains(header.toUpperCase()))
			return IConstants.HEADER_TYPE;
		if (Arrays.asList(portfolio_head).contains(header.toUpperCase()))
			return IConstants.HEADER_PORT;
		if (Arrays.asList(instrument_head).contains(header.toUpperCase()))
			return IConstants.HEADER_INS;
		if (Arrays.asList(currency_head).contains(header.toUpperCase()))
			return IConstants.HEADER_CURR;
		return header;
	}
	
	public static List<String> MapHeaderToColumn(List<String> headers)
	{
		List<String> result = new ArrayList<String>();
		for (String header : headers)
		{
			result.add(MapHeaderToColumn(header));
		}
		return result;
	}
	
	public static String[] MapHeaderToColumn(String[] headers)
	{
		String[] result = new String[headers.length];
		for (int i = 0; i < result.length; i++) {
			result[i]= MapHeaderToColumn(headers[i]);
		}
		
		return result;
	}
}
