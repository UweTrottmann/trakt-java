package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;
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

    @Test
    public void test_getAuthorizationRequest() throws OAuthSystemException {
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);

        OAuthClientRequest request = getTrakt().buildAuthorizationRequest(TEST_REDIRECT_URI, sampleState);

        assertThat(request).isNotNull();
        assertThat(request.getLocationUri()).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(request.getLocationUri()).doesNotContain("scope");

        System.out.println("Get an auth code at the following URI: " + request.getLocationUri());
    }

    @Test
    public void test_getAccessToken() throws IOException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            System.out.print("Skipping test_getAccessTokenRequest test, no valid auth data");
            return;
        }

        Response<AccessToken> response = getTrakt().exchangeCodeForAccessToken(TEST_CLIENT_SECRET,
                TEST_REDIRECT_URI, TEST_AUTH_CODE);
        assertAccessTokenResponse(response);
    }

    @Test
    public void test_refreshAccessToken() throws IOException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_REFRESH_TOKEN.isEmpty()) {
            System.out.print("Skipping test_refreshAccessToken test, no valid auth data");
            return;
        }

        Response<AccessToken> response = getTrakt().refreshAccessToken(TEST_CLIENT_SECRET,
                TEST_REDIRECT_URI, TEST_REFRESH_TOKEN);
        assertAccessTokenResponse(response);
    }

    private void assertAccessTokenResponse(Response<AccessToken> response) {
        assertSuccessfulResponse(response);
        assertThat(response.body().access_token).isNotEmpty();
        assertThat(response.body().refresh_token).isNotEmpty();

        System.out.println("Retrieved access token: " + response.body().access_token);
        System.out.println("Retrieved refresh token: " + response.body().refresh_token);
        System.out.println("Retrieved scope: " + response.body().scope);
        System.out.println("Retrieved expires in: " + response.body().expires_in + " seconds");
    }

}
