package resources;

public class Inbound {

	private static int _missing_trade_index = -1;

	public Inbound() {
		// TODO Auto-generated constructor stub
	}

	public static void setMissingTradeIndex(int index) {
		_missing_trade_index = index;
	}
	
	public static int getMissingTradeIndex() {
		return _missing_trade_index ;
	}
}
