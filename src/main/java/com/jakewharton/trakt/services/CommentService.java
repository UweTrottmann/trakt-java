
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Response;

import retrofit.http.Body;
import retrofit.http.POST;

public interface CommentService {

    /**
     * Add a comment (shout or review) to an episode on trakt.
     */
    @POST("/comment/episode/{apikey}")
    Response episode(
            @Body EpisodeComment comment
    );

    /**
     * Add a comment (shout or review) to a movie on trakt.
     */
    @POST("/comment/movie/{apikey}")
    Response movie(
            @Body MovieComment comment
    );

    /**
     * Add a comment (shout or review) to a show on trakt.
     */
    @POST("/comment/show/{apikey}")
    Response show(
            @Body ShowComment comment
    );

    public static class EpisodeComment extends ShowComment {

        public int season;

        public int episode;

        public EpisodeComment(int tvdbId, int season, int episode, String comment) {
            super(tvdbId, comment);
            this.season = season;
            this.episode = episode;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the shout is a spoiler. Defaults to false.
         */
        public EpisodeComment spoiler(boolean isSpoiler) {
            this.spoiler = isSpoiler;
            return this;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the comment is a 200+ word review. Defaults to
         * false.
         */
        public EpisodeComment review(boolean isReview) {
            this.review = isReview;
            return this;
        }
    }

    public static class MovieComment extends MovieService.Movie {

        public String comment;

        public Boolean spoiler;

        public Boolean review;

        public MovieComment(String imdbId, String comment) {
            super(imdbId);
            this.comment = comment;
        }

        public MovieComment(int tmdbId, String comment) {
            super(tmdbId);
            this.comment = comment;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the shout is a spoiler. Defaults to false.
         */
        public MovieComment spoiler(boolean isSpoiler) {
            this.spoiler = isSpoiler;
            return this;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the comment is a 200+ word review. Defaults to
         * false.
         */
        public MovieComment review(boolean isReview) {
            this.review = isReview;
            return this;
        }
    }

    public static class ShowComment extends ShowService.Show {

        public String comment;

        public Boolean spoiler;

        public Boolean review;

        public ShowComment(int tvdbId, String comment) {
            super(tvdbId);
            this.comment = comment;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the shout is a spoiler. Defaults to false.
         */
        public ShowComment spoiler(boolean isSpoiler) {
            this.spoiler = isSpoiler;
            return this;
        }

        /**
         * <em>Optional.</em> Set to true to indicate the comment is a 200+ word review. Defaults to
         * false.
         */
        public ShowComment review(boolean isReview) {
            this.review = isReview;
            return this;
        }
    }


}
