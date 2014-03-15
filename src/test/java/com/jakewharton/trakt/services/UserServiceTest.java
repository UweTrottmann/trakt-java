package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowProgress;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.enumerations.Extended;
import com.jakewharton.trakt.enumerations.Extended2;
import com.jakewharton.trakt.enumerations.SortType;

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
    public void test_libraryMoviesAll() {
        List<Movie> movies = getManager().userService().libraryMoviesAll("sgtest",
                Extended.DEFAULT);
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0)).isNotNull();
    }

    @Test
    public void test_libraryMoviesCollection() {
        List<Movie> movies = getManager().userService().libraryMoviesCollection("sgtest",
                Extended.DEFAULT);
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0)).isNotNull();
    }

    @Test
    public void test_libraryMoviesWatched() {
        List<Movie> movies = getManager().userService().libraryMoviesWatched("sgtest",
                Extended.DEFAULT);
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsAll() {
        List<TvShow> shows = getManager().userService().libraryShowsAll("sgtest", Extended.DEFAULT);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsExtended() {
        List<TvShow> shows = getManager().userService()
                .libraryShowsAll("sgtest", Extended.EXTENDED);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsMin() {
        List<TvShow> shows = getManager().userService().libraryShowsAll("sgtest", Extended.MIN);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollection() {
        List<TvShow> shows = getManager().userService()
                .libraryShowsCollection("sgtest", Extended.DEFAULT);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollectionExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsCollection(
                "sgtest", Extended.EXTENDED);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsCollectionMin() {
        List<TvShow> shows = getManager().userService()
                .libraryShowsCollection("sgtest", Extended.MIN);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatched() {
        List<TvShow> shows = getManager().userService()
                .libraryShowsWatched("sgtest", Extended.DEFAULT);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatchedExtended() {
        List<TvShow> shows = getManager().userService().libraryShowsWatched("sgtest",
                Extended.EXTENDED);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_libraryShowsWatchedMin() {
        List<TvShow> shows = getManager().userService().libraryShowsWatched("sgtest", Extended.MIN);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }

    @Test
    public void test_progressWatchedFull() {
        List<TvShowProgress> progress = getManager().userService().progressWatched(
                "sgtest", "the-walking-dead", SortType.DEFAULT, Extended2.FULL);
        assertThat(progress).isNotEmpty();
        assertThat(progress.get(0)).isNotNull();
    }

    @Test
    public void test_progressWatchedNormal() {
        List<TvShowProgress> progress = getManager().userService().progressWatched(
                "sgtest", "the-walking-dead", SortType.DEFAULT, Extended2.NORMAL);
        assertThat(progress).isNotEmpty();
        assertThat(progress.get(0)).isNotNull();
    }

    @Test
    public void test_progressCollectedFull() {
        List<TvShowProgress> progress = getManager().userService().progressCollected(
                "sgtest", "the-walking-dead", SortType.DEFAULT, Extended2.FULL);
        assertThat(progress).isNotEmpty();
        assertThat(progress.get(0)).isNotNull();
    }

    @Test
    public void test_progressCollectedNormal() {
        List<TvShowProgress> progress = getManager().userService().progressCollected(
                "sgtest", "the-walking-dead", SortType.DEFAULT, Extended2.NORMAL);
        assertThat(progress).isNotEmpty();
        assertThat(progress.get(0)).isNotNull();
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
