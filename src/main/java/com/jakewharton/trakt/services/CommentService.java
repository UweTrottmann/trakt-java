
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
     * Add a comment (shout or review) to a show on trakt.
     */
    @POST("/comment/show/{apikey}")
    Response show(
            @Body ShowComment comment
    );

    /**
     * Add a comment (shout or review) to a movie on trakt.
     */
    @POST("/comment/movie/{apikey}")
    Response movie(
            @Body MovieComment comment
    );

    public static class EpisodeComment extends ShowComment {

        public int season;

        public int episode;
    }

    public static class ShowComment extends ShowService.Show {

        public String comment;

        /**
         * <em>Optional.</em> Set to true to indicate the shout is a spoiler. Defaults to false.
         */
        public Boolean spoiler;

        /**
         * <em>Optional.</em> Set to true to indicate the comment is a 200+ word review. Defaults to
         * false.
         */
        public Boolean review;
    }

    public static class MovieComment extends MovieService.Movie {

        public String comment;

        /**
         * <em>Optional.</em> Set to true to indicate the shout is a spoiler. Defaults to false.
         */
        public Boolean spoiler;

        /**
         * <em>Optional.</em> Set to true to indicate the comment is a 200+ word review. Defaults to
         * false.
         */
        public Boolean review;
    }

}
