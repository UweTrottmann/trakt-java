package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Movie;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MoviesTest extends BaseTestCase {

    @Test
    public void test_summary_slug() {
        Movie movie = getTrakt().movies().summary(TestData.MOVIE_SLUG);
        assertTestMovie(movie);
    }

    @Test
    public void test_summary_trakt_id() {
        Movie movie = getTrakt().movies().summary(TestData.MOVIE_TRAKT_ID);
        assertTestMovie(movie);
    }

    public static void assertTestMovie(Movie movie) {
        assertThat(movie).isNotNull();
        assertThat(movie.ids).isNotNull();
        assertThat(movie.title).isEqualTo(TestData.MOVIE_TITLE);
        assertThat(movie.year).isEqualTo(TestData.MOVIE_YEAR);
        assertThat(movie.ids.trakt).isEqualTo(TestData.MOVIE_TRAKT_ID);
        assertThat(movie.ids.slug).isEqualTo(TestData.MOVIE_SLUG);
        assertThat(movie.ids.imdb).isEqualTo(TestData.MOVIE_IMDB_ID);
        assertThat(movie.ids.tmdb).isEqualTo(TestData.MOVIE_TMDB_ID);
    }

}
