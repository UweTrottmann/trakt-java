/*
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
import com.uwetrottmann.trakt5.entities.DeviceCode;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * This test requires {@code TEST_CLIENT_SECRET} and {@code TEST_DEVICE_CODE} to be set as environment variables or
 * in secrets.properties. See the tests for details.
 */
public class DeviceAuthTest extends BaseTestCase {

    // The Redirect URI is not used in OAuth device authentication.
    // Set the default as the out-of-band URI used during standard OAuth.
    private static final String TEST_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    @Test
    public void test_generateDeviceCode() throws IOException {
        Properties secrets = tryToloadSecrets();
        String clientSecret = getClientSecretFromEnvOrPropertiesOrIgnoreTest(secrets);

        TestTraktV2 trakt = new TestTraktV2(getClientId(), clientSecret, TEST_REDIRECT_URI);

        Response<DeviceCode> codeResponse = trakt.generateDeviceCode();
        assertSuccessfulResponse(codeResponse);
        DeviceCode deviceCode = codeResponse.body();
        assertThat(deviceCode.device_code).isNotEmpty();
        assertThat(deviceCode.user_code).isNotEmpty();
        assertThat(deviceCode.verification_url).isNotEmpty();
        assertThat(deviceCode.expires_in).isPositive();
        assertThat(deviceCode.interval).isPositive();

        System.out.println("Device Code: " + deviceCode.device_code);
        System.out.println("User Code: " + deviceCode.user_code);
        System.out.println("Enter the user code at the following URI: " + deviceCode.verification_url);
        System.out.println("Set the TEST_DEVICE_CODE variable to run the access token test");
    }

    @Test
    public void test_getAccessToken() throws IOException {
        Properties secrets = tryToloadSecrets();
        String clientSecret = getClientSecretFromEnvOrPropertiesOrIgnoreTest(secrets);
        TestTraktV2 trakt = new TestTraktV2(getClientId(), clientSecret, TEST_REDIRECT_URI);

        String deviceCode = getVarFromEnvOrProperties(secrets, "TEST_DEVICE_CODE");
        boolean hasDeviceCode = deviceCode != null && !deviceCode.isEmpty();
        assumeTrue("TEST_DEVICE_CODE must be set via environment variable or secrets.properties file", hasDeviceCode);

        Response<AccessToken> response = trakt.exchangeDeviceCodeForAccessToken(deviceCode);
        assertAccessTokenResponse(response);
    }

}
