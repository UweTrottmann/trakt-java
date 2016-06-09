package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecommendationsTest extends BaseTestCase {

    @Test
    public void test_movies() throws IOException {
        List<Movie> movies = executeCall(getTrakt().recommendations().movies(Extended.DEFAULT_MIN));
        assertThat(movies).isNotEmpty();
    }

    @Test
    public void test_dismissMovie() throws IOException {
        executeCall(getTrakt().recommendations().dismissMovie(String.valueOf(TestData.MOVIE_TRAKT_ID)));
    }

    @Test
    public void test_shows() throws IOException {
        List<Show> shows = executeCall(getTrakt().recommendations().shows(Extended.DEFAULT_MIN));
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_dismissShow() throws IOException {
        executeCall(getTrakt().recommendations().dismissShow(String.valueOf(TestData.SHOW_TRAKT_ID)));
    }

}
