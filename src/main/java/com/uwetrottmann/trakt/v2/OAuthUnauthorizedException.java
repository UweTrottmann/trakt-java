package com.uwetrottmann.trakt.v2;

import retrofit.RetrofitError;

/**
 * Thrown if trakt returns HTTP status code 401 Unauthorized, meaning that a valid OAuth access token must be provided.
 * This might also occur if the user has revoked your access token, and you need to authorize your app again.
 */
public class OAuthUnauthorizedException extends Exception {

    public OAuthUnauthorizedException(RetrofitError cause) {
        super("A valid OAuth access token must be provided", cause);
    }

}
