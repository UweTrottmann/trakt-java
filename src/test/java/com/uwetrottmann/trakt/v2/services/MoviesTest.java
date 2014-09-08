package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.MovieTranslation;
import com.uwetrottmann.trakt.v2.entities.Translation;
import com.uwetrottmann.trakt.v2.entities.TrendingMovie;
import com.uwetrottmann.trakt.v2.enums.Extended;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoviesTest extends BaseTestCase {

    @Test
    public void test_popular() {
        List<Movie> movies = getTrakt().movies().popular();
        for (Movie movie : movies) {
            assertMovieNotNull(movie);
        }
    }

    @Test
    public void test_trending() {
        List<TrendingMovie> movies = getTrakt().movies().trending();
        for (TrendingMovie movie : movies) {
            assertThat(movie.watchers).isNotNull();
            assertMovieNotNull(movie.movie);
        }
    }

    private void assertMovieNotNull(Movie movie) {
        assertThat(movie.title).isNotEmpty();
        assertThat(movie.ids).isNotNull();
        assertThat(movie.ids.trakt).isNotNull();
        assertThat(movie.year).isNotNull();
    }

    @Test
    public void test_summary_slug() {
        Movie movie = getTrakt().movies().summary(TestData.MOVIE_SLUG, Extended.FULLIMAGES);
        assertTestMovie(movie);
    }

    @Test
    public void test_summary_trakt_id() {
        Movie movie = getTrakt().movies().summary(String.valueOf(TestData.MOVIE_TRAKT_ID), Extended.FULLIMAGES);
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

    @Test
    public void test_translations() {
        List<MovieTranslation> translations = getTrakt().movies().translations("batman-begins-2005");
        for (Translation translation : translations) {
            assertThat(translation.language).isNotEmpty();
        }
    }

    @Test
    public void test_translation() {
        List<MovieTranslation> translations = getTrakt().movies().translation("batman-begins-2005", "de");
        // we know that Batman Begins has a German translation, otherwise this test would fail
        assertThat(translations).hasSize(1);
        assertThat(translations.get(0).language).isEqualTo("de");
    }

    @Test
    public void test_comments() {
        getTrakt().movies().comments(TestData.MOVIE_SLUG);
    }

}
