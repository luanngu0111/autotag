package upskills.tagprocess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import resources.IConstants;

/**
 * @author LuanNgu Class usage is represent data after
 */
public class ResultObj {
	protected boolean selected;
	protected int trade_number;
	protected String portfolio;
	protected String instrument;

	private String trade_family;
	private String trade_group;
	private String trade_type;
	private String currency;
	protected String field_name;
	protected boolean systematic;
	protected List<Integer> issues;

	public List<Integer> getIssues() {
		return issues;
	}

	public void setIssues(List<Integer> issues) {
		this.issues = issues;
	}

	public String getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getTrade_number() {
		return trade_number;
	}

	public void setTrade_number(int trade_number) {
		this.trade_number = trade_number;
	}

	public String getTrade_family() {
		return trade_family;
	}

	public void setTrade_family(String trade_family) {
		this.trade_family = trade_family;
	}

	public String getTrade_group() {
		return trade_group;
	}

	public void setTrade_group(String trade_group) {
		this.trade_group = trade_group;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public boolean isSystematic() {
		return systematic;
	}

	public void setSystematic(boolean systematic) {
		this.systematic = systematic;
	}

	public ResultObj() {
		super();
		this.selected = false;
		this.trade_number = 0;
		this.trade_family = null;
		this.trade_group = null;
		this.trade_type = null;
		this.currency = null;
		this.field_name = null;
		this.systematic = false;
		this.issues = new ArrayList<Integer>();
	}

	/**
	 * @param selected
	 * @param trade_number
	 * @param trade_family
	 * @param trade_group
	 * @param trade_type
	 * @param currency
	 * @param field_name
	 * @param systematic
	 * @param issues
	 */
	public ResultObj(boolean selected, int trade_number, String trade_family, String trade_group, String trade_type,
			String currency, String field_name, boolean systematic, List<Integer> issues) {
		super();
		this.selected = selected;
		this.trade_number = trade_number;
		this.trade_family = trade_family;
		this.trade_group = trade_group;
		this.trade_type = trade_type;
		this.currency = currency;
		this.field_name = field_name;
		this.systematic = systematic;
		this.issues = issues;
	}

	/**
	 * @param trade_number
	 * @param trade_family
	 * @param trade_group
	 * @param trade_type
	 * @param currency
	 * @param field_name
	 * @param systematic
	 * @param issues
	 */
	public ResultObj(int trade_number, String trade_family, String trade_group, String trade_type, String currency,
			String field_name, boolean systematic, List<Integer> issues) {
		super();
		this.trade_number = trade_number;
		this.trade_family = trade_family;
		this.trade_group = trade_group;
		this.trade_type = trade_type;
		this.currency = currency;
		this.field_name = field_name;
		this.systematic = systematic;
		this.issues = issues;
	}

	/**
	 * @param selected
	 * @param trade_number
	 * @param trade_family
	 * @param trade_group
	 * @param trade_type
	 * @param currency
	 * @param field_name
	 * @param systematic
	 */
	public ResultObj(boolean selected, int trade_number, String trade_family, String trade_group, String trade_type,
			String currency, String field_name, boolean systematic) {
		super();
		this.selected = selected;
		this.trade_number = trade_number;
		this.trade_family = trade_family;
		this.trade_group = trade_group;
		this.trade_type = trade_type;
		this.currency = currency;
		this.field_name = field_name;
		this.systematic = systematic;
		this.issues = new ArrayList<Integer>();
	}

	/**
	 * @param trade_number
	 * @param trade_family
	 * @param trade_group
	 * @param trade_type
	 * @param currency
	 * @param field_name
	 * @param systematic
	 */
	public ResultObj(int trade_number, String trade_family, String trade_group, String trade_type, String currency,
			String field_name, boolean systematic) {
		super();
		this.trade_number = trade_number;
		this.trade_family = trade_family;
		this.trade_group = trade_group;
		this.trade_type = trade_type;
		this.currency = currency;
		this.field_name = field_name;
		this.systematic = systematic;
		this.issues = new ArrayList<Integer>();
	}

	/**
	 * Present object properties as a list of string
	 * 
	 * @return String list of object properties
	 */
	public List<String> convertObj() {
		List<String> str_list = new ArrayList<String>();
		str_list.add(selected ? "X" : " ");
//		if (portfolio != null)
			str_list.add(portfolio);
//		if (instrument != null)
			str_list.add(instrument);
//		if (trade_number != 0) {
			str_list.add(String.valueOf(trade_number));
			str_list.add(trade_family);
			str_list.add(trade_group);
			str_list.add(trade_type);
			str_list.add(currency);
//		}
		str_list.add(field_name);
		str_list.add(systematic ? "Y" : "N");
		if (issues != null) {
			for (int iss : issues) {
				str_list.add(String.valueOf(iss));
			}
		} else {
			str_list.add(" ");
		}
		return str_list;
	}

	public void addIssue(int issue_id) {
		if (issues == null)
			issues = new ArrayList<Integer>();
		this.issues.add(issue_id);
	}

	public void addAllIssues(Collection<? extends Integer> collection) {
		this.issues.addAll(collection);
	}

	public Object getValueByName(String field_name) {
		if (field_name.equals(IConstants.HEADER_CURR))
			return this.currency;
		if (field_name.equals(IConstants.HEADER_FAMILY))
			return this.trade_family;
		if (field_name.equals(IConstants.HEADER_GROUP))
			return this.trade_group;
		if (field_name.equals(IConstants.HEADER_INS))
			return this.instrument;
		if (field_name.equals(IConstants.HEADER_PORT))
			return this.portfolio;
		if (field_name.equals(IConstants.HEADER_TRADE))
			return this.trade_number;
		if (field_name.equals(IConstants.HEADER_TYPE))
			return this.trade_type;

		return null;
	}
}
