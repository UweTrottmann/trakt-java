package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.SearchResult;
import com.uwetrottmann.trakt5.enums.IdType;
import com.uwetrottmann.trakt5.enums.Type;
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
            @Query("type") Type type,
            @Query("year") Integer year,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

    /**
     * ID lookups are helpful if you have an external ID and want to get the trakt ID and info. This method will search
     * for movies, shows, episodes, people, users, and lists.
     *
     * @param idType Set to any of {@link IdType}.
     * @param id ID that matches with the type.
     */
    @GET("/search")
    List<SearchResult> idLookup(
            @Query(value = "id_type", encodeValue = false) IdType idType,
            @Query(value = "id", encodeValue = false) String id,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );

}
