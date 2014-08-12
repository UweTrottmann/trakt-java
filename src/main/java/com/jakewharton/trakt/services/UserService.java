
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.CalendarDate;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowProgress;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.enumerations.Extended;
import com.jakewharton.trakt.enumerations.Extended2;
import com.jakewharton.trakt.enumerations.SortType;

import java.util.List;

import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

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
     * Returns all movies in a user's library. Each movie will indicate if it's in the user's
     * collection and how many plays it has. Protected users won't return any data unless you are
     * friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete movie info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tmdb_id, plays, in_collection,
     *                 unseen) required for media center syncing if set to MIN. This sends about
     *                 half the data.
     */
    @GET("/user/library/movies/all.json/{apikey}/{username}{extended}")
    List<Movie> libraryMoviesAll(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns all movies in a user's library collection. Collection items might include blu-rays,
     * dvds, and digital downloads. Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete movie info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tmdb_id) required for media center
     *                 syncing if set to MIN. This sends about half the data.
     */
    @GET("/user/library/movies/collection.json/{apikey}/{username}{extended}")
    List<Movie> libraryMoviesCollection(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns all movies that a user has watched. This method is useful to sync trakt's data with
     * local media center. Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete movie info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tmdb_id, plays) required for media
     *                 center syncing if set to MIN. This sends about half the data.
     */
    @GET("/user/library/movies/watched.json/{apikey}/{username}{extended}")
    List<Movie> libraryMoviesWatched(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns all shows in a user's library. Each show will indicate how many plays it has.
     * Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete show info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tvdb_id, tvrage_id, plays) required
     *                 for media center syncing if set to MIN. This sends about half the data.
     */
    @GET("/user/library/shows/all.json/{apikey}/{username}{extended}")
    List<TvShow> libraryShowsAll(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns all shows and episodes in a user's library collection. Collection items might include
     * blu-rays, dvds, and digital downloads. Protected users won't return any data unless you are
     * friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete show info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tvdb_id, tvrage_id, seasons) required
     *                 for media center syncing if set to MIN. This sends about half the data.
     */
    @GET("/user/library/shows/collection.json/{apikey}/{username}{extended}")
    List<TvShow> libraryShowsCollection(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns all shows and episodes that a user has watched. This method is useful to sync trakt's
     * data with local media center. Protected users won't return any data unless you are friends.
     *
     * @param username You can get a username by browsing the website and looking at the URL when on
     *                 a profile page.
     * @param extended Returns complete show info if set to EXTENDED. Only send this if you really
     *                 need the full dump as it doubles the data size being sent back. Returns only
     *                 the minimal info (title, year, imdb_id, tvdb_id, tvrage_id, seasons) required
     *                 for media center syncing if set to MIN. This sends about half the data.
     */
    @GET("/user/library/shows/watched.json/{apikey}/{username}{extended}")
    List<TvShow> libraryShowsWatched(
            @EncodedPath("username") String username,
            @EncodedPath("extended") Extended extended
    );

    /**
     * Returns profile information for a user. Protected users won't return any data unless you are
     * friends.
     */
    @GET("/user/profile.json/{apikey}/{username}")
    UserProfile profile(
            @EncodedPath("username") String username
    );
    
    /**
     * Get the progress of the shows that the user has watched
     * @param username  You can get a username by browsing the website and looking at the URL when on
     *                  a profile page.
     * @param title     You can get a specific show using the slug e.g. the-walking-dead or TVDB id.
     *                  All show progress can be returned using the title "all"
     * @param sort      Sort the returned shows. See {@link SortType}
     * @param extended  Return minimal show info (title, year, imdb_id, tvdb_id, tvrage_id) by default.
     *                  Set to {@link Extended2#NORMAL} to also include images and show url.
     *                  Set to {@link Extended2#FULL} for the complete show details.
     *                  Only get extended info if you really need to since it adds a lot of data
     * @return the progress of the shows that a user has watched
     */
    @GET("/user/progress/watched.json/{apikey}/{username}/{title}/{sort}{extended}")
    List<TvShowProgress> progressWatched(
            @EncodedPath("username") String username,
            @Path("title") String title,
            @Path("sort") SortType sort,
            @EncodedPath("extended") Extended2 extended
    );
    
    /**
     * Get the progress of the shows that the user has collected
     * @param username  You can get a username by browsing the website and looking at the URL when on
     *                  a profile page.
     * @param title     You can get a specific show using the slug e.g. the-walking-dead or TVDB id.
     *                  All shows can be returned using the title "all"
     * @param sort      Sort the returned shows. See {@link SortType}
     * @param extended  Return minimal show info (title, year, imdb_id, tvdb_id, tvrage_id) by default.
     *                  Set to {@link Extended2#NORMAL} to also include images and show url.
     *                  Set to {@link Extended2#FULL} for the complete show details.
     *                  Only get extended info if you really need to since it adds a lot of data
     * @return the progress of the shows that the user has collected
     */
    @GET("/user/progress/collected.json/{apikey}/{username}/{title}/{sort}{extended}")
    List<TvShowProgress> progressCollected(
            @EncodedPath("username") String username,
            @Path("title") String title,
            @Path("sort") SortType sort,
            @EncodedPath("extended") Extended2 extended
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
     * Returns all shows airing in the next 7 days.
     */
    @GET("/user/calendar/shows.json/{apikey}/{username}")
    List<CalendarDate> calendarShows(
            @EncodedPath("username") String username
    );

    /**
     * Returns all shows airing during the time period specified.
     *
     * @param date Start date for the calendar in the format Ymd (i.e. 20110421). If blank, defaults to today.
     * @param days Number of days to display starting from the date. If blank, defaults to 7 days.
     */
    @GET("/user/calendar/shows.json/{apikey}/{username}/{date}/{days}")
    List<CalendarDate> calendarShows(
            @EncodedPath("username") String username,
            @EncodedPath("date") String date,
            @EncodedPath("days") int days
    );

}