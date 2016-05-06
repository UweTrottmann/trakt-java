package com.uwetrottmann.trakt.v2;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Test;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
public class AuthTest extends BaseTestCase {

    private static final String TEST_CLIENT_SECRET = "";
    private static final String TEST_AUTH_CODE = "";
    private static final String TEST_REFRESH_TOKEN = "";
    private static final String TEST_REDIRECT_URI = "http://localhost";
    private static final String TEST_USERNAME = "sgtest";

    @Test
    public void test_getAuthorizationRequest() throws OAuthSystemException, URISyntaxException {
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);

        OAuthClientRequest request = TraktV2.getAuthorizationRequest(TEST_CLIENT_ID, TEST_REDIRECT_URI, sampleState);

        assertThat(request).isNotNull();
        assertThat(request.getLocationUri()).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(request.getLocationUri()).doesNotContain("scope");

        System.out.println("Get an auth code at the following URI: " + request.getLocationUri());
    }

    @Test
    public void test_getAccessTokenRequest() throws OAuthSystemException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            System.out.print("Skipping test_getAccessTokenRequest test, no valid auth data");
            return;
        }

        OAuthClientRequest request = TraktV2.getAccessTokenRequest(TEST_CLIENT_ID, TEST_CLIENT_SECRET,
                TEST_REDIRECT_URI, TEST_AUTH_CODE);
        assertThat(request).isNotNull();
        assertThat(request.getLocationUri()).startsWith(TraktV2.OAUTH2_TOKEN_URL);
        System.out.println("Generated Token Request URI: " + request.getLocationUri());
    }

    @Test
    public void test_getAccessToken() throws OAuthProblemException, OAuthSystemException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            System.out.print("Skipping test_getAccessTokenRequest test, no valid auth data");
            return;
        }

        OAuthAccessTokenResponse response = TraktV2.getAccessToken(
                TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI, TEST_AUTH_CODE);
        System.out.println(response.getBody());
        System.out.println("Retrieved access token: " + response.getAccessToken());
        System.out.println("Retrieved refresh token: " + response.getRefreshToken());
        System.out.println("Retrieved scope: " + response.getScope());
        System.out.println("Retrieved expires in: " + response.getExpiresIn() + " seconds");
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getRefreshToken()).isNotEmpty();
    }

    @Test
    public void test_refreshAccessToken() throws OAuthProblemException, OAuthSystemException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_REFRESH_TOKEN.isEmpty()) {
            System.out.print("Skipping test_refreshAccessToken test, no valid auth data");
            return;
        }

        OAuthAccessTokenResponse response = TraktV2.refreshAccessToken(
                TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI, TEST_REFRESH_TOKEN);
        System.out.println(response.getBody());
        System.out.println("Retrieved access token: " + response.getAccessToken());
        System.out.println("Retrieved refresh token: " + response.getRefreshToken());
        System.out.println("Retrieved scope: " + response.getScope());
        System.out.println("Retrieved expires in: " + response.getExpiresIn() + " seconds");
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getRefreshToken()).isNotEmpty();
    }

}
