package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Genre;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GenreServiceTest extends BaseTestCase {

    @Test
    public void test_movies() {
        List<Genre> genres = getManager().genreService().movies();
        assertThat(genres).isNotEmpty();
        assertThat(genres.get(0)).isNotNull();
    }

    @Test
    public void test_shows() {
        List<Genre> genres = getManager().genreService().shows();
        assertThat(genres).isNotEmpty();
        assertThat(genres.get(0)).isNotNull();
    }
}