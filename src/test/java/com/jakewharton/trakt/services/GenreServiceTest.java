package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Genre;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class GenreServiceTest extends BaseTestCase {

    public void test_movies() {
        List<Genre> genres = getManager().genreService().movies();
        assertThat(genres).isNotEmpty();
        assertThat(genres.get(0)).isNotNull();
    }

    public void test_shows() {
        List<Genre> genres = getManager().genreService().shows();
        assertThat(genres).isNotEmpty();
        assertThat(genres.get(0)).isNotNull();
    }
}