package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.entities.CalendarEntry;
import com.uwetrottmann.trakt.v2.entities.CalendarShowEntry;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarsTest extends BaseTestCase {

    // week which has show premiere (and therefore season premiere)
    private static final String TEST_START_DATE = "2014-09-01";
    private static final int TEST_DAYS = 7;

    @Test
    public void test_myShows() throws OAuthUnauthorizedException {
        List<CalendarShowEntry> shows = getTrakt().calendars().myShows(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);
    }

    @Test
    public void test_myNewShows() throws OAuthUnauthorizedException {
        List<CalendarShowEntry> shows = getTrakt().calendars().myNewShows(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);
    }

    @Test
    public void test_mySeasonPremieres() throws OAuthUnauthorizedException {
        List<CalendarShowEntry> shows = getTrakt().calendars().mySeasonPremieres(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);
    }

    @Test
    public void test_shows() {
        // do unauthenticated call
        getTrakt().setAccessToken(null);

        List<CalendarShowEntry> shows = getTrakt().calendars().shows(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);

        // restore auth
        getTrakt().setAccessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_newShows() {
        // do unauthenticated call
        getTrakt().setAccessToken(null);

        List<CalendarShowEntry> shows = getTrakt().calendars().newShows(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);

        // restore auth
        getTrakt().setAccessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_seasonPremieres() {
        // do unauthenticated call
        getTrakt().setAccessToken(null);

        List<CalendarShowEntry> shows = getTrakt().calendars().seasonPremieres(TEST_START_DATE, TEST_DAYS);
        assertShowCalendar(shows);

        // restore auth
        getTrakt().setAccessToken(TEST_ACCESS_TOKEN);
    }

    @Test
    public void test_movies() throws OAuthUnauthorizedException {
        // do unauthenticated call
        getTrakt().setAccessToken(null);

        Map<DateTime, List<CalendarEntry>> movies = getTrakt().calendars().movies("2014-05-01", 30);
        assertThat(movies.keySet()).doesNotContainNull();
        for (List<CalendarEntry> entries : movies.values()) {
            for (CalendarEntry entry : entries) {
                assertThat(entry.airs_at).isNull();
                assertThat(entry.episode).isNull();
                assertThat(entry.show).isNull();
                assertThat(entry.movie).isNotNull();
            }
        }

        // restore auth
        getTrakt().setAccessToken(TEST_ACCESS_TOKEN);
    }

    private void assertShowCalendar(List<CalendarShowEntry> shows) {
        for (CalendarShowEntry entry : shows) {
            assertThat(entry.first_aired).isNotNull();
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
        }
    }

}
