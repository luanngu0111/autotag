package upskills.tagprocess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LuanNgu
 * Class usage is represent data after 
 */
public class ResultObj {
	private boolean selected;
	private int trade_number;
	private String trade_family;
	private String trade_group;
	private String trade_type;
	private String currency;
	private String field_name;
	private boolean systematic;
	private Integer issues;
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
	public Integer getIssues() {
		return issues;
	}
	public void setIssues(Integer issues) {
		this.issues = issues;
	}
	
	public ResultObj()
	{
		super();
		this.selected = false;
		this.trade_number = 0;
		this.trade_family = null;
		this.trade_group = null;
		this.trade_type = null;
		this.currency = null;
		this.field_name = null;
		this.systematic = false;
		this.issues = 0;
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
			String currency, String field_name, boolean systematic, Integer issues) {
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
			String field_name, boolean systematic, Integer issues) {
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
	}
	
	/** Present object properties as a list of string
	 * @return String list of object properties
	 */
	public List<String> convertObj()
	{
		List<String> str_list = new ArrayList<String>();
		str_list.add(selected?"X":" ");
		str_list.add(String.valueOf(trade_number));
		str_list.add(trade_family);
		str_list.add(trade_group);
		str_list.add(trade_type);
		str_list.add(currency);
		str_list.add(field_name);
		str_list.add(systematic?"Y":"N");
		str_list.add(String.valueOf(issues));
		return str_list;
	}
}
