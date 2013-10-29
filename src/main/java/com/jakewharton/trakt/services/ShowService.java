package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Checkin;
import com.jakewharton.trakt.entities.CheckinResponse;
import com.jakewharton.trakt.entities.Response;
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

    public static class Show {

        public String imdb_id;

        public Integer tvdb_id;

        public String title;

        public Integer year;
    }

    public static class Season extends Show {

        public int season;

    }

    public static class Episodes extends Show {

        public List<Episode> episodes;

        public Episodes(int tvdbId, int season, int episode) {
            this.tvdb_id = tvdbId;
            episodes = new ArrayList<Episode>();
            episodes.add(new Episode(season, episode));
        }

        public Episodes(int tvdbId, List<Episode> episodes) {
            this.tvdb_id = tvdbId;
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
