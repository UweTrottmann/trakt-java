package com.uwetrottmann.trakt.v2;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
public class AuthTest extends BaseTestCase {

    public static final String TEST_CLIENT_ID = "e683ed71dd4a4afe73ba73151a4645f511b8703464a7807045088c733ef8d634";
    public static final String TEST_CLIENT_SECRET = "21da158feb52479c53936a48b13e4abe94b907908387d47b70710deb2f4a51fa";
    private static final String AUTH_CODE = "";
    public static final String TEST_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    @Test
    public void test_getAccessTokenRequest() throws OAuthSystemException {
        OAuthClientRequest request = TraktV2.getAccessTokenRequest(TEST_CLIENT_ID, TEST_CLIENT_SECRET,
                TEST_REDIRECT_URI, AUTH_CODE);
        assertThat(request).isNotNull();
        assertThat(request.getLocationUri()).startsWith(TraktV2.OAUTH2_TOKEN_URL);
        System.out.println("Generated Token Request URI: " + request.getLocationUri());
    }

    @Test
    public void test_getAccessToken() throws OAuthProblemException, OAuthSystemException {
        if (AUTH_CODE.length() == 0) {
            throw new IllegalArgumentException(
                    "Make sure you set a temporary auth code to exchange for an access token");
        }
        OAuthAccessTokenResponse response = TraktV2.getAccessToken(
                TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI, AUTH_CODE);
        assertThat(response.getAccessToken()).isNotEmpty();
        assertThat(response.getRefreshToken()).isNull(); // trakt does not supply refresh tokens
        System.out.println("Retrieved access token: " + response.getAccessToken());
        System.out.println("Retrieved refresh token: " + response.getRefreshToken());
        System.out.println("Retrieved scope: " + response.getScope());
        System.out.println("Retrieved expires in: " + response.getExpiresIn());
    }

    @Test
    public void test_getAuthorizationRequest() throws OAuthSystemException, URISyntaxException {
        OAuthClientRequest request = TraktV2.getAuthorizationRequest(TEST_CLIENT_ID, TEST_REDIRECT_URI);
        assertThat(request).isNotNull();
        assertThat(request.getLocationUri()).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(request.getLocationUri()).doesNotContain("scope");
        System.out.println("Get an auth code at the following URI: " + request.getLocationUri());
    }

}
