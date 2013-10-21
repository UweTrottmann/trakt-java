
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Response;
import retrofit.http.POST;

/**
 * Endpoints for Movie.
 */
public interface MovieService {

    /**
     * Notify trakt that a user wants to cancel their current check in. <br/>
     * <br/>
     * <em>Warning</em>: This method requires a developer API key.
     */
    @POST("/movie/cancelcheckin/{apikey}")
    Response cancelcheckin();

    /**
     * Notify Trakt that a user has stopped watching a movie. <br/>
     * <br/>
     * <em>Warning</em>: This method requires a developer API key.
     */
    @POST("/movie/cancelwatching/{apikey}")
    Response cancelwatching();

}
