package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

/**
 * Type to use for parsing check in error response (call {@link retrofit.RetrofitError#getBodyAs(java.lang.reflect.Type)}
 * with this class) if you get a 409 HTTP status code when checking in.
 */
public class CheckinError {

    /** Timestamp which is when the user can check in again. */
    public DateTime expires_at;

}
