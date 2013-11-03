package com.jakewharton.trakt;

import org.junit.BeforeClass;

public abstract class BaseTestCase {

    public static final String USERNAME = "sgtest";

    public static final String PASSWORD_SHA_1 = "2a4d398c09ec9c6915d1f46710ceed9673fa4e3d";

    protected static final String API_KEY = "5abdaea0246b840cb7c709f8e1788fed";

    private static final boolean DEBUG = false;

    private static final Trakt sTrakt = new Trakt();

    @BeforeClass
    public static void setUpOnce() {
        sTrakt.setApiKey(API_KEY);
        sTrakt.setAuthentication(USERNAME, PASSWORD_SHA_1);
        sTrakt.setIsDebug(DEBUG);
    }

    protected final Trakt getManager() {
        return sTrakt;
    }
}
