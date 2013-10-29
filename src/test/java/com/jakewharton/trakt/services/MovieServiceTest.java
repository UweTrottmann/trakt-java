package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Movie;

import static org.fest.assertions.api.Assertions.assertThat;

public class MovieServiceTest extends BaseTestCase {

    public void test_summary() {
        Movie movie = getManager().movieService().summary("tt1285016");
        assertThat(movie).isNotNull();
        assertThat(movie.title).isEqualTo("The Social Network");
    }

}
