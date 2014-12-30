package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecommendationsServiceTest extends BaseTestCase {

    @Test
    public void test_movies() {
        List<Movie> movies = getManager().recommendationsService().movies();
        assertThat(movies).isNotNull();

        if (!movies.isEmpty()) {
            assertThat(movies.get(0)).isNotNull();
        }
    }

    @Test
    public void test_shows() {
        List<TvShow> tvShows = getManager().recommendationsService().shows();
        assertThat(tvShows).isNotNull();

        if (!tvShows.isEmpty()) {
            assertThat(tvShows.get(0)).isNotNull();
        }
    }
}
