package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.entities.Genre;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GenresTest extends BaseTestCase {

    @Test
    public void test_genres_shows() throws IOException {
        Response<List<Genre>> response = getTrakt().genres().shows().execute();
        assertSuccessfulResponse(response);
        List<Genre> genres = response.body();
        assertGenres(genres);
    }

    @Test
    public void test_genres_movies() throws IOException {
        Response<List<Genre>> response = getTrakt().genres().movies().execute();
        assertSuccessfulResponse(response);
        List<Genre> genres = response.body();
        assertGenres(genres);
    }

    private void assertGenres(List<Genre> genres) {
        for (Genre genre : genres) {
            assertThat(genre.name).isNotEmpty();
            assertThat(genre.slug).isNotEmpty();
        }
    }

}
