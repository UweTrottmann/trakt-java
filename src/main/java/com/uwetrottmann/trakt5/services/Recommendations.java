package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.exceptions.OAuthUnauthorizedException;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface Recommendations {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Personalized movie recommendations for a user. Results returned with the top recommendation first.
     */
    @GET("/recommendations/movies")
    List<Movie> movies(
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Dismiss a movie from getting recommended anymore.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @DELETE("/recommendations/movies/{id}")
    Response dismissMovie(
            @Path("id") String movieId
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Personalized show recommendations for a user. Results returned with the top recommendation first.
     */
    @GET("/recommendations/shows")
    List<Show> shows(
            @Query(value = "extended", encodeValue = false) Extended extended
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Required</b>
     *
     * <p> Dismiss a show from getting recommended anymore.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: 922.
     */
    @DELETE("/recommendations/shows/{id}")
    Response dismissShow(
            @Path("id") String showId
    ) throws OAuthUnauthorizedException;

}
