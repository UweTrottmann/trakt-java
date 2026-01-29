/*
 * Copyright 2014-2024 Uwe Trottmann
 * Copyright 2020 Sam Malone
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * The access and refresh token tests require {@code TEST_CLIENT_SECRET} and either {@code TEST_AUTH_CODE} or
 * {@code TEST_REFRESH_TOKEN} to be set as environment variables or in secrets.properties. See the tests for details.
 * <p>
 * It can also be used to build an authorization URL ({@link #test_getAuthorizationRequest()}) to obtain an auth code to
 * then obtain an access token ({@link #test_getAccessToken()}) to use as {@code TEST_ACCESS_TOKEN}.
 */
public class AuthTest extends BaseTestCase {

    private static final String TEST_REDIRECT_URI = "http://localhost";

    @Test
    public void test_getAuthorizationRequest() {
        // client secret not required for this test
        TestTraktV2 trakt = new TestTraktV2(getClientId(), null, TEST_REDIRECT_URI);
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);

        String authUrl = trakt.buildAuthorizationUrl(sampleState);

        assertThat(authUrl).isNotEmpty();
        assertThat(authUrl).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(authUrl).doesNotContain("scope");

        System.out.println("Get an auth code at the following URI: " + authUrl);
    }

    @Test
    public void test_getAccessToken() throws IOException {
        Properties secrets = tryToloadSecrets();
        String clientSecret = getClientSecretFromEnvOrPropertiesOrIgnoreTest(secrets);
        String authCode = getVarFromEnvOrProperties(secrets, "TEST_AUTH_CODE");
        boolean hasAuthCode = authCode != null && !authCode.isEmpty();
        assumeTrue("TEST_AUTH_CODE must be set via environment variable or secrets.properties file", hasAuthCode);

        TestTraktV2 trakt = new TestTraktV2(getClientId(), clientSecret, TEST_REDIRECT_URI);

        Response<AccessToken> response = trakt.exchangeCodeForAccessToken(authCode);
        assertAccessTokenResponse(response);
    }

    @Test
    public void test_refreshAccessToken() throws IOException {
        Properties secrets = tryToloadSecrets();
        String clientSecret = getClientSecretFromEnvOrPropertiesOrIgnoreTest(secrets);
        String refreshToken = getVarFromEnvOrProperties(secrets, "TEST_REFRESH_TOKEN");
        boolean hasRefreshToken = refreshToken != null && !refreshToken.isEmpty();
        assumeTrue("TEST_REFRESH_TOKEN must be set via environment variable or secrets.properties file",
                hasRefreshToken);

        TestTraktV2 trakt = new TestTraktV2(getClientId(), clientSecret, TEST_REDIRECT_URI);

        Response<AccessToken> response = trakt.refreshAccessToken(refreshToken);
        assertAccessTokenResponse(response);
    }

}
