package upskills.database;

import org.hibernate.cfg.Configuration;

public class Configs {
	/**
	 * @param host host name ex: http://hostname
	 * @param port port number
	 * @param username 
	 * @param password
	 * @param dbname database name
	 */
	public static void setProperties(String host, String port, String username, String password, String dbname)
	{
		Configuration c = new Configuration();
		String url = "jdbc:mysql://"+host+":"+port+"/"+dbname;
		c.setProperty("hibernate.connection.username",username);
		c.setProperty("hibernate.connection.password",password);
		c.setProperty("hibernate.connection.url",url);
	}
	
	/**
	 * @param url: database connection url
	 * @param username
	 * @param password
	 * @param dbname database name
	 */
	public static void setProperties(String url, String username, String password, String dbname)
	{
		Configuration c = new Configuration();
		c.setProperty("hibernate.connection.username",username);
		c.setProperty("hibernate.connection.password",password);
		c.setProperty("hibernate.connection.url",url);
	}
	
}
