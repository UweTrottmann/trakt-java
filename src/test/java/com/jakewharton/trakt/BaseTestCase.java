package com.jakewharton.trakt;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
	protected static final String API_KEY = "7f9fb61a46ed0d8ecc917b789154d397";
	
	private final ServiceManager manager = new ServiceManager();
	
	@Override
	public void setUp() {
		manager.setApiKey(API_KEY);
		manager.setAuthentication("trakt-java", "e00e0e31fe07213b59e5784c9942cfe220771827");
	}
	
	protected final ServiceManager getManager() {
		return manager;
	}
}
