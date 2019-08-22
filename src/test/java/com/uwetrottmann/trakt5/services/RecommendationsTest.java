package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.Show;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecommendationsTest extends BaseTestCase {

    @Test
    public void test_movies() throws IOException {
        List<Movie> movies = executeCall(getTrakt().recommendations().movies(null, null, null));
        assertThat(movies).isNotEmpty();
    }

    @Test
    public void test_movies_pages() throws IOException {
        List<Movie> movies1 = executeCall(getTrakt().recommendations().movies(1, 5, null));
        assertThat(movies1).isNotEmpty();

        List<Movie> movies2 = executeCall(getTrakt().recommendations().movies(2, 5, null));
        assertThat(movies2).isNotEmpty().isNotEqualTo(movies1);
    }

    @Test
    public void test_dismissMovie() throws IOException {
        executeVoidCall(getTrakt().recommendations().dismissMovie(String.valueOf(TestData.MOVIE_TRAKT_ID)));
    }

    @Test
    public void test_shows() throws IOException {
        List<Show> shows = executeCall(getTrakt().recommendations().shows(null, null, null));
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_shows_pages() throws IOException {
        List<Show> shows1 = executeCall(getTrakt().recommendations().shows(1, 5, null));
        assertThat(shows1).isNotEmpty();

        List<Show> shows2 = executeCall(getTrakt().recommendations().shows(2, 5, null));
        assertThat(shows2).isNotEmpty().isNotEqualTo(shows1);
    }

    @Test
    public void test_dismissShow() throws IOException {
        executeVoidCall(getTrakt().recommendations().dismissShow(String.valueOf(TestData.SHOW_TRAKT_ID)));
    }

}
