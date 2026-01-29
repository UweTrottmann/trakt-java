/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.MovieTranslation;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.entities.Translation;
import com.uwetrottmann.trakt5.entities.TrendingMovie;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.Type;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MoviesTest extends BaseTestCase {

    @Test
    public void test_popular() throws IOException {
        List<Movie> movies = executeCall(getTrakt().movies().popular(2, null, null));
        assertThat(movies).isNotNull();
        assertThat(movies.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
        for (Movie movie : movies) {
            assertMovieNotNull(movie);
        }
    }

    @Test
    public void test_trending() throws IOException {
        List<TrendingMovie> movies = executeCall(getTrakt().movies().trending(1, null, null));
        assertThat(movies).isNotNull();
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
        Movie movie = executeCall(getTrakt().movies().summary(TestData.MOVIE_SLUG, Extended.FULL));
        assertTestMovie(movie);
    }

    @Test
    public void test_summary_trakt_id() throws IOException {
        Movie movie = executeCall(getTrakt().movies().summary(String.valueOf(TestData.MOVIE_TRAKT_ID),
                Extended.FULL));
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
        List<MovieTranslation> translations = executeCall(getTrakt().movies().translations("batman-begins-2005"));
        assertThat(translations).isNotNull();
        for (Translation translation : translations) {
            assertThat(translation.language).isNotEmpty();
        }
    }

    @Test
    public void test_translation() throws IOException {
        List<MovieTranslation> translations = executeCall(getTrakt().movies().translation("batman-begins-2005",
                "de"));
        assertThat(translations).isNotNull();
        // we know that Batman Begins has a German translation, otherwise this test would fail
        assertThat(translations).isNotEmpty();
        assertThat(translations.get(0).language).isEqualTo("de");
    }

    @Test
    public void test_comments() throws IOException {
        List<Comment> comments = executeCall(getTrakt().movies().comments(TestData.MOVIE_SLUG, 1, null,
                null));
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    public void test_people() throws IOException {
        Credits credits = executeCall(getTrakt().movies().people(TestData.MOVIE_SLUG));
        assertCast(credits, Type.PERSON);
        assertCrew(credits, Type.PERSON);
    }

    @Test
    public void test_ratings() throws IOException {
        Ratings ratings = executeCall(getTrakt().movies().ratings(TestData.MOVIE_SLUG));
        assertRatings(ratings);
    }

    @Test
    public void test_stats() throws IOException {
        Stats stats = executeCall(getTrakt().movies().stats(TestData.MOVIE_SLUG));
        assertStats(stats);
    }

}
