package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Comment;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Stats;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MovieServiceTest extends BaseTestCase {

    public void test_comments() {
        List<Comment> comments = getManager().movieService().comments("tt0079470");
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0)).isNotNull();
    }

    public void test_stats() {
        Stats stats = getManager().movieService().stats("the-social-network-2010");
        assertThat(stats).isNotNull();
        assertThat(stats.ratings).isNotNull();
        assertThat(stats.ratings.distribution).isNotNull();
        assertThat(stats.scrobbles).isNotNull();
        assertThat(stats.checkins).isNotNull();
        assertThat(stats.collection).isNotNull();
        assertThat(stats.lists).isNotNull();
        assertThat(stats.comments).isNotNull();
    }

    public void test_summary() {
        Movie movie = getManager().movieService().summary("tt1285016");
        assertThat(movie).isNotNull();
        assertThat(movie.title).isEqualTo("The Social Network");
    }

}
