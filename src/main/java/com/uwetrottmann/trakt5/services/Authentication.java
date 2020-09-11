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
