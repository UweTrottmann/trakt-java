package com.uwetrottmann.trakt5.entities;

import retrofit2.Response;

import java.util.Date;

/**
 * Type to use for parsing check in error response (call {@link com.uwetrottmann.trakt5.TraktV2#checkForCheckinError(Response)}
 * with this class) if you get a 409 HTTP status code when checking in.
 */
public class CheckinError {

    /** Timestamp which is when the user can check in again. */
    public Date expires_at;

}
