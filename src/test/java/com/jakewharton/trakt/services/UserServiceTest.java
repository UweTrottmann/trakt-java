package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.UserProfile;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class UserServiceTest extends BaseTestCase {

    @Test
    public void test_following() {
        List<UserProfile> following = getManager().userService().following("sgtest");
        assertThat(following).isNotNull();
    }

    @Test
    public void test_friends() {
        List<UserProfile> following = getManager().userService().friends("sgtest");
        assertThat(following).isNotNull();
    }

    @Test
    public void test_libraryShowsAll() {
        List<TvShow> shows = getManager().userService().libraryShowsAll("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsAllExtended("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsMin() {
        List<TvShow> shows = getManager().userService().libraryShowsAllMinimum("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollection() {
        List<TvShow> shows = getManager().userService().libraryShowsCollection("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollectionExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsCollectionExtended(
                "JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollectionMin() {
        List<TvShow> shows = getManager().userService()
                .libraryShowsCollectionMinimum("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatched() {
        List<TvShow> shows = getManager().userService().libraryShowsWatched("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatchedExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsWatchedExtended("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatchedMin() {
        List<TvShow> shows = getManager().userService().libraryShowsWatchedMinimum("JakeWharton");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_profile() {
        UserProfile profile = getManager().userService().profile("justin");
        assertThat(profile).isNotNull();
        assertThat(profile.username).isEqualTo("justin");
        assertThat(profile.watched).isNotNull();
    }

    @Test
    public void test_watchlistMovies() {
        List<Movie> movies = getManager().userService().watchlistMovies("justin");
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0)).isNotNull();
    }

    @Test
    public void test_watchlistShows() {
        List<TvShow> shows = getManager().userService().watchlistShows("justin");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }


    @Test
    public void test_userCalendar() {
        List<CalendarDate> shows = getManager().userService().calendarShows("JakeWharton", "2013-09-21", 7);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }
}
