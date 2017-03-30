package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
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

    private static final TraktV2 trakt = new TestTraktV2(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI);

    @Override
    protected TraktV2 getTrakt() {
        return trakt;
    }

    @Test
    public void test_getAuthorizationRequest() {
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);

        String authUrl = getTrakt().buildAuthorizationUrl(sampleState);

        assertThat(authUrl).isNotEmpty();
        assertThat(authUrl).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(authUrl).doesNotContain("scope");

        System.out.println("Get an auth code at the following URI: " + authUrl);
    }

    @Test
    public void test_getAccessToken() throws IOException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            System.out.print("Skipping test_getAccessTokenRequest test, no valid auth data");
            return;
        }

        Response<AccessToken> response = getTrakt().exchangeCodeForAccessToken(TEST_AUTH_CODE);
        assertAccessTokenResponse(response);
    }

    @Test
    public void test_refreshAccessToken() throws IOException {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_REFRESH_TOKEN.isEmpty()) {
            System.out.print("Skipping test_refreshAccessToken test, no valid auth data");
            return;
        }

        Response<AccessToken> response = getTrakt().refreshAccessToken();
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
