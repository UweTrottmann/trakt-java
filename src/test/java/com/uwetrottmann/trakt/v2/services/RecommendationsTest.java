package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.Show;
import com.uwetrottmann.trakt.v2.enums.Extended;
import org.junit.Test;
import retrofit.client.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecommendationsTest extends BaseTestCase {

    @Test
    public void test_movies() throws OAuthUnauthorizedException {
        List<Movie> movies = getTrakt().recommendations().movies(Extended.DEFAULT_MIN);
        assertThat(movies).isNotEmpty();
    }

    @Test
    public void test_dismissMovie() throws OAuthUnauthorizedException {
        Response response = getTrakt().recommendations().dismissMovie(String.valueOf(TestData.MOVIE_TRAKT_ID));
        assertThat(response).isNotNull();
    }

    @Test
    public void test_shows() throws OAuthUnauthorizedException {
        List<Show> shows = getTrakt().recommendations().shows(Extended.DEFAULT_MIN);
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_dismissShow() throws OAuthUnauthorizedException {
        Response response = getTrakt().recommendations().dismissShow(String.valueOf(TestData.SHOW_TRAKT_ID));
        assertThat(response).isNotNull();
    }

}
