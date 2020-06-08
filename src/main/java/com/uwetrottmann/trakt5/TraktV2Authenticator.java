package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import javax.annotation.Nullable;
import java.io.IOException;

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
     * If not doing a trakt {@link TraktV2#API_URL} request tries to refresh the access token with the refresh token.
     *
     * @param response The response passed to {@link #authenticate(Route, Response)}.
     * @param trakt The {@link TraktV2} instance to get the API key from and to set the updated JSON web token on.
     * @return A request with updated authorization header or null if no auth is possible.
     */
    @Nullable
    public static Request handleAuthenticate(Response response, TraktV2 trakt) throws IOException {
        if (!trakt.apiHost().equals(response.request().url().host())) {
            return null; // not a trakt API endpoint (possibly trakt OAuth or other API), give up.
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
