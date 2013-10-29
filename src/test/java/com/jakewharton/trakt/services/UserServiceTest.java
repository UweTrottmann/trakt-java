package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.UserProfile;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class UserServiceTest extends BaseTestCase {

    public void test_following() {
        List<UserProfile> following = getManager().userService().following("sgtest");
        assertThat(following).isNotNull();
    }

    public void test_friends() {
        List<UserProfile> following = getManager().userService().friends("sgtest");
        assertThat(following).isNotNull();
    }

    public void test_libraryShowsAll() {
        List<TvShow> shows = getManager().userService().libraryShowsAll("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    public void test_libraryShowsExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsAllExtended("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    public void test_libraryShowsMin() {
        List<TvShow> shows = getManager().userService().libraryShowsAllMinimum("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    public void test_watchlistMovies() {
        List<Movie> movies = getManager().userService().watchlistMovies("justin");
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0)).isNotNull();
    }

    public void test_watchlistShows() {
        List<TvShow> shows = getManager().userService().watchlistShows("justin");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }
}
