package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Checkin;
import com.jakewharton.trakt.entities.CheckinResponse;
import com.jakewharton.trakt.entities.Comment;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.Stats;
import com.jakewharton.trakt.entities.TvShow;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
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
            @Body Checkin checkin
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
            @Path("title") String slug,
            @Path("season") int season,
            @Path("episode") int episode,
            @Path("type") String type
    );

    /**
     * Add episodes to your library collection.
     */
    @POST("/show/episode/library/{apikey}")
    void episodeLibrary(
            @Body Episodes episodes
    );

    /**
     * Remove episodes from your library collection.
     */
    @POST("/show/episode/unlibrary/{apikey}")
    void episodeUnlibrary(
            @Body Episodes episodes
    );

    /**
     * Add episodes watched outside of trakt to your library.
     */
    @POST("/show/episode/seen/{apikey}")
    void episodeSeen(
            @Body Episodes episodes
    );

    /**
     * Remove episodes watched outside of trakt from your library.
     */
    @POST("/show/episode/unseen/{apikey}")
    void episodeUnseen(
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
     * Add all episodes for a season to your library collection.
     */
    @POST("/show/season/library/{apikey}")
    void seasonLibrary(
            @Body Season season
    );

    /**
     * Add all episodes from a season watched outside of trakt to your library.
     */
    @POST("/show/library/seen/{apikey}")
    void seasonSeen(
            @Body Season season
    );

    /**
     * Add all episodes for a show to your library collection.
     */
    @POST("/show/library/{apikey}")
    void showLibrary(
            @Body Show show
    );

    /**
     * Remove an entire show (including all episodes) from your library collection.
     */
    @POST("/show/unlibrary/{apikey}")
    void showUnlibrary(
            @Body Show show
    );

    /**
     * Add all episodes for a show watched outside of trakt to your library.
     */
    @POST("/show/seen/{apikey}")
    void showSeen(
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
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}")
    TvShow summary(
            @Path("title") int tvdbId
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") String slug
    );

    @GET("/show/summary.json/{apikey}/{title}/extended")
    TvShow summaryExtended(
            @Path("title") int tvdbId
    );

    @GET("/shows/trending.json/{apikey}")
    List<TvShow> trending();

    public static class Show {

        public String imdb_id;

        public Integer tvdb_id;

        public String title;

        public Integer year;

        public Show(int tvdbId) {
            this.tvdb_id = tvdbId;
        }

        public Show(String imdbId) {
            this.imdb_id = imdbId;
        }

        public Show(String title, int year) {
            this.title = title;
            this.year = year;
        }
    }

    public static class Season extends Show {

        public int season;

        public Season(int tvdbId, int season) {
            super(tvdbId);
            this.season = season;
        }
    }

    public static class Episodes extends Show {

        public List<Episode> episodes;

        public Episodes(int tvdbId, int season, int episode) {
            super(tvdbId);
            episodes = new ArrayList<Episode>();
            episodes.add(new Episode(season, episode));
        }

        public Episodes(int tvdbId, List<Episode> episodes) {
            super(tvdbId);
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

}
