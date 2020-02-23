package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.entities.CalendarMovieEntry
import com.redissi.trakt.entities.CalendarShowEntry
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.io.IOException

class CalendarsTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun `get my shows`() = runBlocking {
        val entries = trakt.calendars().myShows(START_DATE_MY_SHOWS, TEST_DAYS)
        entries.shouldNotBeNull()
        assertShowCalendar(entries)
    }

    @Test
    @Throws(IOException::class)
    fun `get my new shows`() = runBlocking {
        val response = trakt.calendars().myNewShows(START_DATE_MY_SHOWS, TEST_DAYS)
        response.shouldNotBeNull()
        assertShowCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun `get my season premieres`() = runBlocking {
        val response = trakt.calendars().mySeasonPremieres(START_DATE_MY_SHOWS, TEST_DAYS)
        response.shouldNotBeNull()
        assertShowCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun `get my movies`() = runBlocking {
        val response = trakt.calendars().myMovies(START_DATE_MY_MOVIES, DAYS_MOVIES)
        response.shouldNotBeNull()
        assertMovieCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun `get all shows`() = runBlocking<Unit> {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = trakt.calendars().shows(START_DATE_ALL, TEST_DAYS)
        response.shouldNotBeNull()
        assertShowCalendar(response)
        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun `get all new shows`() = runBlocking<Unit> {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = trakt.calendars().newShows(START_DATE_ALL, TEST_DAYS)
        response.shouldNotBeNull()
        assertShowCalendar(response)
        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun `get all season premieres`() = runBlocking<Unit> {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = trakt.calendars().seasonPremieres(START_DATE_ALL, TEST_DAYS)
        response.shouldNotBeNull()
        assertShowCalendar(response)
        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun `get all movies`() = runBlocking<Unit> {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = trakt.calendars().movies(START_DATE_ALL, 30)
        response.shouldNotBeNull()
        assertMovieCalendar(response)
        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    private fun assertShowCalendar(shows: List<CalendarShowEntry>) {
        for (entry in shows) {
            entry.firstAired.shouldNotBeNull()
            entry.episode.shouldNotBeNull()
            entry.show.shouldNotBeNull()
        }
    }

    private fun assertMovieCalendar(movies: List<CalendarMovieEntry>) {
        for (entry in movies) {
            entry.released.shouldNotBeNull()
            entry.movie.shouldNotBeNull()
        }
    }

    companion object {
        // week which has show premiere (and therefore season premiere)
        private const val START_DATE_ALL = "2016-10-01"
        private const val START_DATE_MY_SHOWS = "2014-09-01"
        private const val TEST_DAYS = 7
        const val START_DATE_MY_MOVIES = "2014-05-01"
        const val DAYS_MOVIES = 30
    }
}