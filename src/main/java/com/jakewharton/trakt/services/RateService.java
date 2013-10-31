package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.RatingResponse;
import com.jakewharton.trakt.enumerations.Rating;

import retrofit.http.Body;
import retrofit.http.POST;

public interface RateService {

    @POST("/rate/episode/{apikey}")
    RatingResponse episode(
            @Body EpisodeRating rating
    );

    @POST("/rate/movie/{apikey}")
    RatingResponse movie(
            @Body MovieRating rating
    );

    @POST("/rate/show/{apikey}")
    RatingResponse show(
            @Body ShowRating rating
    );

    public static class ShowRating extends ShowService.Show {

        public Rating rating;

        public ShowRating(int tvdbId, Rating rating) {
            super(tvdbId);
            this.rating = rating;
        }
    }

    public static class EpisodeRating extends ShowService.Show {

        public int season;

        public int episode;

        public Rating rating;

        public EpisodeRating(int tvdbId, int season, int episode, Rating rating) {
            super(tvdbId);
            this.season = season;
            this.episode = episode;
            this.rating = rating;
        }
    }

    public class MovieRating extends MovieService.Movie {

        public Rating rating;

        public MovieRating(String imdbId, Rating rating) {
            super(imdbId);
            this.rating = rating;
        }

        public MovieRating(int tmdbId, Rating rating) {
            super(tmdbId);
            this.rating = rating;
        }
    }
}
