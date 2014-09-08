package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Show;
import com.uwetrottmann.trakt.v2.entities.Translation;
import com.uwetrottmann.trakt.v2.entities.TrendingShow;
import com.uwetrottmann.trakt.v2.enums.Extended;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface Shows {

    /**
     * Returns the most popular shows. Popularity is calculated using the rating percentage and the number of ratings.
     */
    @GET("/shows/popular")
    List<Show> popular();

    /**
     * Returns all shows being watched right now. Shows with the most users are returned first.
     */
    @GET("/shows/trending")
    List<TrendingShow> trending();

    /**
     * Returns a single shows's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}")
    Show summary(
            @Path("id") String showId,
            @EncodedQuery("extended") Extended extended
    );

    /**
     * Returns all translations for a show, including language and translated values for title and overview.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}/translations")
    List<Translation> translations(
            @Path("id") String showId
    );

    /**
     * Returns a single translation for a show. If the translation does not exist, the returned list will be empty.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("/shows/{id}/translations/{language}")
    List<Translation> translation(
            @Path("id") String showId,
            @Path("language") String language
    );

    /**
     * Returns all top level comments for a show. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("/shows/{id}/comments")
    List<Comment> comments(
            @Path("id") String showId
    );

}
