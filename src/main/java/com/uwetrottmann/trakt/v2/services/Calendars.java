package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.entities.CalendarEntry;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;
import java.util.Map;

public interface Calendars {

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all shows airing in the next 7 days.
     */
    @GET("/calendars/shows")
    Map<DateTime, List<CalendarEntry>> shows() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all shows airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("/calendars/shows/{startdate}/{days}")
    Map<DateTime, List<CalendarEntry>> shows(
            @Path("startdate") String startDate,
            @Path("days") int days
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all new show premieres (season 1, episode 1) airing in the next 7 days.
     */
    @GET("/calendars/shows/new")
    Map<DateTime, List<CalendarEntry>> newShows() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all new show premieres (season 1, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("/calendars/shows/new/{startdate}/{days}")
    Map<DateTime, List<CalendarEntry>> newShows(
            @Path("startdate") String startDate,
            @Path("days") int days
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all show premieres (any season, episode 1) airing in the next 7 days.
     */
    @GET("/calendars/shows/premieres")
    Map<DateTime, List<CalendarEntry>> seasonPremieres() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all show premieres (any season, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("/calendars/shows/premieres/{startdate}/{days}")
    Map<DateTime, List<CalendarEntry>> seasonPremieres(
            @Path("startdate") String startDate,
            @Path("days") int days
    ) throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all movies with a release date in the next 7 days.
     */
    @GET("/calendars/movies")
    Map<DateTime, List<CalendarEntry>> movies() throws OAuthUnauthorizedException;

    /**
     * <b>OAuth Optional</b>
     *
     * <p> Returns all movies with a release date during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days Number of days to display. Example: 7.
     */
    @GET("/calendars/movies/{startdate}/{days}")
    Map<DateTime, List<CalendarEntry>> movies(
            @Path("startdate") String startDate,
            @Path("days") int days
    ) throws OAuthUnauthorizedException;

}
