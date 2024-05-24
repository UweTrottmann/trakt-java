/*
 * Copyright 2024 Uwe Trottmann
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

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.AccessToken;
import com.uwetrottmann.trakt5.entities.AccessTokenRefreshRequest;
import com.uwetrottmann.trakt5.entities.AccessTokenRequest;
import com.uwetrottmann.trakt5.entities.ClientId;
import com.uwetrottmann.trakt5.entities.DeviceCode;
import com.uwetrottmann.trakt5.entities.DeviceCodeAccessTokenRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Authentication {

    @POST("oauth/token")
    Call<AccessToken> exchangeCodeForAccessToken(
            @Body AccessTokenRequest tokenRequest
    );

    @POST("oauth/token")
    Call<AccessToken> refreshAccessToken(
            @Body AccessTokenRefreshRequest refreshRequest
    );

    /**
     * Generate new codes to start the device authentication process.
     * The {@code device_code} and {@code interval} will be used later to poll for the {@code access_token}.
     * The {@code user_code} and {@code verification_url} should be presented to the user.
     * @param clientId Application Client Id
     */
    @POST("oauth/device/code")
    Call<DeviceCode> generateDeviceCode(
            @Body ClientId clientId
    );

    /**
     * Use the {@code device_code} and poll at the {@code interval} (in seconds) to check if the user has
     * authorized you app. Use {@code expires_in} to stop polling after that many seconds, and gracefully
     * instruct the user to restart the process.
     * <b>It is important to poll at the correct interval and also stop polling when expired.</b>
     *
     * When you receive a {@code 200} success response, save the {@code access_token} so your app can
     * authenticate the user in methods that require it. The {@code access_token} is valid for 3 months.
     * Save and use the {@code refresh_token} to get a new {@code access_token} without asking the user
     * to re-authenticate.
     * @param deviceCodeAccessTokenRequest Device Code
     */
    @POST("oauth/device/token")
    Call<AccessToken> exchangeDeviceCodeForAccessToken(
            @Body DeviceCodeAccessTokenRequest deviceCodeAccessTokenRequest
    );

}
