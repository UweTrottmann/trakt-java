package com.uwetrottmann.trakt.v2;

import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Test;

public class AuthTest extends BaseTestCase {

    public static final String TEST_CLIENT_ID = "e683ed71dd4a4afe73ba73151a4645f511b8703464a7807045088c733ef8d634";
    public static final String TEST_CLIENT_SECRET = "21da158feb52479c53936a48b13e4abe94b907908387d47b70710deb2f4a51fa";
    private static final String AUTH_CODE = "";

    @Test
    public void test_getAccessToken() throws OAuthProblemException, OAuthSystemException {
        if (AUTH_CODE.length() == 0) {
            throw new IllegalArgumentException(
                    "Make sure you set a temporary auth code to exchange for an access token");
        }
        OAuthAccessTokenResponse response = TraktV2.getAccessToken(
                TEST_CLIENT_ID, TEST_CLIENT_SECRET, "http://localhost", AUTH_CODE);
        System.out.println("Retrieved access token: " + response.getAccessToken());
    }

}
