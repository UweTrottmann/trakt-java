package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.Person;
import com.uwetrottmann.trakt.v2.enums.Extended;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface People {

    /**
     * Returns a single person's details.
     *
     * @param personId trakt ID, trakt slug, or IMDB ID Example: bryan-cranston.
     */
    @GET("/people/{id}")
    Person summary(
            @Path("id") String personId,
            @Query("extended") Extended extended
    );

    @GET("/people/{id}/movies")
    Credits movieCredits(
            @Path("id") String personId
    );

    @GET("/people/{id}/shows")
    Credits showCredits(
            @Path("id") String personId
    );

}
