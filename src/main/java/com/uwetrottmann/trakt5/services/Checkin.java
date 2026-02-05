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
import com.uwetrottmann.trakt5.entities.EpisodeCheckin;
import com.uwetrottmann.trakt5.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt5.entities.MovieCheckin;
import com.uwetrottmann.trakt5.entities.MovieCheckinResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

public interface Checkin {

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Check into an episode. This should be tied to a user action to manually indicate they are watching something. The
     * item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    Call<EpisodeCheckinResponse> checkin(
            @Body EpisodeCheckin episodeCheckin
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Check into a movie. This should be tied to a user action to manually indicate they are watching something. The
     * item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    Call<MovieCheckinResponse> checkin(
            @Body MovieCheckin movieCheckin
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Removes any active checkins, no need to provide a specific item.
     */
    @DELETE("checkin")
    Call<Void> deleteActiveCheckin();

}
