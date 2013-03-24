
package com.jakewharton.trakt.services;

import com.google.myjson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Response;

public class CommentService extends TraktApiService {
    /**
     * Add a comment (shout or review) to an episode on trakt.
     * 
     * @param imdbId IMDB ID for the show.
     * @return Builder instance.
     */
    public EpisodeBuilder episode(String imdbId) {
        return new EpisodeBuilder(this).imdbId(imdbId);
    }

    /**
     * Add a comment (shout or review) to an episode on trakt.
     * 
     * @param tvdbId TVDB ID (thetvdb.com) for the show.
     * @return Builder instance.
     */
    public EpisodeBuilder episode(int tvdbId) {
        return new EpisodeBuilder(this).tvdbId(tvdbId);
    }

    /**
     * Add a comment (shout or review) to an episode on trakt.
     * 
     * @param title Show title.
     * @param year Show title.
     * @return Builder instance.
     */
    public EpisodeBuilder episode(String title, int year) {
        return new EpisodeBuilder(this).title(title).year(year);
    }

    /**
     * Add a comment (shout or review) to a movie on trakt.
     * 
     * @param imdbId IMDB ID for the movie.
     * @return Builder instance.
     */
    public MovieBuilder movie(String imdbId) {
        return new MovieBuilder(this).imdbId(imdbId);
    }

    /**
     * Add a comment (shout or review) to a movie on trakt.
     * 
     * @param tmdbId TMDB (themoviedb.org) ID for the movie.
     * @return Builder instance.
     */
    public MovieBuilder movie(int tmdbId) {
        return new MovieBuilder(this).tmdbId(tmdbId);
    }

    /**
     * Add a comment (shout or review) to a movie on trakt.
     * 
     * @param title Movie title.
     * @param year Movie year.
     * @return Builder instance.
     */
    public MovieBuilder movie(String title, int year) {
        return new MovieBuilder(this).title(title).year(year);
    }

    /**
     * Add a comment (shout or review) to a show on trakt.
     * 
     * @param imdbId IMDB ID for the show.
     * @return Builder instance.
     */
    public ShowBuilder show(String imdbId) {
        return new ShowBuilder(this).imdbId(imdbId);
    }

    /**
     * Add a comment (shout or review) to a show on trakt.
     * 
     * @param tvdbId TVDB ID (thetvdb.com) for the show.
     * @return Builder instance.
     */
    public ShowBuilder show(int tvdbId) {
        return new ShowBuilder(this).tvdbId(tvdbId);
    }

    /**
     * Add a comment (shout or review) to a show on trakt.
     * 
     * @param title Show title.
     * @param year Show year.
     * @return Builder instance.
     */
    public ShowBuilder show(String title, int year) {
        return new ShowBuilder(this).title(title).year(year);
    }

    private static final String POST_IMDB_ID = "imdb_id";
    private static final String POST_TVDB_ID = "tvdb_id";
    private static final String POST_TMDB_ID = "tmdb_id";
    private static final String POST_TITLE = "title";
    private static final String POST_YEAR = "year";
    private static final String POST_COMMENT = "comment";
    private static final String POST_SPOILER = "spoiler";
    private static final String POST_REVIEW = "review";

    public static final class EpisodeBuilder extends TraktApiBuilder<Response> {
        private static final String POST_SEASON = "season";
        private static final String POST_EPISODE = "episode";

        private static final String URI = "/comment/episode/" + FIELD_API_KEY;

        private EpisodeBuilder(CommentService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Show IMDB ID.
         * 
         * @param imdbId Value.
         * @return Builder instance.
         */
        public EpisodeBuilder imdbId(String imdbId) {
            postParameter(POST_IMDB_ID, imdbId);
            return this;
        }

        /**
         * Show TVDB ID.
         * 
         * @param tvdbId Value.
         * @return Builder instance.
         */
        public EpisodeBuilder tvdbId(int tvdbId) {
            postParameter(POST_TVDB_ID, tvdbId);
            return this;
        }

        /**
         * Show title.
         * 
         * @param title Value.
         * @return Builder instance.
         */
        public EpisodeBuilder title(String title) {
            postParameter(POST_TITLE, title);
            return this;
        }

        /**
         * Show year.
         * 
         * @param year Value.
         * @return Builder instance.
         */
        public EpisodeBuilder year(int year) {
            postParameter(POST_YEAR, year);
            return this;
        }

        /**
         * Show season. Send 0 if watching a special.
         * 
         * @param season Value.
         * @return Builder instance.
         */
        public EpisodeBuilder season(int season) {
            postParameter(POST_SEASON, season);
            return this;
        }

        /**
         * Show episode.
         * 
         * @param episode Value.
         * @return Builder instance.
         */
        public EpisodeBuilder episode(int episode) {
            postParameter(POST_EPISODE, episode);
            return this;
        }

        /**
         * Text for the comment.
         * 
         * @param comment Value.
         * @return Builder instance.
         */
        public EpisodeBuilder comment(String comment) {
            postParameter(POST_COMMENT, comment);
            return this;
        }

        /**
         * Set to true to indicate the comment is a spoiler. Defaults to false.
         * 
         * @param spoiler Value.
         * @return Builder instance.
         */
        public EpisodeBuilder spoiler(boolean spoiler) {
            postParameter(POST_SPOILER, spoiler);
            return this;
        }

        /**
         * Set to true to indicate the comment is a 200+ word review. Defaults
         * to false.
         * 
         * @param review Value.
         * @return Builder instance.
         */
        public EpisodeBuilder review(boolean review) {
            postParameter(POST_REVIEW, review);
            return this;
        }
    }

    public static final class MovieBuilder extends TraktApiBuilder<Response> {

        private static final String URI = "/comment/movie/" + FIELD_API_KEY;

        private MovieBuilder(CommentService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * IMDB ID for the movie.
         * 
         * @param imdbId Value.
         * @return Builder instance.
         */
        public MovieBuilder imdbId(String imdbId) {
            postParameter(POST_IMDB_ID, imdbId);
            return this;
        }

        /**
         * TMDB (themoviedb.org) ID for the movie.
         * 
         * @param tmdbId Value.
         * @return Builder instance.
         */
        public MovieBuilder tmdbId(int tmdbId) {
            postParameter(POST_TMDB_ID, tmdbId);
            return this;
        }

        /**
         * Movie title.
         * 
         * @param title Value.
         * @return Builder instance.
         */
        public MovieBuilder title(String title) {
            postParameter(POST_TITLE, title);
            return this;
        }

        /**
         * Movie year.
         * 
         * @param year Value.
         * @return Builder instance.
         */
        public MovieBuilder year(int year) {
            postParameter(POST_YEAR, year);
            return this;
        }

        /**
         * Text for the comment.
         * 
         * @param comment Value.
         * @return Builder instance.
         */
        public MovieBuilder comment(String comment) {
            postParameter(POST_COMMENT, comment);
            return this;
        }

        /**
         * Set to true to indicate the shout is a spoiler. Defaults to false.
         * 
         * @param spoiler Value.
         * @return Builder instance.
         */
        public MovieBuilder spoiler(boolean spoiler) {
            postParameter(POST_SPOILER, spoiler);
            return this;
        }

        /**
         * Set to true to indicate the comment is a 200+ word review. Defaults
         * to false.
         * 
         * @param review Value.
         * @return Builder instance.
         */
        public MovieBuilder review(boolean review) {
            postParameter(POST_REVIEW, review);
            return this;
        }
    }

    public static final class ShowBuilder extends TraktApiBuilder<Response> {

        private static final String URI = "/comment/show/" + FIELD_API_KEY;

        private ShowBuilder(CommentService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Show IMDB ID.
         * 
         * @param imdbId Value.
         * @return Builder instance.
         */
        public ShowBuilder imdbId(String imdbId) {
            postParameter(POST_IMDB_ID, imdbId);
            return this;
        }

        /**
         * Show TVDB ID.
         * 
         * @param tvdbId Value.
         * @return Builder instance.
         */
        public ShowBuilder tvdbId(int tvdbId) {
            postParameter(POST_TVDB_ID, tvdbId);
            return this;
        }

        /**
         * Show title.
         * 
         * @param title Value.
         * @return Builder instance.
         */
        public ShowBuilder title(String title) {
            postParameter(POST_TITLE, title);
            return this;
        }

        /**
         * Show year.
         * 
         * @param year Value.
         * @return Builder instance.
         */
        public ShowBuilder year(int year) {
            postParameter(POST_YEAR, year);
            return this;
        }

        /**
         * Text for the comment.
         * 
         * @param comment Value.
         * @return Builder instance.
         */
        public ShowBuilder comment(String comment) {
            postParameter(POST_COMMENT, comment);
            return this;
        }

        /**
         * Set to true to indicate the comment is a spoiler. Defaults to false.
         * 
         * @param spoiler Value.
         * @return Builder instance.
         */
        public ShowBuilder spoiler(boolean spoiler) {
            postParameter(POST_SPOILER, spoiler);
            return this;
        }

        /**
         * Set to true to indicate the comment is a 200+ word review. Defaults
         * to false.
         * 
         * @param review Value.
         * @return Builder instance.
         */
        public ShowBuilder review(boolean review) {
            postParameter(POST_REVIEW, review);
            return this;
        }
    }
}
