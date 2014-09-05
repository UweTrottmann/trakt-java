package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.OAuthUnauthorizedException;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckin;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt.v2.entities.MovieCheckin;
import com.uwetrottmann.trakt.v2.entities.MovieCheckinResponse;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.POST;

public interface Checkin {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Check into an episode. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     *
     * <p> <b>Note:</b> If a checkin is already in progress, a 409 HTTP status code will returned. The response will
     * contain an expires_at timestamp which is when the user can check in again.
     */
    @POST("/checkin")
    EpisodeCheckinResponse checkin(
            @Body EpisodeCheckin episodeCheckin
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Check into a movie. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     *
     * <p> <b>Note:</b> If a checkin is already in progress, a 409 HTTP status code will returned. The response will
     * contain an expires_at timestamp which is when the user can check in again.
     */
    @POST("/checkin")
    MovieCheckinResponse checkin(
            @Body MovieCheckin movieCheckin
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Removes any active checkins, no need to provide a specific item.
     */
    @DELETE("/checkin")
    Response deleteActiveCheckin() throws OAuthUnauthorizedException;

}
