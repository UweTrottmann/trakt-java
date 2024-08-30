/*
 * Copyright 2016-2024 Uwe Trottmann
 * Copyright 2020 srggsf
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
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * If required tries to obtain a new access and refresh tokens if a refresh token is available.
 * If so, replaces the existing tokens and updates the authentication header of the request.
 * See {@link #handleAuthenticate(Response, TraktV2)}.
 */
public class TraktV2Authenticator implements Authenticator {

    public final TraktV2 trakt;

    public TraktV2Authenticator(TraktV2 trakt) {
        this.trakt = trakt;
    }

    @Override
    @Nullable
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        return handleAuthenticate(response, trakt);
    }

    /**
     * If not doing a Trakt {@link TraktV2#API_URL} request tries to refresh the access token with the refresh token.
     *
     * @param response The response passed to {@link #authenticate(Route, Response)}.
     * @param trakt The {@link TraktV2} instance to get the API key from and to set the updated JSON web token on.
     * @return A request with updated authorization header or null if no auth is possible.
     */
    @Nullable
    public static Request handleAuthenticate(Response response, TraktV2 trakt) throws IOException {
        if (!trakt.apiHost().equals(response.request().url().host())) {
            return null; // not a Trakt API endpoint (possibly Trakt OAuth or other API), give up.
        }
        if (responseCount(response) >= 2) {
            return null; // failed 2 times, give up.
        }
        String refreshToken = trakt.refreshToken();
        if (refreshToken == null || refreshToken.length() == 0) {
            return null; // have no refresh token, give up.
        }

        // try to refresh the access token with the refresh token
        retrofit2.Response<AccessToken> refreshResponse = trakt.refreshAccessToken(refreshToken);
        AccessToken body = refreshResponse.body();
        if (!refreshResponse.isSuccessful() || body == null) {
            return null; // failed to retrieve a token, give up.
        }

        // store the new tokens
        String accessToken = body.access_token;
        trakt.accessToken(accessToken);
        trakt.refreshToken(body.refresh_token);

        // retry request
        return response.request().newBuilder()
                .header(TraktV2.HEADER_AUTHORIZATION, "Bearer" + " " + accessToken)
                .build();
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}
