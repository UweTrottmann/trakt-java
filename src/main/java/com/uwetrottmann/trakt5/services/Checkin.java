package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.EpisodeCheckin;
import com.uwetrottmann.trakt5.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt5.entities.MovieCheckin;
import com.uwetrottmann.trakt5.entities.MovieCheckinResponse;
import com.uwetrottmann.trakt5.exceptions.CheckinInProgressException;
import com.uwetrottmann.trakt5.exceptions.OAuthUnauthorizedException;
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
     */
    @POST("/checkin")
    EpisodeCheckinResponse checkin(
            @Body EpisodeCheckin episodeCheckin
    ) throws OAuthUnauthorizedException, CheckinInProgressException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Check into a movie. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("/checkin")
    MovieCheckinResponse checkin(
            @Body MovieCheckin movieCheckin
    ) throws OAuthUnauthorizedException, CheckinInProgressException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Removes any active checkins, no need to provide a specific item.
     */
    @DELETE("/checkin")
    Response deleteActiveCheckin() throws OAuthUnauthorizedException;

}
