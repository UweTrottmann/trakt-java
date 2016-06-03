package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.entities.CalendarMovieEntry;
import com.uwetrottmann.trakt5.entities.CalendarShowEntry;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarsTest extends BaseTestCase {

    // week which has show premiere (and therefore season premiere)
    private static final String START_DATE_ALL = "2016-06-01";
    private static final String START_DATE_MY_SHOWS = "2014-09-01";
    private static final int TEST_DAYS = 7;
    public static final String START_DATE_MY_MOVIES = "2014-05-01";
    public static final int DAYS_MOVIES = 30;

    @Test
    public void test_myShows() throws IOException {
        Response<List<CalendarShowEntry>> response = getTrakt().calendars().myShows(START_DATE_MY_SHOWS,
                TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());
    }

    @Test
    public void test_myNewShows() throws IOException {
        Response<List<CalendarShowEntry>> response = getTrakt().calendars().myNewShows(START_DATE_MY_SHOWS,
                TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());
    }

    @Test
    public void test_mySeasonPremieres() throws IOException {
        Response<List<CalendarShowEntry>> response = getTrakt().calendars().mySeasonPremieres(START_DATE_MY_SHOWS,
                TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());
    }

    @Test
    public void test_myMovies() throws IOException {
        Response<List<CalendarMovieEntry>> response = getTrakt().calendars().myMovies(START_DATE_MY_MOVIES,
                DAYS_MOVIES).execute();
        assertSuccessfulResponse(response);
        assertMovieCalendar(response.body());
    }

    @Test
    public void test_shows() throws IOException {
        // do unauthenticated call
        getTrakt().accessToken(null);

        Response<List<CalendarShowEntry>> response = getTrakt().calendars().shows(START_DATE_ALL, TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());

        // restore auth
        getTrakt().accessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_newShows() throws IOException {
        // do unauthenticated call
        getTrakt().accessToken(null);

        Response<List<CalendarShowEntry>> response = getTrakt().calendars().newShows(START_DATE_ALL,
                TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());

        // restore auth
        getTrakt().accessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_seasonPremieres() throws IOException {
        // do unauthenticated call
        getTrakt().accessToken(null);

        Response<List<CalendarShowEntry>> response = getTrakt().calendars().seasonPremieres(START_DATE_ALL,
                TEST_DAYS).execute();
        assertSuccessfulResponse(response);
        assertShowCalendar(response.body());

        // restore auth
        getTrakt().accessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_movies() throws IOException {
        // do unauthenticated call
        getTrakt().accessToken(null);

        Response<List<CalendarMovieEntry>> response = getTrakt().calendars().movies(START_DATE_ALL, 30).execute();
        assertSuccessfulResponse(response);
        assertMovieCalendar(response.body());

        // restore auth
        getTrakt().accessToken(TEST_ACCESS_TOKEN);
    }

    private void assertShowCalendar(List<CalendarShowEntry> shows) {
        for (CalendarShowEntry entry : shows) {
            assertThat(entry.first_aired).isNotNull();
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
        }
    }

    private void assertMovieCalendar(List<CalendarMovieEntry> movies) {
        for (CalendarMovieEntry entry : movies) {
            assertThat(entry.released).isNotNull();
            assertThat(entry.movie).isNotNull();
        }
    }

}
