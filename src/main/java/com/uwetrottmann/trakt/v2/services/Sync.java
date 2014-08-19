package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.CollectedShow;
import retrofit.http.GET;

import java.util.List;

public interface Sync {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected movies in a user's collection. A collected item indicates availability to watch digitally
     * or on physical media.
     */
    @GET("/sync/collection/movies")
    List<CollectedMovie> getCollectionMovies();

    /**
     * <b>OAuth Required</b>
     *
     * <p> Get all collected shows in a user's collection. A collected item indicates availability to watch digitally or
     * on physical media.
     */
    @GET("/sync/collection/shows")
    List<CollectedShow> getCollectionShows();

}
