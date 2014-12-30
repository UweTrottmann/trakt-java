package com.uwetrottmann.trakt.v2.exceptions;

import com.uwetrottmann.trakt.v2.entities.CheckinError;
import org.joda.time.DateTime;
import retrofit.RetrofitError;

/**
 * Thrown if trakt returns HTTP status code 409 Conflict, meaning that another check-in is already in progress. You can
 * either cancel the existing check-in, or wait until the given time and try again.
 */
public class CheckinInProgressException extends Exception {

    private final DateTime expiresAt;

    public CheckinInProgressException(RetrofitError cause) {
        super("A checkin is already in progress", cause);

        Object body = cause.getBodyAs(CheckinError.class);
        if (body != null) {
            CheckinError error = (CheckinError) body;
            expiresAt = error.expires_at;
        } else {
            expiresAt = null;
        }
    }

    /**
     * Time, when the user can check in again.
     */
    public DateTime getExpiresAt() {
        return expiresAt;
    }
}
