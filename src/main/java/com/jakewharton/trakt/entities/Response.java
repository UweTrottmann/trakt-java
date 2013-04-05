
package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

public class Response implements TraktEntity {
    private static final long serialVersionUID = 5921890886906816035L;

    public String status; // TODO: enum
    /**
     * Returned if checking in episodes.
     */
    public TvShow show;
    /**
     * Returned if checking in movies.
     */
    public Movie movie;
    public String message;
    public String error;
    public int wait;

}
