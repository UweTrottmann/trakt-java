package com.jakewharton.trakt;

import junit.framework.TestCase;

public abstract class BaseTestCase extends TestCase {
    protected static final String API_KEY = "5abdaea0246b840cb7c709f8e1788fed";
    public static final String USERNAME = "sgtest";
    public static final String PASSWORD_SHA_1 = "2a4d398c09ec9c6915d1f46710ceed9673fa4e3d";

    private final Trakt manager = new Trakt();

    @Override
    public void setUp() {
        manager.setApiKey(API_KEY);
        manager.setAuthentication(USERNAME, PASSWORD_SHA_1);
        manager.setIsDebug(true);
    }

    protected final Trakt getManager() {
        return manager;
    }
}
