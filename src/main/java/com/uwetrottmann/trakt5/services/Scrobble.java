package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.GenericProgress;

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
    Call<Void> startWatching(
            @Body GenericProgress prog
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> User pauses a video
     */
    @POST("scrobble/pause")
    Call<Void> pauseWatching(
            @Body GenericProgress prog
    );

	/**
     * <b>OAuth Required</b>
     *
     * <p> User stops a video
     */
    @POST("scrobble/stop")
    Call<Void> stopWatching(
            @Body GenericProgress prog
    );

}
