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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
public class DeviceAuthTest extends BaseTestCase {

    private static final String TEST_CLIENT_SECRET = "";
    private static final String TEST_DEVICE_CODE = "";
    // The Redirect URI is not used in OAuth device authentication.
    // Set the default as the out-of-band URI used during standard OAuth.
    private static final String TEST_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private static TraktV2 trakt;

    @Override
    protected TraktV2 getTrakt() {
        if (trakt == null) {
            trakt = new TestTraktV2(getClientId(), TEST_CLIENT_SECRET, TEST_REDIRECT_URI);
        }
        return trakt;
    }

    @Test
    public void test_generateDeviceCode() throws IOException {
        if (getClientId().isEmpty()) {
            System.out.print("Skipping test_generateDeviceCode test, no valid client id");
            return;
        }

        Response<DeviceCode> codeResponse = getTrakt().generateDeviceCode();
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
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_DEVICE_CODE.isEmpty()) {
            System.out.print("Skipping test_getAccessToken test, no valid auth data");
            return;
        }

        Response<AccessToken> response = getTrakt().exchangeDeviceCodeForAccessToken(TEST_DEVICE_CODE);
        assertAccessTokenResponse(response);
    }

}
