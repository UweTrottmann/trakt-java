package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.MovieTranslation;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Translation;
import com.uwetrottmann.trakt5.entities.TrendingMovie;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.Type;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoviesTest extends BaseTestCase {

    @Test
    public void test_popular() throws IOException {
        Response<List<Movie>> response = getTrakt().movies().popular(2, null, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Movie> movies = response.body();
        assertThat(movies.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
        for (Movie movie : movies) {
            assertMovieNotNull(movie);
        }
    }

    @Test
    public void test_trending() throws IOException {
        Response<List<TrendingMovie>> response = getTrakt().movies().trending(1, null, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<TrendingMovie> movies = response.body();
        assertThat(movies.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
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
    public void test_summary_slug() throws IOException {
        Response<Movie> response = getTrakt().movies().summary(TestData.MOVIE_SLUG, Extended.FULLIMAGES).execute();
        assertSuccessfulResponse(response);
        Movie movie = response.body();
        assertTestMovie(movie);
    }

    @Test
    public void test_summary_trakt_id() throws IOException {
        Response<Movie> response = getTrakt().movies().summary(String.valueOf(TestData.MOVIE_TRAKT_ID),
                Extended.FULLIMAGES).execute();
        assertSuccessfulResponse(response);
        Movie movie = response.body();
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
    public void test_translations() throws IOException {
        Response<List<MovieTranslation>> response = getTrakt().movies().translations("batman-begins-2005").execute();
        assertSuccessfulResponse(response);
        List<MovieTranslation> translations = response.body();
        for (Translation translation : translations) {
            assertThat(translation.language).isNotEmpty();
        }
    }

    @Test
    public void test_translation() throws IOException {
        Response<List<MovieTranslation>> response = getTrakt().movies().translation("batman-begins-2005",
                "de").execute();
        assertSuccessfulResponse(response);
        List<MovieTranslation> translations = response.body();
        // we know that Batman Begins has a German translation, otherwise this test would fail
        assertThat(translations).hasSize(1);
        assertThat(translations.get(0).language).isEqualTo("de");
    }

    @Test
    public void test_comments() throws IOException {
        Response<List<Comment>> response = getTrakt().movies().comments(TestData.MOVIE_SLUG, 1, null,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Comment> comments = response.body();
        assertThat(comments.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    public void test_people() throws IOException {
        Response<Credits> response = getTrakt().movies().people(TestData.MOVIE_SLUG).execute();
        assertSuccessfulResponse(response);
        Credits credits = response.body();
        assertCast(credits, Type.PERSON);
        assertCrew(credits, Type.PERSON);
    }

    @Test
    public void test_ratings() throws IOException {
        Response<Ratings> response = getTrakt().movies().ratings(TestData.MOVIE_SLUG).execute();
        assertSuccessfulResponse(response);
        Ratings ratings = response.body();
        assertRatings(ratings);
    }

}
