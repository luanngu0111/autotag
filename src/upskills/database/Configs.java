package upskills.database;

import org.hibernate.cfg.Configuration;

import resources.IConstants;

public class Configs {
	/**
	 * @param host host name ex: http://hostname
	 * @param port port number
	 * @param username 
	 * @param password
	 * @param dbname database name
	 */
	public static Configuration setProperties(String host, String port, String username, String password, String dbname)
	{
		Configuration c = new Configuration().configure("/resources/hibernate.cfg.xml");
		String url = "jdbc:mysql://"+host+":"+port+"/"+dbname+"?autoReconnect=true&useSSL=false";
		c.setProperty("hibernate.connection.username",username);
		c.setProperty("hibernate.connection.password",password);
		c.setProperty("hibernate.connection.url",url);
		return c;
	}
	
	/**
	 * @param url: database connection url
	 * @param username
	 * @param password
	 * @param dbname database name
	 */
	public static Configuration setProperties(String url, String username, String password, String dbname)
	{
		Configuration c = new Configuration();
		c.setProperty("hibernate.connection.username",username);
		c.setProperty("hibernate.connection.password",password);
		c.setProperty("hibernate.connection.url",url);
		return c;
	}
	
	
	
}
