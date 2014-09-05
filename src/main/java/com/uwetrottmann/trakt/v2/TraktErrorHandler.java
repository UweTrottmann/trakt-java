package com.uwetrottmann.trakt.v2;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Intercepts HTTP 401 Unauthorized responses and if so wraps the {@link retrofit.RetrofitError} inside a {@link
 * com.uwetrottmann.trakt.v2.OAuthUnauthorizedException}.
 */
public class TraktErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            return new OAuthUnauthorizedException(cause);
        }
        return cause;
    }

}
