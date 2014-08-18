package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.EpisodeCheckin;
import com.uwetrottmann.trakt.v2.entities.MovieCheckin;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;

public interface Checkin {

    /**
     * Check into an episode. This should be tied to a user action to manually indicate they are watching something. The
     * item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     *
     * <p> <b>Note:</b> If a checkin is already in progress, a 409 HTTP status code will returned. The response will
     * contain an expires_at timestamp which is when the user can check in again.
     *
     * @return
     */
    @POST("/checkin")
    Response checkin(
            @Body EpisodeCheckin episodeCheckin
    );

    /**
     * Check into a movie. This should be tied to a user action to manually indicate they are watching something. The
     * item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     *
     * <p> <b>Note:</b> If a checkin is already in progress, a 409 HTTP status code will returned. The response will
     * contain an expires_at timestamp which is when the user can check in again.
     *
     * @return
     */
    @POST("/checkin")
    Response checkin(
            @Body MovieCheckin movieCheckin
    );

    /**
     * Removes any active checkins, no need to provide a specific item.
     *
     * @return
     */
    @DELETE("/checkin")
    Response deleteActiveCheckin();

}
