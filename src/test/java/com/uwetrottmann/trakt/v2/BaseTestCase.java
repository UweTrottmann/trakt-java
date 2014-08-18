package com.uwetrottmann.trakt.v2;

import org.junit.BeforeClass;

public class BaseTestCase {

    protected static final String TEST_API_KEY = "e683ed71dd4a4afe73ba73151a4645f511b8703464a7807045088c733ef8d634";
    public static final String TEST_ACCESS_TOKEN = "c2930b671da7dfd9249ddf3e280ca0710a86eef3fd95a46cbacb043a86cb1b20"; // "uwe" on v2 test server

    private static final boolean DEBUG = true;

    private static final TraktV2 trakt = new TraktV2();
    private static final TraktV2 traktWithAuth = new TraktV2();

    @BeforeClass
    public static void setUpOnce() {
        trakt.setApiKey(TEST_API_KEY);
        trakt.setIsDebug(DEBUG);

        traktWithAuth.setApiKey(TEST_API_KEY);
        traktWithAuth.setAccessToken(TEST_ACCESS_TOKEN);
        traktWithAuth.setIsDebug(DEBUG);
    }

    protected final TraktV2 getTrakt() {
        return trakt;
    }

    protected final TraktV2 getTraktWithAuth() {
        return traktWithAuth;
    }

}
