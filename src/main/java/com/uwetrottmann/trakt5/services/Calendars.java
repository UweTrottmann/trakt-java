/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.TraktV2;
import com.uwetrottmann.trakt5.entities.CalendarMovieEntry;
import com.uwetrottmann.trakt5.entities.CalendarShowEntry;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface Calendars {

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     *
     * @see #shows(String, int)
     */
    @GET("calendars/my/shows/{startdate}/{days}")
    Call<List<CalendarShowEntry>> myShows(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     *
     * @see #newShows(String, int)
     */
    @GET("calendars/my/shows/new/{startdate}/{days}")
    Call<List<CalendarShowEntry>> myNewShows(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     *
     * @see #seasonPremieres(String, int)
     */
    @GET("calendars/my/shows/premieres/{startdate}/{days}")
    Call<List<CalendarShowEntry>> mySeasonPremieres(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     *
     * @see #movies(String, int)
     */
    @GET("calendars/my/movies/{startdate}/{days}")
    Call<List<CalendarMovieEntry>> myMovies(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * Returns all shows airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days      Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/{startdate}/{days}")
    Call<List<CalendarShowEntry>> shows(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * Returns all new show premieres (season 1, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days      Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/new/{startdate}/{days}")
    Call<List<CalendarShowEntry>> newShows(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * Returns all show premieres (any season, episode 1) airing during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days      Number of days to display. Example: 7.
     */
    @GET("calendars/all/shows/premieres/{startdate}/{days}")
    Call<List<CalendarShowEntry>> seasonPremieres(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

    /**
     * Returns all movies with a release date during the time period specified.
     *
     * @param startDate Start the calendar on this date. Example: 2014-09-01.
     * @param days      Number of days to display. Example: 7.
     */
    @GET("calendars/all/movies/{startdate}/{days}")
    Call<List<CalendarMovieEntry>> movies(
            @Path("startdate") String startDate,
            @Path("days") int days
    );

}
