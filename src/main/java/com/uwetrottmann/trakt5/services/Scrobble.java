package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.PlaybackResponse;
import com.uwetrottmann.trakt5.entities.ScrobbleProgress;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Scrobble {
	/**
     * <b>OAuth Required</b>
     *
     * <p> User starts a video
     */
    @POST("scrobble/start")
    Call<PlaybackResponse> startWatching(
            @Body ScrobbleProgress prog
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> User pauses a video
     */
    @POST("scrobble/pause")
    Call<PlaybackResponse> pauseWatching(
            @Body ScrobbleProgress prog
    );

	/**
     * <b>OAuth Required</b>
     *
     * <p> User stops a video
     */
    @POST("scrobble/stop")
    Call<PlaybackResponse> stopWatching(
            @Body ScrobbleProgress prog
    );

}
