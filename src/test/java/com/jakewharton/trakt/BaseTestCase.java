package com.jakewharton.trakt;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
    protected static final String API_KEY = "5abdaea0246b840cb7c709f8e1788fed";

    private final ServiceManager manager = new ServiceManager();

    @Override
    public void setUp() {
        manager.setApiKey(API_KEY);
        manager.setAuthentication("sgtest", "2a4d398c09ec9c6915d1f46710ceed9673fa4e3d");
    }

    protected final ServiceManager getManager() {
        return manager;
    }
}
