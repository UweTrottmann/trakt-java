package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.SearchResult;
import com.uwetrottmann.trakt.v2.enums.IdType;
import com.uwetrottmann.trakt.v2.enums.Type;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface Search {

    /**
     * Queries will search fields like the title and description.
     *
     * @param query Searches titles and descriptions.
     * @param type (optional) Narrow down search by element type.
     */
    @GET("/search")
    List<SearchResult> textQuery(
            @Query("query") String query,
            @Query("type") Type type
    );

    /**
     * ID lookups are helpful if you have an external ID and want to get the trakt ID and info. This method will search
     * for movies, shows, episodes, people, users, and lists.
     *
     * @param idType Set to any of {@link com.uwetrottmann.trakt.v2.enums.IdType}.
     * @param id ID that matches with the type.
     */
    @GET("/search")
    List<SearchResult> idLookup(
            @EncodedQuery("id_type") IdType idType,
            @EncodedQuery("id") String id
    );

}
