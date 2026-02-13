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

package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import com.uwetrottmann.trakt5.enums.Audio;
import com.uwetrottmann.trakt5.enums.AudioChannels;
import com.uwetrottmann.trakt5.enums.Hdr;
import com.uwetrottmann.trakt5.enums.MediaType;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Resolution;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nonnull;

public class SyncMovie {

    public MovieIds ids;

    public OffsetDateTime collected_at;
    /**
     * Warning: Trakt only stores and returns minute-precision timestamps for the watched at time. So seconds and
     * nanoseconds will always be zero. Using {@code .truncatedTo(ChronoUnit.MINUTES)} can be helpful.
     */
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;
    public MediaType media_type;
    public Resolution resolution;
    public Hdr hdr;
    public Audio audio;
    public AudioChannels audio_channels;
    @SerializedName("3d")
    public Boolean is3d;

    @Nonnull
    public SyncMovie id(MovieIds id) {
        this.ids = id;
        return this;
    }

    @Nonnull
    public SyncMovie collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    /**
     * Warning: Trakt only stores and returns minute-precision timestamps for the watched at time. So seconds and
     * nanoseconds will always be zero. Using {@code .truncatedTo(ChronoUnit.MINUTES)} can be helpful.
     */
    @Nonnull
    public SyncMovie watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    @Nonnull
    public SyncMovie ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    @Nonnull
    public SyncMovie rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    @Nonnull
    public SyncMovie mediaType(MediaType media_type) {
        this.media_type = media_type;
        return this;
    }

    @Nonnull
    public SyncMovie resolution(Resolution resolution) {
        this.resolution = resolution;
        return this;
    }

    @Nonnull
    public SyncMovie hdr(Hdr hdr) {
        this.hdr = hdr;
        return this;
    }

    @Nonnull
    public SyncMovie audio(Audio audio) {
        this.audio = audio;
        return this;
    }

    @Nonnull
    public SyncMovie audioChannels(AudioChannels audio_channels) {
        this.audio_channels = audio_channels;
        return this;
    }

    @Nonnull
    public SyncMovie is3d(Boolean is3d) {
        this.is3d = is3d;
        return this;
    }

}
