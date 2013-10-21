
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface UserService {

    /**
     * Returns all movies in a user's watchlist. Each movie will indicate when
     * it was added to the watchlist. Protected users won't return any data
     * unless you are friends.
     *
     * @param username You can get a username by browsing the website and
     *                 looking at the URL when on a profile page.
     */
    @GET("/user/watchlist/movies.json/{apikey}/{username}")
    List<Movie> watchlistMovies(
            @Path("username") String username
    );

    /**
     * Returns all shows in a user's watchlist. Each show will indicate when it
     * was added to the watchlist. Protected users won't return any data unless
     * you are friends.
     *
     * @param username You can get a username by browsing the website and
     *                 looking at the URL when on a profile page.
     */
    @GET("/user/watchlist/shows.json/{apikey}/{username}")
    List<TvShow> watchlistShows(
            @Path("username") String username
    );

    /**
     * Returns all shows in a user's library. Each show will indicate how many
     * plays it has. Protected users won't return any data unless you are
     * friends.
     *
     * @param username You can get a username by browsing the website and
     *                 looking at the URL when on a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}")
    List<TvShow> libraryShowsAll(
            @Path("username") String username
    );

    /**
     * Returns complete show info if set to true. Only send this if you really need
     * the full dump as it doubles the data size being sent back.
     *
     * @param username You can get a username by browsing the website and
     *                 looking at the URL when on a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}/extended")
    List<TvShow> libraryShowsAllExtended(
            @Path("username") String username
    );

    /**
     * Returns only the minimal info (title, year, imdb_id, tvdb_id, tvrage_id, plays)
     * required for media center syncing if set to min. This sends about half the data.
     *
     * @param username You can get a username by browsing the website and
     *                 looking at the URL when on a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}/min")
    List<TvShow> libraryShowsAllMinimum(
            @Path("username") String username
    );

}