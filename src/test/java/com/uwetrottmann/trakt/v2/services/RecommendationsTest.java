package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.Show;
import com.uwetrottmann.trakt.v2.enums.Extended;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class RecommendationsTest extends BaseTestCase {

    @Test
    public void test_movies() {
        List<Movie> movies = getTrakt().recommendations().movies(Extended.FULLIMAGES);
        assertThat(movies).isNotEmpty();
    }

    @Test
    public void test_shows() {
        List<Show> shows = getTrakt().recommendations().shows(Extended.FULLIMAGES);
        assertThat(shows).isNotEmpty();
    }

}
