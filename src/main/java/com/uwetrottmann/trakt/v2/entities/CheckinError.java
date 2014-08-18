package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;

/**
 * Type to use for parsing check in error response (call {@link retrofit.RetrofitError#getBodyAs(java.lang.reflect.Type)}
 * with this class) if you get a 409 HTTP status code when checking in.
 */
public class CheckinError {

    /** Timestamp which is when the user can check in again. */
    public Date expires_at;

}
