package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.ActionResponse;
import com.jakewharton.trakt.entities.Comment;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.Stats;
import com.jakewharton.trakt.enumerations.Status;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class MovieServiceTest extends BaseTestCase {

    @Test
    public void test_comments() {
        List<Comment> comments = getManager().movieService().comments("tt0079470");
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0)).isNotNull();
    }

    @Test
    public void test_library() {
        ActionResponse response = getManager().movieService().library(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_seen() {
        ActionResponse response = getManager().movieService().seen(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
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

    @Test
    public void test_summary() {
        Movie movie = getManager().movieService().summary("tt1285016");
        assertThat(movie).isNotNull();
        assertThat(movie.title).isEqualTo("The Social Network");
    }

    @Test
    public void test_unlibrary() {
        Response response = getManager().movieService().unlibrary(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_unseen() {
        Response response = getManager().movieService().unseen(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_unwatchlist() {
        Response response = getManager().movieService().unwatchlist(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_watchlist() {
        ActionResponse response = getManager().movieService().watchlist(new MovieService.Movies(
                new MovieService.SeenMovie("tt1285016")));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }
}
