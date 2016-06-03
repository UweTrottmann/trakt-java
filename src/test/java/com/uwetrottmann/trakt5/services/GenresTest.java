package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.entities.Genre;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GenresTest extends BaseTestCase {

    @Test
    public void test_genres_shows() {
        List<Genre> genres = getTrakt().genres().shows();
        assertGenres(genres);
    }

    @Test
    public void test_genres_movies() {
        List<Genre> genres = getTrakt().genres().movies();
        assertGenres(genres);
    }

    private void assertGenres(List<Genre> genres) {
        for (Genre genre : genres) {
            assertThat(genre.name).isNotEmpty();
            assertThat(genre.slug).isNotEmpty();
        }
    }

}
