package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.CheckinResponse;
import com.jakewharton.trakt.entities.Comment;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.Share;
import com.jakewharton.trakt.entities.Stats;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.enumerations.Extended2;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Endpoints for Show.
 */
public interface ShowService {

    /**
     * Notify trakt that a user wants to cancel their current check in. <br/> <br/>
     * <em>Warning</em>: This method requires a developer API key.
     */
    @POST("/show/cancelcheckin/{apikey}")
    Response cancelcheckin();

    /**
     * Notify trakt that a user has stopped watching a show.<br/> <br/> <em>Warning</em>: This
     * method requires a developer API key.
     */
    @POST("/show/cancelwatching/{apikey}")
    Response cancelwatching();

    /**
     * Check into a show on trakt. Think of this method as in between a seen and a scrobble. After
     * checking in, trakt will automatically display it as watching then switch over to watched
     * status once the duration has elapsed.<br/> <br/> <em>Warning</em>: This method requires a
     * developer API key.
     */
    @POST("/show/checkin/{apikey}")
    CheckinResponse checkin(
            @Body ShowCheckin checkin
    );

    /**
     * Returns all comments (shouts and reviews) for a show. Most recent comments returned first.
     */
    @GET("/show/comments.json/{apikey}/{title}")
    List<Comment> comments(
            @Path("title") int tvdbId
    );

    /**
     * Returns all comments (shouts and reviews) for a show. Most recent comments returned first.
     */
    @GET("/show/comments.json/{apikey}/{title}")
    List<Comment> comments(
            @Path("title") String slug
    );

    /**
     * Returns all comments (shouts and reviews) for a show. Most recent comments returned first.
     *
     * @param type Set to all (default), shouts, or reviews.
     */
    @GET("/show/comments.json/{apikey}/{title}/{type}")
    List<Comment> comments(
            @Path("title") int tvdbId,
            @Path("type") String type
    );

    /**
     * Returns all comments (shouts and reviews) for a show. Most recent comments returned first.
     *
     * @param type Set to all (default), shouts, or reviews.
     */
    @GET("/show/comments.json/{apikey}/{title}/{type}")
    List<Comment> comments(
            @Path("title") String slug,
            @Path("type") String type
    );

    /**
     * Returns all comments (shouts and reviews) for an episode. Most recent comments returned
     * first.
     */
    @GET("/show/episode/comments.json/{apikey}/{title}/{season}/{episode}")
    List<Comment> episodeComments(
            @Path("title") int tvdbId,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Returns all comments (shouts and reviews) for an episode. Most recent comments returned
     * first.
     */
    @GET("/show/episode/comments.json/{apikey}/{title}/{season}/{episode}")
    List<Comment> episodeComments(
            @Path("title") String slug,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Returns all comments (shouts and reviews) for an episode. Most recent comments returned
     * first.
     *
     * @param type Set to all (default), shouts, or reviews.
     */
    @GET("/show/episode/comments.json/{apikey}/{title}/{season}/{episode}/{type}")
    List<Comment> episodeComments(
            @Path("title") int tvdbId,
            @Path("season") int season,
            @Path("episode") int episode,
            @Path("type") String type
    );

    /**
     * Returns all comments (shouts and reviews) for an episode. Most recent comments returned
     * first.
     *
     * @param type Set to all (default), shouts, or reviews.
     */
    @GET("/show/episode/comments.json/{apikey}/{title}/{season}/{episode}/{type}")
    List<Comment> episodeComments(
            @Path("title") String slug,
            @Path("season") int season,
            @Path("episode") int episode,
            @Path("type") String type
    );

    /**
     * Add episodes to your library collection.
     */
    @POST("/show/episode/library/{apikey}")
    Response episodeLibrary(
            @Body Episodes episodes
    );

    /**
     * Add episodes watched outside of trakt to your library.
     */
    @POST("/show/episode/seen/{apikey}")
    Response episodeSeen(
            @Body Episodes episodes
    );

    @GET("/show/episode/stats.json/{apikey}/{title}/{season}/{episode}")
    Stats episodeStats(
            @Path("title") int showTvdbId,
            @Path("season") int season,
            @Path("episode") int episode
    );

    @GET("/show/episode/stats.json/{apikey}/{title}/{season}/{episode}")
    Stats episodeStats(
            @Path("title") String slug,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Returns information for an episode including ratings.
     */
    @GET("/show/episode/summary.json/{apikey}/{title}/{season}/{episode}")
    TvEntity episodeSummary(
            @Path("title") int showTvdbId,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Returns information for an episode including ratings.
     */
    @GET("/show/episode/summary.json/{apikey}/{title}/{season}/{episode}")
    TvEntity episodeSummary(
            @Path("title") String slug,
            @Path("season") int season,
            @Path("episode") int episode
    );

    /**
     * Remove episodes from your library collection.
     */
    @POST("/show/episode/unlibrary/{apikey}")
    Response episodeUnlibrary(
            @Body Episodes episodes
    );

    /**
     * Remove episodes watched outside of trakt from your library.
     */
    @POST("/show/episode/unseen/{apikey}")
    Response episodeUnseen(
            @Body Episodes episodes
    );

    /**
     * Add all episodes for a season to your library collection.
     */
    @POST("/show/season/library/{apikey}")
    Response seasonLibrary(
            @Body Season season
    );

    /**
     * Add all episodes from a season watched outside of trakt to your library.
     */
    @POST("/show/season/seen/{apikey}")
    Response seasonSeen(
            @Body Season season
    );

    /**
     * Add all episodes for a show to your library collection.
     */
    @POST("/show/library/{apikey}")
    Response showLibrary(
            @Body Show show
    );

    /**
     * Add all episodes for a show watched outside of trakt to your library.
     */
    @POST("/show/seen/{apikey}")
    Response showSeen(
            @Body Show show
    );

    /**
     * Remove an entire show (including all episodes) from your library collection.
     */
    @POST("/show/unlibrary/{apikey}")
    Response showUnlibrary(
            @Body Show show
    );

    @GET("/show/stats.json/{apikey}/{title}")
    Stats stats(
            @Path("title") int showTvdbId
    );

    @GET("/show/stats.json/{apikey}/{title}")
    Stats stats(
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}")
    TvShow summary(
            @Path("title") int tvdbId
    );

    @GET("/show/summary.json/{apikey}/{title}")
    TvShow summary(
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") int tvdbId
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") String slug
    );

    /**
     * Returns information for one or more TV shows.
     *
     * @param extended2 By default, this returns the minimal info. Set to NORMAL for more info (url,
     *                  images, genres). Set to FULL for full info. Only send this if you really
     *                  need the full dump as it doubles the data size being sent back.
     */
    @GET("/show/summaries.json/{apikey}/{title}/{extended}")
    List<TvShow> summaries(
            @EncodedPath("title") String slugsOrTvdbIds,
            @Path("extended") Extended2 extended2
    );

    @GET("/shows/trending.json/{apikey}")
    List<TvShow> trending();

    public static class Episodes extends Show {

        public List<Episode> episodes;

        public Episodes(int showTvdbId, int season, int episode) {
            super(showTvdbId);
            episodes = new ArrayList<Episode>();
            episodes.add(new Episode(season, episode));
        }

        public Episodes(int showTvdbId, List<Episode> episodes) {
            super(showTvdbId);
            this.episodes = episodes;
        }

        public static class Episode {

            public int season;

            public int episode;

            public String last_played;

            public Episode(int season, int episode) {
                this.season = season;
                this.episode = episode;
            }
        }
    }

    public static class Season extends Show {

        public int season;

        public Season(int showTvdbId, int season) {
            super(showTvdbId);
            this.season = season;
        }
    }

    public static class Show {

        public String imdb_id;

        public Integer tvdb_id;

        public String title;

        public Integer year;

        public Show(int showTvdbId) {
            this.tvdb_id = showTvdbId;
        }

        public Show(String imdbId) {
            this.imdb_id = imdbId;
        }

        public Show(String title, int year) {
            this.title = title;
            this.year = year;
        }
    }

    public class ShowCheckin extends Show {

        public Integer season;

        public Integer episode;

        public Integer episode_tvdb_id;

        public Integer duration;

        public Integer venue_id;

        public String venue_name;

        public Share share;

        public String message;

        public String app_version;

        public String app_date;

        /**
         * @param appVersion Version number of the app, be as specific as you can including nightly
         *                   build number, etc. Used to help debug.
         * @param appDate    Build date of the media center. Used to help debug.
         */
        public ShowCheckin(int showTvdbId, int season, int episode, String appVersion,
                String appDate) {
            super(showTvdbId);
            this.season = season;
            this.episode = episode;
            this.app_version = appVersion;
            this.app_date = appDate;
        }

        public ShowCheckin(int showTvdbId, int season, int episode, String message,
                String appVersion,
                String appDate) {
            this(showTvdbId, season, episode, appVersion, appDate);
            this.message = message;
        }

    }


}
