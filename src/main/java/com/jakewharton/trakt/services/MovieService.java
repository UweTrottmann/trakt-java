
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Endpoints for Movie.
 */
public interface MovieService {

    /**
     * Notify trakt that a user wants to cancel their current check in. <br/> <br/>
     * <em>Warning</em>: This method requires a developer API key.
     */
    @POST("/movie/cancelcheckin/{apikey}")
    Response cancelcheckin();

    /**
     * Notify Trakt that a user has stopped watching a movie. <br/> <br/> <em>Warning</em>: This
     * method requires a developer API key.
     */
    @POST("/movie/cancelwatching/{apikey}")
    Response cancelwatching();

    @POST("/movie/seen/{apikey}")
    void seen(
            @Body Movies movies
    );

    @GET("/movie/summary.json/{apikey}/{title}")
    com.jakewharton.trakt.entities.Movie summary(
            @Path("title") String imdbIdOrSlug
    );

    @GET("/movie/summary.json/{apikey}/{title}")
    com.jakewharton.trakt.entities.Movie summary(
            @Path("title") int tmdbId
    );

    public static class Movie {

        public String imdb_id;

        public Integer tmdb_id;

        public String title;

        public Integer year;
    }

    public static class SeenMovie extends Movie {

        public String last_played;

        public SeenMovie(String imdbId) {
            this.imdb_id = imdbId;
        }

        public SeenMovie(int tmdbId) {
            this.tmdb_id = tmdbId;
        }
    }

    public static class Movies {

        public List<SeenMovie> movies;

        public Movies(List<SeenMovie> seenMovies) {
            movies = seenMovies;
        }

        public Movies(SeenMovie seenMovie) {
            movies = new ArrayList<SeenMovie>();
            movies.add(seenMovie);
        }
    }


}
