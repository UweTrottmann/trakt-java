package com.redissi.trakt.services

import com.redissi.trakt.entities.CalendarMovieEntry
import com.redissi.trakt.entities.CalendarShowEntry
import retrofit2.http.GET
import retrofit2.http.Path

interface Calendars {
    /**
     * **OAuth Required**
     *
     * @see .shows
     */
    @GET("calendars/my/shows/{startdate}/{days}")
    suspend fun myShows(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * **OAuth Required**
     *
     * @see .newShows
     */
    @GET("calendars/my/shows/new/{startdate}/{days}")
    suspend fun myNewShows(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * **OAuth Required**
     *
     * @see .seasonPremieres
     */
    @GET("calendars/my/shows/premieres/{startdate}/{days}")
    suspend fun mySeasonPremieres(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * **OAuth Required**
     *
     * @see .movies
     */
    @GET("calendars/my/movies/{startdate}/{days}")
    suspend fun myMovies(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarMovieEntry>?

    /**
     * Returns all shows airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/{startdate}/{days}")
    suspend fun shows(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * Returns all new show premieres (season 1, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/new/{startdate}/{days}")
    suspend fun newShows(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * Returns all show premieres (any season, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/premieres/{startdate}/{days}")
    suspend fun seasonPremieres(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarShowEntry>?

    /**
     * Returns all movies with a release date during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("calendars/all/movies/{startdate}/{days}")
    suspend fun movies(
        @Path("startdate") startDate: String?,
        @Path("days") days: Int
    ): List<CalendarMovieEntry>?
}