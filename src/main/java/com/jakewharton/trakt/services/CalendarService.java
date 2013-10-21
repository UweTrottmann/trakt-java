package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.CalendarDate;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface CalendarService {

    /**
     * Returns all shows premiering during the time period specified.
     */
    @GET("/calendar/premieres/{apikey}")
    List<CalendarDate> premieres();

    /**
     * Returns all shows premiering during the time period specified.
     *
     * @param date Start date for the calendar in the format Ymd (i.e. 20110421). If blank, defaults to today.
     * @param days Number of days to display starting from the date. If blank, defaults to 7 days.
     */
    @GET("/calendar/premieres/{apikey}")
    List<CalendarDate> premieres(
            @Query("date") String date,
            @Query("days") int days
    );

    /**
     * Returns all shows airing during the time period specified.
     */
    @GET("/calendar/shows/{apikey}")
    List<CalendarDate> shows();

    /**
     * Returns all shows premiering during the time period specified.
     *
     * @param date Start date for the calendar in the format Ymd (i.e. 20110421). If blank, defaults to today.
     * @param days Number of days to display starting from the date. If blank, defaults to 7 days.
     */
    @GET("/calendar/shows/{apikey}")
    List<CalendarDate> shows(
            @Query("date") String date,
            @Query("days") int days
    );

}
