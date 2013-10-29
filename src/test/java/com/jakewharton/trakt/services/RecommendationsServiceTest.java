package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class RecommendationsServiceTest extends BaseTestCase {

    public void test_movies() {
        List<Movie> movies = getManager().recommendationsService().movies();
        assertThat(movies).isNotNull();

        if (!movies.isEmpty()) {
            assertThat(movies.get(0)).isNotNull();
        }
    }

    public void test_shows() {
        List<TvShow> tvShows = getManager().recommendationsService().shows();
        assertThat(tvShows).isNotNull();

        if (!tvShows.isEmpty()) {
            assertThat(tvShows.get(0)).isNotNull();
        }
    }
}
