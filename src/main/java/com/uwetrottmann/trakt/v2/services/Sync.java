package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.annotations.DELETE;
import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.CollectedShow;
import com.uwetrottmann.trakt.v2.entities.SyncEntity;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

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

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add one or more items to a user's collection including the format of the item.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @POST("/sync/collection")
    SyncResponse addItemsToCollection(
            @Body SyncEntity items
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Remove one or more items from a user's collection.
     *
     * @param items A list of movies, shows, seasons or episodes.
     */
    @DELETE("/sync/collection")
    SyncResponse deleteItemsFromCollection(
            @Body SyncEntity items
    );

}
