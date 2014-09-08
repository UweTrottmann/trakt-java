package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.SearchResult;
import com.uwetrottmann.trakt.v2.enums.IdType;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface Search {

    @GET("/search")
    List<SearchResult> textQuery(
            @Query("query") String query
    );

    @GET("/search")
    List<SearchResult> idLookup(
            @EncodedQuery("id_type") IdType idType,
            @EncodedQuery("id") String id
    );

}
