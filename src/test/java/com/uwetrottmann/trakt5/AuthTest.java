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
 * The access and refresh token tests require a {@code TEST_AUTH_CODE} with either {@code TEST_CLIENT_SECRET} or
 * {@code TEST_CODE_VERIFIER} (for PKCE) to be set as environment variables or in secrets.properties. Alternatively, a
 * {@code TEST_REFRESH_TOKEN}. See the tests for details.
 * <p>
 * This can also be used to build an authorization URL ({@link #test_getAuthorizationRequest()} using a client secret,
 * or {@link #test_getAuthorizationRequestPkce()} using PKCE) to obtain an auth code to then obtain an access token
 * ({@link #test_getAccessToken()} or {@link #test_getAccessTokenPkce()}) to use as {@code TEST_ACCESS_TOKEN}.
 */
public class AuthTest extends BaseTestCase {

    private static final String TEST_REDIRECT_URI = "http://localhost";

    @Test
    public void test_getAuthorizationRequest() {
        // client secret not required to build URL
        TestTraktV2 trakt = new TestTraktV2(getClientId(), null, TEST_REDIRECT_URI);
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);

        String authUrl = trakt.buildAuthorizationUrl(sampleState);

        assertThat(authUrl).isNotEmpty();
        assertThat(authUrl).isEqualTo(
                TraktV2.OAUTH2_AUTHORIZATION_URL + "?response_type=code" +
                        "&client_id=" + getClientId() +
                        "&redirect_uri=http%3A%2F%2Flocalhost" +
                        "&state=" + sampleState);
        // trakt does not support scopes, so don't send one (server sets default scope)
        assertThat(authUrl).doesNotContain("scope");

        System.out.println("Get an auth code at the following URI: " + authUrl);
    }

    @Test
    public void test_getAuthorizationRequestPkce() {
        // client secret not required for PKCE
        TestTraktV2 trakt = new TestTraktV2(getClientId(), null, TEST_REDIRECT_URI);
        String sampleState = new BigInteger(130, new SecureRandom()).toString(32);
        String codeVerifier = TraktV2.generateCodeVerifier();

        String authUrl = trakt.buildAuthorizationUrl(sampleState, codeVerifier);

        assertThat(authUrl).isEqualTo(
                TraktV2.OAUTH2_AUTHORIZATION_URL + "?response_type=code" +
                        "&client_id=" + getClientId() +
                        "&redirect_uri=http%3A%2F%2Flocalhost" +
                        "&state=" + sampleState +
                        "&code_challenge=" + TraktV2.codeChallengeS256(codeVerifier) +
                        "&code_challenge_method=S256");

        System.out.println("Code verifier: " + codeVerifier);
        System.out.println("Get an auth code using PKCE at the following URI: " + authUrl);
    }

    @Test
    public void test_generateCodeVerifier() {
        String codeVerifier = TraktV2.generateCodeVerifier();
        // 43-128 characters of [A-Z] / [a-z] / [0-9] / "-" / "." / "_" / "~"
        assertThat(codeVerifier).matches("[A-Za-z0-9\\-._~]{43,128}");
        assertThat(TraktV2.generateCodeVerifier()).isNotEqualTo(codeVerifier);
    }

    @Test
    public void test_codeChallengeS256() {
        // Example from RFC 7636 Appendix B
        assertThat(TraktV2.codeChallengeS256("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"))
                .isEqualTo("E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM");
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
    public void test_getAccessTokenPkce() throws IOException {
        Properties secrets = tryToloadSecrets();
        String authCode = getVarFromEnvOrProperties(secrets, "TEST_AUTH_CODE");
        String codeVerifier = getVarFromEnvOrProperties(secrets, "TEST_CODE_VERIFIER");
        boolean hasAuthCode = authCode != null && !authCode.isEmpty()
                && codeVerifier != null && !codeVerifier.isEmpty();
        assumeTrue(
                "TEST_AUTH_CODE and TEST_CODE_VERIFIER must be set via environment variable or secrets.properties file",
                hasAuthCode);

        // client secret not required for PKCE
        TestTraktV2 trakt = new TestTraktV2(getClientId(), null, TEST_REDIRECT_URI);

        Response<AccessToken> response = trakt.exchangeCodeForAccessToken(authCode, codeVerifier);
        assertAccessTokenResponse(response);
    }

    @Test
    public void test_refreshAccessToken() throws IOException {
        Properties secrets = tryToloadSecrets();
        String refreshToken = getVarFromEnvOrProperties(secrets, "TEST_REFRESH_TOKEN");
        boolean hasRefreshToken = refreshToken != null && !refreshToken.isEmpty();
        assumeTrue("TEST_REFRESH_TOKEN must be set via environment variable or secrets.properties file",
                hasRefreshToken);

        TestTraktV2 trakt = new TestTraktV2(getClientId());

        Response<AccessToken> response = trakt.refreshAccessToken(refreshToken);
        assertAccessTokenResponse(response);
    }

}
