package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Movie;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecommendationsTest extends BaseTestCase {

    @Test
    public void test_movies() throws IOException {
        Response<List<Movie>> response = getTrakt().recommendations().movies(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Movie> movies = response.body();
        assertThat(movies).isNotEmpty();
    }

    @Test
    public void test_dismissMovie() throws IOException {
        Response response = getTrakt().recommendations().dismissMovie(
                String.valueOf(TestData.MOVIE_TRAKT_ID)).execute();
        assertSuccessfulResponse(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void test_shows() throws IOException {
        Response<List<Show>> response = getTrakt().recommendations().shows(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Show> shows = response.body();
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_dismissShow() throws IOException {
        Response response = getTrakt().recommendations().dismissShow(String.valueOf(TestData.SHOW_TRAKT_ID)).execute();
        assertSuccessfulResponse(response);
        assertThat(response).isNotNull();
    }

}
