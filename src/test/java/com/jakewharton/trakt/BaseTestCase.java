package com.jakewharton.trakt;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
	private final ServiceManager manager = new ServiceManager();
	
	@Override
	public void setUp() {
		manager.setApiKey("de8bc5e9866c77d655310bb0c8ab9a83");
	}
	
	protected final ServiceManager getManager() {
		return manager;
	}
}
