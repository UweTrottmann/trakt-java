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
import com.uwetrottmann.trakt5.entities.PlaybackResponse;
import com.uwetrottmann.trakt5.entities.ScrobbleProgress;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Scrobble {
    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * User starts a video
     */
    @POST("scrobble/start")
    Call<PlaybackResponse> startWatching(
            @Body ScrobbleProgress prog
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * User pauses a video
     */
    @POST("scrobble/pause")
    Call<PlaybackResponse> pauseWatching(
            @Body ScrobbleProgress prog
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * User stops a video
     */
    @POST("scrobble/stop")
    Call<PlaybackResponse> stopWatching(
            @Body ScrobbleProgress prog
    );

}
