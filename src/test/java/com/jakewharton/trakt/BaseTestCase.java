package com.jakewharton.trakt;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
	protected static final String API_KEY = "de8bc5e9866c77d655310bb0c8ab9a83";
	
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
