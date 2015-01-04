package com.uwetrottmann.trakt.v2;

import com.uwetrottmann.trakt.v2.exceptions.CheckinInProgressException;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Intercepts HTTP 401 Unauthorized responses and wraps the {@link retrofit.RetrofitError} inside a {@link
 * com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException}.
 *
 * <p> Intercepts HTTP 409 Conflict responses and wraps the {@link retrofit.RetrofitError} inside a {@link
 * com.uwetrottmann.trakt.v2.exceptions.CheckinInProgressException}.
 */
public class TraktErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null) {
            if (r.getStatus() == 401) {
                return new OAuthUnauthorizedException(cause);
            }
            if (r.getUrl().endsWith("/checkin") && r.getStatus() == 409) {
                return new CheckinInProgressException(cause);
            }
        }
        return cause;
    }

}
