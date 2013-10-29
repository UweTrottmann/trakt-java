
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.UserProfile;

import java.util.List;

import retrofit.http.EncodedPath;
import retrofit.http.GET;

public interface UserService {

    /**
     * Returns an array of all followers including the since timestamp which is when the
     * relationship began. Protected users won't return any data unless you are friends. Any friends
     * of the main user that are protected won't display data either.
     */
    @GET("/user/network/followers.json/{apikey}/{username}")
    List<UserProfile> followers(
            @EncodedPath("username") String username
    );

    /**
     * Returns an array of all user's they follow including the since timestamp which is when the
     * relationship began. Protected users won't return any data unless you are friends. Any friends
     * of the main user that are protected won't display data either.
     */
    @GET("/user/network/following.json/{apikey}/{username}")
    List<UserProfile> following(
            @EncodedPath("username") String username
    );

    /**
     * Returns an array of the user's friends (a 2 way relationship where each user follows the
     * other) including the since timestamp which is when the friendship began. Protected users
     * won't return any data unless you are friends. Any friends of the main user that are protected
     * won't display data either.
     */
    @GET("/user/network/friends.json/{apikey}/{username}")
    List<UserProfile> friends(
            @EncodedPath("username") String username
    );

    /**
     * Returns all movies in a user's watchlist. Each movie will indicate when it was added to the
     * watchlist. Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     */
    @GET("/user/watchlist/movies.json/{apikey}/{username}")
    List<Movie> watchlistMovies(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows in a user's watchlist. Each show will indicate when it was added to the
     * watchlist. Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     */
    @GET("/user/watchlist/shows.json/{apikey}/{username}")
    List<TvShow> watchlistShows(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows in a user's library. Each show will indicate how many plays it has.
     * Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}")
    List<TvShow> libraryShowsAll(
            @EncodedPath("username") String username
    );

    /**
     * Returns complete show info if set to true. Only send this if you really need the full dump as
     * it doubles the data size being sent back.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}/extended")
    List<TvShow> libraryShowsAllExtended(
            @EncodedPath("username") String username
    );

    /**
     * Returns only the minimal info (title, year, imdb_id, tvdb_id, tvrage_id, plays) required for
     * media center syncing if set to min. This sends about half the data.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}/min")
    List<TvShow> libraryShowsAllMinimum(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows and episodes that a user has watched. This method is useful to sync trakt's
     * data with local media center. Protected users won't return any data unless you are friends.
     */
    @GET("/user/library/shows/watched.json/{apikey}/{username}")
    List<TvShow> libraryShowsWatched(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows and episodes that a user has watched. This method is useful to sync trakt's
     * data with local media center. Protected users won't return any data unless you are friends.
     */
    @GET("/user/library/shows/watched.json/{apikey}/{username}/extended")
    List<TvShow> libraryShowsWatchedExtended(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows and episodes that a user has watched. This method is useful to sync trakt's
     * data with local media center. Protected users won't return any data unless you are friends.
     */
    @GET("/user/library/shows/watched.json/{apikey}/{username}/min")
    List<TvShow> libraryShowsWatchedMinimum(
            @EncodedPath("username") String username
    );

}