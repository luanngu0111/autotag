package resources;

import logger.impl.AutotagLogger;

public class AutoLogger extends AutotagLogger {

	private static AutoLogger instance;
	
	private AutoLogger() {
		super();
	}

	public static AutoLogger getInstance()
	{
		if (instance == null)
			instance = new AutoLogger();
		return instance;
	}
}
